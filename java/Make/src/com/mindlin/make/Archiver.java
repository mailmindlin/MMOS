package com.mindlin.make;

import java.io.File;

public abstract class Archiver {
	protected final String command;
	protected StringBuffer parts;
	public Archiver(String command) {
		this.command=command;
	}
	public abstract CCompiler setOutput(String output);
	public abstract CCompiler setOutput(File output);
	public abstract CCompiler addTarget(File target);
	public abstract CCompiler addTarget(String language, File target);
	public abstract CCompiler addTarget(String target);
	public abstract CCompiler addTarget(String language, String target);
	public abstract CCompiler flag(String flag, String...arguments);
	public abstract boolean execute();
}
