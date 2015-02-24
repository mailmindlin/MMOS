package com.mindlin.assembler.preprocessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mindlin.assembler.util.FileUtils;

public class PreLexer implements Callable<JSONArray> {
	protected JSONObject af;
	public PreLexer(JSONObject af2) {
		this.af=af2;
	}
	@Override
	public JSONArray call() throws FileNotFoundException, ParsingException {
		JSONArray lines;
		if(af.has("lines"))
			lines=af.getJSONArray("lines");
		else
			lines = FileUtils.readFile(af.<File>getAs("file"), new JSONArray());
		JSONArray noComments = removeComments(lines);
		JSONArray result = lex(new PipingListIterator<Object, String>(noComments.listIterator()));
		System.out.println(result.toString());
		return result;
	}
	public JSONArray lex(ListIterator<String> li) throws ParsingException {
		return lex(new AtomicInteger(0), li);
	}
	/**
	 * Extracts a single conditional block from the list iterator
	 * 
	 * @param li
	 * @return parsed conditional block
	 * @throws PreprocessorException
	 */
	public JSONArray lex(AtomicInteger lineNum, ListIterator<String> li) throws ParsingException {
		JSONArray result = new JSONArray();
		while (li.hasNext()) {
			String line = li.next().trim();
//			System.out.println("Lexing line: " + line);
			if (line.startsWith("#if")) {
				JSONObject conditional = new JSONObject();
				int num = 0;
				while (true) {
					if (line.startsWith("#ifdef "))
						conditional.put("c" + num, "defined(" + line.substring(7).trim() + ")");
					else if (line.startsWith("#ifndef "))
						conditional.put("c" + num, "!defined(" + line.substring(8).trim() + ")");
					else if (line.startsWith("#elif "))
						conditional.put("c" + num, line.substring(6).trim());
					else if (line.startsWith("#if "))
						conditional.put("c" + num, line.substring(4).trim());
					else if (line.startsWith("#else")) {
						if (conditional.has("e"))
							throw new ParsingException(af.getString("name") + ":" + lineNum.get()
									+ " Too many #else statements.");
						conditional.put("e", lex(lineNum, li));
						line = li.next().trim();
						lineNum.incrementAndGet();
						continue;
					} else if(line.startsWith("#endif")) {
						if(conditional.isEmpty())
							throw new ParsingException(af.getString("name") + ":" + lineNum + " Extra #endif");
						else
							break;
					} else
						throw new ParsingException(af.getString("name") + ":" + lineNum + " Malformed #if statement:\n\t" + line);
					//parse the next conditional block
					conditional.put("r" + num, lex(lineNum, li));
					//get ready for the next one
					num++;
					try{
						line = li.next().trim();
					}catch(NoSuchElementException e) {
						throw new ParsingException(af.getString("name") + ":" + lineNum + " No #endif");
					}
				}
				result.put(conditional);
			} else if (line.startsWith("#endif") || line.startsWith("#elif") || line.startsWith("#else")) {
				li.previous();
				lineNum.decrementAndGet();
				return result;
			} else {
				lineNum.incrementAndGet();
				result.put(line);
			}
		}
		return result;
	}
	/**
	 * Remove all comments and blank lines from the inputed text
	 * @param li input text
	 * @return list with comments/blank lines removed
	 */
	public JSONArray removeComments(List<Object> in) {
		JSONArray output = new JSONArray();
		ListIterator<Object> li = in.listIterator();
		boolean mlc = false;
		while (li.hasNext()) {
			boolean quote = false;
			boolean slc = false;
			String line = ((String)li.next()).trim();
			StringBuilder outLine = new StringBuilder();
			char[] lchr = line.toCharArray();
			for (int i = 0; i < lchr.length; i++) {
				if (i < lchr.length - 1 && (!mlc))
					if (i < lchr.length - 1 && (!mlc) && (!quote) && lchr[i] == '/' && lchr[i + 1] == '/') {
						slc = true;
						if (!mlc)
							break;// there's nothing usefull for the rest of the
									// line
						i++;// skip the next /
						continue;// skip this char
					}
				if (i < lchr.length - 1 && (!quote) && (!slc) && lchr[i] == '/' && lchr[i + 1] == '*') {
					mlc = true;
					i++;// skip the '*'
					continue;// skip this char
				}
				if (i < lchr.length - 1 && (!quote) && lchr[i] == '*' && lchr[i + 1] == '/') {
					mlc = false;
					// skip this and the next char
					i++;
					continue;
				}
				if ((!mlc) && (!slc)) {
					outLine.append(lchr[i]);
					if (lchr[i] == '"')
						quote = !quote;
				}
			}
			System.out.println("\"" + line + "\"->\"" + outLine.toString().trim() + "\"");
//			if (outLine.length() > 0)
				output.add(outLine.toString().trim());
		}
		return output;
	}
	@SuppressWarnings("unchecked")
	public static class PipingListIterator<Origin, Destination> implements ListIterator<Destination> {
		protected final ListIterator<Origin> li;
		public PipingListIterator(ListIterator<Origin> origin) {
			this.li=origin;
		}

		@Override
		public boolean hasNext() {
			return li.hasNext();
		}

		@Override
		public Destination next() {
			return (Destination)li.next();
		}

		@Override
		public boolean hasPrevious() {
			return li.hasPrevious();
		}

		@Override
		public Destination previous() {
			return (Destination) li.previous();
		}

		@Override
		public int nextIndex() {
			return li.nextIndex();
		}

		@Override
		public int previousIndex() {
			return li.previousIndex();
		}

		@Override
		public void remove() {
			li.remove();
		}

		@Override
		public void set(Destination e) {
			li.set((Origin) e);
		}

		@Override
		public void add(Destination e) {
			li.add((Origin) e);
		}
		
	}
}
