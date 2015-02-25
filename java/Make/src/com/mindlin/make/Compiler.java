package com.mindlin.make;


public interface Compiler {
	public boolean accept(String os, String arch);
	public Archiver<?> AR();
	public Assembler<?> AS();
	public CCompiler<?> CC();
	public Linker<?> LD();
}
