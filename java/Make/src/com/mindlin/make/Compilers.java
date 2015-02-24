package com.mindlin.make;
import java.util.ArrayList;


public class Compilers {
	public static void loadClasses() throws ClassNotFoundException {
//		Class.forName("compilers.OSX_ARM");
		Class.forName("compilers.RPI_ARM");
//		Class.forName("compilers.OSX_8664");
		Class.forName("compilers.Win_ARM");
//		Class.forName("compilers.Win_8664");
//		Class.forName("Linux_ARM");
//		Class.forName("Linux_8664");
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
