package com.mindlin.assembler.preprocessor;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedTransferQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mindlin.assembler.Program;
import com.mindlin.assembler.tokenizer.PreprocessorExpressionTokenizer;

public class Preprocessor {
	public static final Pattern define = Pattern.compile("#define ([\\w_]*) ?(.*)");
	protected static final ExecutorService executor = Executors.newCachedThreadPool();
	protected PreprocessorExpressionTokenizer tokenizer = new PreprocessorExpressionTokenizer();
	Program p;

	public Preprocessor(Program p) {
		this.p = p;
	}

	public void run() throws FileNotFoundException, ParsingException {
		preprocessEverything();
	}

	protected JSONArray lexFile(JSONObject af) throws FileNotFoundException, ParsingException {
		return new PreLexer(af).call();
	}

	protected void preprocessEverything() throws FileNotFoundException, ParsingException {
		Set<JSONObject> processed = new HashSet<JSONObject>();
		LinkedTransferQueue<JSONObject> toProcess = new LinkedTransferQueue<JSONObject>();
		toProcess.offer(p.af);
		JSONObject af;
		while ((af = toProcess.poll()) != null) {
			if (processed.contains(af))
				continue;
			af.put("parsed", process(lexFile(af)));
			processed.add(af);
		}
	}

	protected void preprocessEverythingMultithreaded() {
		ConcurrentHashMap<JSONObject, Future<JSONArray>> processing = new ConcurrentHashMap<JSONObject, Future<JSONArray>>();
		ConcurrentHashMap<JSONObject, JSONArray> done = new ConcurrentHashMap<JSONObject, JSONArray>();
		lexFile(p.af, processing);
		while (!processing.isEmpty()) {
			processing.forEach((af, rs) -> {
				if (((Future<JSONArray>) rs).isDone()) {
					processing.remove(af);
					try {
						JSONArray result = process(rs.get());
						done.put(af, result);
						System.out.println("Result: ");
						result.forEach(line -> System.out.println(line));
					} catch (Exception e) {
						e.printStackTrace();
						return;// fail
				}
			}
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
		})	;
		}
	}

	protected void lexFile(JSONObject af, Map<JSONObject, Future<JSONArray>> storage) {
		if (storage.containsKey(af))
			return;
		storage.put(af, executor.submit(new PreLexer(af)));
	}

	protected List<String> loadPossibleIncludes(JSONArray source) {
		final List<String> result = new ArrayList<String>();
		source.forEach(obj -> {
			if (obj instanceof JSONObject) {
				((JSONObject) obj).forEach((k, v) -> {
					// ignore condition entries
						if (k.startsWith("c"))
							return;
						result.addAll(loadPossibleIncludes((JSONArray) v));
					});
			} else if (obj instanceof String) {
				String s = (String) obj;
				if (s.startsWith("#include"))
					result.add(s.substring(9));
			}
		});
		return result;
	}

	public boolean eval(String condition) {
		System.out.println("Evaluating " + condition);
		JSONArray data = tokenizer.tokenize(condition);
		for (int i = 0; i < data.length(); i++) {
			if (data.get(i) instanceof JSONObject
					&& data.getJSONObject(i).getString("type").equals("function")) {
				JSONObject function = data.getJSONObject(i);
				Object result = null;
				if (function.getString("name").equals("defined"))
					result = p.getEnvironment().isDefined(function.getString("args"));
				if (result != null)
					data.put(i, result);
			}
			if (data.get(i) == JSONObject.NULL || data.get(i) instanceof String
					&& data.getString(i).isEmpty())
				data.remove(i--);
		}
		System.out.println("Result: " + data.toString());
		if (data.length() == 1)
			return data.getBoolean(0);
		return false;// TODO finish somehow
	}

	public JSONArray process(JSONArray input) {
		JSONArray output = new JSONArray();
		for (int i = 0; i < input.length(); i++) {
			if (input.get(i) instanceof JSONObject) {
				JSONObject conditional = (JSONObject) input.get(i);
				// remove this line
				input.remove(i);
				// evaluate & find the section that works
				int condNum = 0;
				while (true) {
					if (conditional.has("c" + condNum)) {
						if (eval(conditional.getAs("c" + condNum))) {
							input.addAll(i, conditional.getAs("r" + condNum));
							break;
						} else
							condNum++;
					} else if (conditional.has("e")) {
						input.addAll(i, (JSONArray) conditional.<JSONArray> getAs("e"));
						break;
					} else
						break;// insert nothing
				}
				i--;// move back a line
				continue;
			} else if (input.get(i) instanceof String) {
				System.out.println("Parsing: " + input.getString(i));
				Matcher m = define.matcher(input.getString(i));
				if (m.find())
					p.getEnvironment().define(m.group(1), m.group(1));
				else {
					output.put(input.getString(i));
				}
			}
		}
		return output;// TODO finish
	}
}
