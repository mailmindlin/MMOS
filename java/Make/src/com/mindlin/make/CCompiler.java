package com.mindlin.make;

import java.io.File;

public abstract class CCompiler {
	protected final String command;
	protected StringBuffer parts;
	public CCompiler(String command) {
		this.command=command;
	}
	public abstract CCompiler setOutput(String output);
	public abstract CCompiler setOutput(File output);
	public abstract CCompiler define(String definition);
	public abstract CCompiler define(String definition, String value);
	public abstract CCompiler link(boolean aflag);
	public abstract CCompiler setLanguage(String lang);
	public abstract CCompiler addTarget(File target);
	public abstract CCompiler addTarget(String language, File target);
	public abstract CCompiler addTarget(String target);
	public abstract CCompiler addTarget(String language, String target);
	public abstract CCompiler setCPU(String cpuName);
	public abstract CCompiler setArchitecture(String arch);
	public abstract CCompiler setWorkingDirectory(File nwd);
	public abstract CCompiler setWorkingDirectory(String nwd);
	public abstract CCompiler flag(String flag, String...arguments);
	public abstract boolean execute();
}
