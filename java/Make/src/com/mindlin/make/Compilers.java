package com.mindlin.make;
import java.util.ArrayList;


public class Compilers {
	public static void loadClasses() throws ClassNotFoundException {
		Class.forName("com.mindlin.make.RPI_ARM");
	}
	protected static ArrayList<Compiler> compilers = new ArrayList<Compiler>();
	public static void register(Compiler c) {
		compilers.add(c);
	}
	public static Compiler getCompiler(String os, String arch) {
		for(Compiler c : compilers) {
			if(c.accept(os, arch)) {
				System.out.println("Using compiler '"+c.getClass().getName()+"'");
				return c;
			}
		}
		return null;
	}
}
