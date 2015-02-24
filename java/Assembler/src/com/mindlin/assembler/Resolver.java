package com.mindlin.assembler;

import java.io.File;
import java.util.ArrayList;


public class Resolver {
	protected final ArrayList<File> includes = new ArrayList<File>();
	public Resolver() {
	}
	public Resolver add(File dir) {
		includes.add(dir);
		return this;
	}
}
