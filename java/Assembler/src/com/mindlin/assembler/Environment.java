package com.mindlin.assembler;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class Environment {
	public final ConcurrentHashMap<String, Object> defines = new ConcurrentHashMap<String, Object>();
	protected ConcurrentHashMap<String, Function> functions = new ConcurrentHashMap<String, Function>();
	public File originDir;
	public Environment(File origin) {
		originDir=origin.getParentFile();
	}
	public void define(String key, String value) {
		defines.put(key, value);
	}
	public boolean isDefined(String key) {
		return defines.containsKey(key);
	}
}
