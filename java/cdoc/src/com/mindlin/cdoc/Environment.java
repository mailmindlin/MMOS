package com.mindlin.cdoc;

import java.util.concurrent.ConcurrentHashMap;

public class Environment {
	protected ConcurrentHashMap<String, String> definitions = new ConcurrentHashMap<String, String>();
	protected ConcurrentHashMap<String, String> functions = new ConcurrentHashMap<String, String>();
}
