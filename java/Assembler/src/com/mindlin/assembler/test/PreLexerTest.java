package com.mindlin.assembler.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.json.JSONArray;
import org.junit.Test;

import com.mindlin.assembler.preprocessor.ParsingException;
import com.mindlin.assembler.preprocessor.PreLexer;

public class PreLexerTest {

	@Test
	public void testCommentRemove() {
		String[] comments = new String[] {
				"//this is a comment",
				"/*this",
				"is also a comment.",
				"//even this",
				"should still be in a",
				"comment*/(not in a comment)" };
		PreLexer pl = new PreLexer(null);
		JSONArray result = pl.removeComments(Arrays.asList(comments));
		assertEquals("Bad length.", result.size(), 6);
		assertEquals("Bad value.", result.get(5), "(not in a comment)");
	}
	@Test
	public void testLex() {
		String[] sample = new String[] {
				"CP1",
				"#ifdef A",
				"CP2",
				"#endif",
				"CP3",
				"#ifndef A",
				"CP4",
				"#elif true",
				"CP5",
				"#else",
				"CP6",
				"#endif"};
		PreLexer pl = new PreLexer(null);
		JSONArray expect = new JSONArray("[\"CP1\",{\"c0\":\"defined(A)\",\"r0\":[\"CP2\"]},\"CP3\",{\"e\":[\"CP6\"],\"c0\":\"!defined(A)\",\"r0\":[\"CP4\"],\"c1\":\"true\",\"r1\":[\"CP5\"]}]");
		try {
			JSONArray result = pl.lex(Arrays.asList(sample).listIterator());
			System.out.println("Expected: "+expect.toString());
			System.out.println("Got:      "+result.toString());
			assertEquals(expect.toString(), result.toString());
		} catch (ParsingException e) {
			e.printStackTrace();
			fail();
		}
		
	}
}
