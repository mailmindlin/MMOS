package com.mindlin.make;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import util.FileUtils;
import util.Properties;
import util.StrUtils;

public class Executor {
	public static void emptyDir(File dir) {
		if(!dir.exists())
			return;
		for(File child : dir.listFiles()) {
			if(child.isDirectory())
				emptyDir(child);
			child.delete();
		}
	}
	public static File makeFile(File src, String... after) {
		String tmp = StrUtils.concat((i)->(i+File.separator), after);
		return new File(src,tmp.substring(0,tmp.length()-File.separator.length()));
	}
	
	public static boolean init(Properties props) {
		props.<File>getAs("bin").mkdirs();
		props.<File>getAs("bin.asm").mkdirs();
		props.<File>getAs("bin.cpp").mkdirs();
		props.<File>getAs("bin.java").mkdirs();
		return true;
	}
	public static boolean clean(Properties props) {
		List<File> bins = Arrays.asList(props.<File>getAs("bin.java"), props.<File>getAs("bin.cpp"), props.<File>getAs("bin.asm"));
		for(File bin:bins)
			emptyDir(bin);
		FileUtils.deleteIfMatches((f)->(!f.isDirectory()), props.<File>getAs("bin"));
		return true;
	}
	public static boolean runTask(Properties props, Compiler compiler, String command) {
		System.out.println(":"+command.trim());
		if(command.equals("clean"))
			return clean(props);
		else if(command.equals("compileLibs"))
			return compileLibs(props, compiler);
		else if(command.equals("compile"))
			return compile(props, compiler);
		return false;
	}
	public static boolean compileLibs(Properties props, Compiler compiler) {
		compileLibrary(props, compiler, "std",makeFile(props.getFile("src.cpp"),"std","stdlib.cpp"),makeFile(props.getFile("bin.cpp"),"stdlib.o"),makeFile(props.getFile("bin.cpp"),"libstd.a"));
		return false;
	}
	public static boolean compile(Properties props, Compiler compiler) {
		runTask(props, compiler,"compileLibs");
		return false;
	}
	public static boolean link(Properties props) {
		
		return false;
	}
	public static boolean check(Properties props){
		return false;
	}
	protected static boolean assemble(Properties props, Compiler compiler, File input, File output) {
		boolean success = true;
		return success;
	}
	protected static boolean compileLibrary(Properties props, Compiler compiler, String name, Path input, Path temp, Path output) {
		boolean success=compiler.CC()
			.define("__LIB_"+name)
			.addTarget(input)
			.setOutput(temp)
			.setLanguage("gnu++11")
//			.flag("Wall")
			.flag("ffreestanding")
			.flag("fno-exceptions")
			.flag("fno-rtti")
			.setCPU("arm1176jzf-s")
			.flag("fpic")
			.flag("Wextra")
			.flag("nostartfiles")
			.flag("Wpointer-arith")
			.flag("w")
			.flag("fexceptions")
			.link(false)
			.execute();
		if(!success)
			return false;
		success=compiler.AR()
				.setOutput(output)
				.addTarget(temp)
				.execute();
		return success;
	}
}
