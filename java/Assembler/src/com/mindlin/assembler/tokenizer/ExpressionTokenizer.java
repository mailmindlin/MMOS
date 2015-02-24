package com.mindlin.assembler.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mindlin.assembler.util.ArrayUtils;

public class ExpressionTokenizer {
	protected static final Pattern func = Pattern.compile("(\\w+)\\(([\\w, ]*)\\)");

	public JSONArray tokenize(String s) {
		JSONArray result = new JSONArray();
		result.put(s);
		Matcher m = func.matcher(s);
		while (m.find()) {
			JSONObject function = new JSONObject();
			function.put("all", m.group());
			function.put("name", m.group(1));
			function.put("args", m.group(2));
			function.put("argv", splitParams(m.group(2)));
			function.put("type", "function");
			int len = m.end() - m.start();
			int start = m.start();
			for (int i = 0; i < result.length(); i++) {
				int clen = 0;
				if (result.get(i) instanceof String)
					clen = result.getString(i).length();
				else if (result.get(i) instanceof JSONObject)
					clen = result.getJSONObject(i).getString("all").length();
				if (clen < start)
					start -= clen;
				else {
					String o = result.getString(i);
					String a = o.substring(0, start);
					String b = o.substring(start + len);
					result.put(i, a);
					result.add(i + 1, function);
					result.add(i + 2, b);
					break;
				}
			}
		}
		System.out.println(result.toString());
		return result;
	}

	public JSONArray splitParams(String s) {
		boolean quote = false;
		int parens = 0;
		JSONArray output = new JSONArray();
		StringBuilder current = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '"') {
				quote = !quote;
				if (parens == 0)
					continue;
			}
			if (!quote) {
				if (c == '(')
					parens++;
				if (c == ')')
					parens--;
				if (c == ',' && parens == 0) {
					output.put(current.toString());
					current = new StringBuilder();
					if (i + 1 < s.length() && s.charAt(i + 1) == ' ')
						i++;
					continue;
				}
			}
			current.append(c);
		}
		output.put(current.toString());
		return output;
	}

	public JSONArray tokenizev2(String expr) {
		List<Character> xprList = Arrays.asList(ArrayUtils.toObject(expr.replaceAll("\\s","").toCharArray()));
		JSONArray bubbled = bubble(xprList.listIterator());
		return null;
	}
	public JSONArray bubble(ListIterator<Character> iter) {
		JSONArray result = new JSONArray();
		StringBuilder sb = new StringBuilder();
		
		while(iter.hasNext()) {
			char c=iter.next();
			sb.append(c);
			if(c=='(') {
				//dump stuff
				String s = sb.toString();
				sb=null;//release stringbuilder
				s=s.substring(0,s.length()-1);
				if(!s.isEmpty())
					result.add(s);

				result.add(bubble(iter));//parse next parens
				sb=new StringBuilder();//reset stringbuilder
			}else if(c==')') {
				//dump stuff
				String s = sb.toString();
				sb=null;//release stringbuilder
				s=s.substring(0,s.length()-1);
				if(!s.isEmpty())
					result.add(s);
				return result;
			}
		}
		if(sb.length()>0)
			result.add(sb.toString());
		return result;
	}
}
