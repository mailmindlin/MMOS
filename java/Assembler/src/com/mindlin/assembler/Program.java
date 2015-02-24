package com.mindlin.assembler;

import java.io.File;
import java.io.FileNotFoundException;

import org.json.JSONObject;

import com.mindlin.assembler.preprocessor.ParsingException;
import com.mindlin.assembler.preprocessor.Preprocessor;

public class Program {
	Environment env;
	protected Resolver resolver = new Resolver();
	protected File main;
	public JSONObject af;
	protected Preprocessor prep;
	public Program() {
		
	}
	public void setMain(File f) {
		main=f;
	}
	public void begin() {
		if(main==null) {
			System.err.println("Error: you didn't specify a file to compile.\n"
					+ "For additional help on how to use this, run 'java -jar MAssembler.jar /?'.");
			System.exit(-1);
		}
		env = new Environment(main);
		prep = new Preprocessor(this);
		try {
			prep.run();
		} catch (FileNotFoundException | ParsingException e) {
			e.printStackTrace();
		}
	}
	public Environment getEnvironment() {
		return env;
	}
}
