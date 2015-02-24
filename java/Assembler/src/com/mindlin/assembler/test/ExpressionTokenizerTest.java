package com.mindlin.assembler.test;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.junit.Test;

import com.mindlin.assembler.tokenizer.PreprocessorExpressionTokenizer;

public class ExpressionTokenizerTest {

	@Test
	public void test() {
		PreprocessorExpressionTokenizer tokenizer = new PreprocessorExpressionTokenizer();
		List<Character> l = Arrays.asList(new Character[]{'(','a',')','?','(','b',')',':','(','c',')'});
		JSONArray result = tokenizer.bubble(l.listIterator());
		System.out.println(result.toString());
	}

}
