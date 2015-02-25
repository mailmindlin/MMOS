package com.mindlin.make;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public interface StdCommand<IMPL extends StdCommand<IMPL>> {
	/**
	 * Set the output location
	 * @param output new file location
	 * @return self
	 */
	IMPL setOutput(String output);
	IMPL setOutput(Path output);
	IMPL addTarget(Path target);
	IMPL addTarget(String target);
	IMPL setRelativeTo(String dir);
	IMPL setArchitecture(String arch);
	IMPL setCPU(String cpuName);
	IMPL setFPU(String fpuName);
	IMPL setGPU(String gpuName);
	IMPL flag(String flag, String... arguments);
	IMPL includeDir(Path dir);
	IMPL includeDir(String dir);
	IMPL setBaseDir(Path dir);
	Path getBaseDir();
	String[] getCommand();
	default Path resolve(String s) {
		if(getBaseDir()!=null)
			return getBaseDir().resolve(s);
		else
			return FileSystems.getDefault().getPath(s);
	}
	default Path resolve(Path p) {
		if(getBaseDir()!=null &&(!p.isAbsolute()))
			return getBaseDir().resolve(p);
		else
			return p;
	}
	boolean execute();
}
