package com.mindlin.make;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONArray;

import util.FileUtils;
import util.Properties;
import util.StrUtils;
import util.SystemProperty;

public class Executor {
	public static void emptyDir(File dir) {
		if (!dir.exists())
			return;
		for (File child : dir.listFiles()) {
			if (child.isDirectory())
				emptyDir(child);
			child.delete();
		}
	}

	public static Path makePath(Path src, String... after) {
		String tmp = StrUtils.concat((i) -> (i + File.separator), after);
		return src.resolve(tmp.substring(0, tmp.length() - File.separator.length()));
	}

	public static boolean init(Properties props) {
		props.getFile("bin").mkdirs();
		props.getFile("bin.asm").mkdirs();
		props.getFile("bin.cpp").mkdirs();
		props.getFile("bin.java").mkdirs();
		return true;
	}

	public static boolean clean(Properties props) {
		List<File> bins = Arrays.asList(props.getFile("bin.cpp"), props.getFile("bin.asm"));
		for (File bin : bins)
			emptyDir(bin);
		File me = SystemProperty.whereAmI();
		FileUtils.deleteIfMatches((f) -> ((!f.isDirectory()) && (!f.equals(me))), props.getFile("bin"));
		return true;
	}

	public static boolean runTask(Properties props, Compiler compiler, String command) {
		System.out.println(":" + command.trim());
		if (command.equals("about")) {
			System.out.println("Version " + Main.OS_VERSION + " of MMOS, written by Mailmindlin, 2015.");
			System.out.println("Compiler (this piece of software) version " + Main.VERSION);
			System.out.println("Assembly source: " + props.getFile("src.asm"));
			System.out.println("C++ source: " + props.getFile("src.cpp"));
			System.out.println("Java source: " + props.getFile("src.java"));
			System.out.println("Assembly bin: " + props.getFile("bin.asm"));
			System.out.println("C++ bin: " + props.getFile("bin.cpp"));
			System.out.println("Java bin: " + props.getFile("bin.java"));
			System.out.println("\nSystem properties: ");
			for (Entry<Object, Object> prop : (System.getProperties().entrySet())) {
				System.out.println(prop.getKey() + ": " + prop.getValue());
			}
		} else if (command.equals("clean"))
			return clean(props);
		else if (command.equals("compileASM"))
			return compileAsm(props, compiler);
		else if (command.equals("compile"))
			return compile(props, compiler);
		else if (command.equals("build"))
			return runTask(props,compiler,"init")
				&& runTask(props,compiler,"compile")
				&& runTask(props,compiler,"link");
		else if (command.equals("link"))
			return link(props, compiler);
		else if (command.equals("init"))
			return init(props);
		return false;
	}

	public static boolean compileAsm(Properties props, Compiler compiler) {
		if (!props.containsKey("libraries"))
			props.put("libraries", new JSONArray());
		JSONArray libraries = props.<JSONArray> getAs("libraries");
		{
			Path memset = makePath(props.getPath("bin.asm"), "memset.o");
			if (assemble(compiler, makePath(props.getPath("src.asm"), "memset.s"), memset))
				if (!libraries.contains(memset))
					libraries.put(memset);
				else {
					System.err.println("Error compiling library: memset");
					return false;
				}
		}
		{
			Path memcpy = makePath(props.getPath("bin.asm"), "memcpy.o");
			if (assemble(compiler, makePath(props.getPath("src.asm"), "memcpy.s"), memcpy))
				if (!libraries.contains(memcpy))
					libraries.put(memcpy);
				else {
					System.err.println("Error compiling library: memcpy");
					return false;
				}
		}
		System.out.println("Successfully made libraries: "
				+ StrUtils.concatWithSpaces(StrUtils.toStringArray(libraries)));
		return true;
	}

	public static boolean compile(Properties props, Compiler compiler) {
		boolean success = true;
		success = success | runTask(props, compiler, "compileASM");
		JSONArray libraries = props.<JSONArray> getAs("libraries");
		{
			Path stdLib = makePath(props.getPath("bin.cpp"), "libstd.a");
			if (compileLibrary(compiler, "std",
					makePath(props.getPath("src.cpp"), "std", "stdlib.cpp"),
					makePath(props.getPath("bin.cpp"), "stdlib.o"), stdLib, new Path[] {}))
				if (!libraries.contains(stdLib))
					libraries.put(stdLib);
				else {
					System.err.println("Error compiling library: std");
					return false;
				}
		}
		/**/
		{
			Path newLib = makePath(props.getPath("bin.cpp"), "newlib.o");
			if (compileCLibrary(compiler, "newlib",
					makePath(props.getPath("src.cpp"), "newlib", "newlib.c"),
					makePath(props.getPath("bin.cpp"), "newlib.o"), newLib, new Path[] {}))
				if (!libraries.contains(newLib))
					libraries.put(newLib);
				else {
					System.err.println("Error compiling library: newlib");
					return false;
				}
		}/**/
		{
			Path bootloader = makePath(props.getPath("bin.asm"), "bootloader.o");
			if (assemble(compiler, makePath(props.getPath("src.asm"), "start.s"), bootloader))
				if (!libraries.contains(bootloader))
					libraries.put(bootloader);
				else {
					System.err.println("Error compiling bootloader");
					return false;
				}
		}
		return success;
	}

	public static boolean link(Properties props, Compiler compiler) {
		// compile kernel (ELF)
		Path elf = props.getPath("bin.elf");
		CCompiler<?> cc = compiler.CC().enableStatistics(true).setLanguage("gnu++11")
				.addTarget(makePath(props.getPath("src.cpp"), "Kernel.cpp")).setOutput(elf)
				.includeDir(props.getPath("bin.cpp")).includeDir(props.getPath("bin.asm")).flag("fPIC")
				.flag("Wall").flag("ffreestanding").flag("fno-rtti").flag("Wextra")
				.flag("nostartfiles").flag("fexceptions")
				.setLinkerScript(makePath(props.getPath("src.ld"), "rpi.ld"))
				.ldFlag("-Map", props.getPath("bin.map").toString()).ldFlag("-nostdlib")
				.ldFlag("-static")
				// .ldFlag("--verbose")
				.ldFlag("--gc-sections")
		// .ldFlag("--no-undefined")
		;

		props.<JSONArray> getAs("libraries").forEach((l) -> (cc.addTarget((Path) l)));
		if (!cc.execute())
			return false;
		compiler.objdump(elf, props.getPath("bin.listing"));
		compiler.objcopy(elf, props.getPath("out"));
		return true;
	}

	public static boolean check(Properties props) {
		return false;
	}

	protected static boolean assemble(Compiler compiler, Path input, Path output) {
		System.out.println("Assembling: " + output.toFile().getName());
		Assembler<?> assembler = compiler.AS();
		boolean success = assembler.addTarget(input).setOutput(output).flag("fpic")
		// .flag("c")
				.execute();
		return success;
	}

	protected static boolean compileLibrary(Compiler compiler, String name, Path input, Path temp,
			Path output, Path[] includes) {
		System.out.println("Compiling library: " + name);
		CCompiler<?> cc = compiler.CC().define("__LIB_" + name).addTarget(input).setOutput(temp)
				.setLanguage("gnu++11").showWarnings(true).flag("ffreestanding").flag("fno-rtti")
				.flag("fPIC").flag("Wextra").flag("nostartfiles").flag("fexceptions").flag("static")
				.flag("Wunused-parameter")
				// .flag("v")
				.link(false);
		for (Path include : includes)
			cc.addTarget(include);
		boolean success = cc.execute();
		if (!success)
			return false;
		success = compiler.AR().setOutput(output).addTarget(temp).execute();
		return success;
	}

	protected static boolean compileCLibrary(Compiler compiler, String name, Path input, Path temp,
			Path output, Path[] includes) {
		System.out.println("Compiling library: " + name);
		CCompiler<?> cc = compiler.CC("C").define("__LIB_" + name).addTarget(input).setOutput(temp)
				.setLanguage("c11")
				// .showWarnings(true)
				.flag("ffreestanding").flag("fPIC").flag("Wextra").flag("nostartfiles")
				.flag("fexceptions").flag("static")
				// .flag("v")
				.suppressWarnings(true).link(false);
		for (Path include : includes)
			cc.addTarget(include);
		boolean success = cc.execute();
		if (!success)
			return false;
		/*
		 * success=compiler.AR() .setOutput(output) .addTarget(temp) .execute();
		 */
		return success;
	}
}
