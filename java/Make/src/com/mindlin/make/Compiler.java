package com.mindlin.make;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import util.FileUtils;
import util.Properties;

public interface Compiler {
	public boolean accept(String os, String arch);
	public CCompiler CC();
	public Archiver AR();
	public Linker LD();
}
