package com.mindlin.assembler;

import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONArray;

import com.mindlin.assembler.preprocessor.ParsingException;
import com.mindlin.assembler.preprocessor.PreLexer.PipingListIterator;

/**
 * Test code <a href="http://6502asm.com/beta/">here.</a>
 * @author wfeehery17
 *
 */
public class Lexer {
	JSONArray input;
	public Lexer(JSONArray input) {
		this.input=input;
	}
	public JSONArray call() throws ParsingException {
		JSONArray output = block(new PipingListIterator<Object, String>(input.listIterator()));
		for(int i=0;i<input.length();i++) {
			String line = input.getString(i);
		}
		return output;
	}
	public JSONArray block(ListIterator<String> li) throws ParsingException {
		return block(new AtomicInteger(0), li);
	}
	/**
	 * Extracts a single conditional block from the list iterator
	 * 
	 * @param li
	 * @return parsed conditional block
	 * @throws PreprocessorException
	 */
	protected JSONArray block(AtomicInteger lineNum, ListIterator<String> li) throws ParsingException {
		JSONArray result = new JSONArray();
		while (li.hasNext()) {
			String line = li.next().trim();
			if(line.equals("{")) {
				//do stuff
			}else if(line.equals("}")) {
				return result;
			}else if (line.contains("{")) {
				String pre = line.substring(0,line.indexOf("{"));
				String post = line.substring(line.indexOf("{")+1);
				System.out.println("Split "+line+" into "+pre+" and "+post);
				li.set(pre);
				li.add(post);
				li.previous();
			} else if (line.contains("}")) {
				String pre = line.substring(0,line.indexOf("}"));
				String post = line.substring(line.indexOf("}")+1);
				System.out.println("Split "+line+" into "+pre+" and "+post);
				li.set(pre);
				li.add(post);
				li.previous();
			} else {
				lineNum.incrementAndGet();
				result.put(line);
			}
		}
		return result;
	}
}
