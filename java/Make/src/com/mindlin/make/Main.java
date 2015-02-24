package com.mindlin.make;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.Set;

import util.Properties;
import util.SymlinkResolver;
import util.SystemProperty;

public class Main {
	final static String OS_VERSION="experimental pre-alpha 0.0.1";
	final static String VERSION="alpha 0.0.1";
	final static Properties properties = new Properties();
	static Compiler compiler;
	final static ArrayList<String> tasks = new ArrayList<String>();
	final static Set<String> finished=new HashSet<String>();
	public static void main(String[] fred) throws IOException {
		parseArguments(fred);
		inferUnset();
		ListIterator<String> taskIterator = tasks.listIterator();
		while(taskIterator.hasNext())
			exec(taskIterator.next());
		System.out.println("Bye!");
	}
	public static void parseArguments(String[] argv) {
		for(int i=0;i<argv.length;i++) {
			String command = argv[i];
			if(command.equals("help")) {
				if(i+1<argv.length)
					printHelp(argv[++i]);
				else
					printStdHelp();
				System.exit(0);
			} else if(command.equals("-arch")) {
				properties.put("arch",argv[++i]);
			} else if(command.equals("-targ"))
				properties.put("target", argv[++i]);
			else if(command.equals("--src-asm"))
				properties.put("src.asm",new File(argv[++i]));
			else if(command.equals("--src-c++"))
				properties.put("src.cpp",new File(argv[++i]));
			else if(command.equals("--src-java"))
				properties.put("src.java",new File(argv[++i]));
			else if(command.equals("--src-ld"))
				properties.put("src.ld",new File(argv[++i]));
			else if(command.equals("--bin"))
				properties.put("bin",new File(argv[++i]));
			else if(command.equals("--bin-asm"))
				properties.put("bin.asm",new File(argv[++i]));
			else if(command.equals("--bin-c++"))
				properties.put("bin.cpp",new File(argv[++i]));
			else if(command.equals("--bin-java"))
				properties.put("bin.java",new File(argv[++i]));
			else if(command.equals("-o"))
				properties.put("out",new File(argv[++i]));
			else if(command.equals("-v"))
				properties.put("verbose", true);
			else
				tasks.add(argv[i]);
		}
	}
	public static void inferUnset() throws IOException {
		File bin = properties.getOrUse("bin", new File("bin").getAbsoluteFile());
		try {
			Compilers.loadClasses();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		properties.addInference("TARCH", "armv6")
			.addInference("out",new File(bin, "kernel.img"))
			.addInference("bin",bin)
			.addInference("bin.asm",SymlinkResolver.resolve(new File(bin, "asm").getAbsoluteFile()))
			.addInference("bin.cpp", SymlinkResolver.resolve(new File(bin, "cpp").getAbsoluteFile()))
			.addInference("bin.java", SymlinkResolver.resolve(new File(bin, "java").getAbsoluteFile()))
			.addInference("src.asm",SymlinkResolver.resolve(new File("asm").getAbsoluteFile()))
			.addInference("src.ld",SymlinkResolver.resolve(new File("linker").getAbsoluteFile()))
			.addInference("src.cpp", SymlinkResolver.resolve(new File("cpp").getAbsoluteFile()))
			.addInference("src.java", SymlinkResolver.resolve(new File("java").getAbsoluteFile()))
			.addInference("kernel", SymlinkResolver.resolve(new File("output/kernel").getAbsoluteFile()))
			.addInference("bin.listing", new File(bin,"kernel.list"))
			.addInference("bin.elf", new File(bin,"kernel.elf"))
			.addInference("bin.map", new File(bin,"kernel.map"))
			.addInference("target.arch","armv6")
			.addInference("target","rpi")
			.addInference("CYGWIN",false)
			.addInference("CARCH",SystemProperty.OS_ARCH.get("x86_64"))
			.addInference("COS",SystemProperty.OS_NAME.get("Windows"))
			.addInference("verbose",false)
			.inferAllUnset()
			.clearInferences();
		if(properties.getBool("verbose"))
			properties.forEach((a,b)->{
				System.out.print("\t"+a.toString()+":"+b.toString());
				if(b instanceof File)
					System.out.print("->"+(((File) b).exists()?"exists":"doesn't exist"));
				System.out.println();
			});
	}
	public static void exec(String cmd) {
		final String command=cmd.toLowerCase().trim();
		System.out.println("Make: "+cmd);
		if(command.equals("build")) {
			exec("compile");
			exec("link");
			exec("test");
		}else if(command.equals("init") && (!finished.contains(command))) {
			properties.<Compiler>getAs("compiler").init(properties);
		}else if(command.equals("compile")) {
			exec("init");
			try {
				properties.<Compiler>getAs("compiler").compile(properties);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}else if(command.equals("link")) {
			try {
				properties.<Compiler>getAs("compiler").link(properties);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}else if(command.equals("clean")) {
			properties.<Compiler>getAs("compiler").clean(properties);
		}else if(command.equals("test")) {
			System.err.println("Testing has not yet been integrated.");
		}else if(command.equals("check")) {
			try {
				properties.<Compiler>getAs("compiler").check(properties);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(command.equals("about")) {
			System.out.println("Version "+OS_VERSION+" of MMOS, written by Mailmindlin, 2015.");
			System.out.println("Compiler (this piece of software) version " + VERSION);
			System.out.println("Assembly source: "+properties.<File>getAs("src.asm"));
			System.out.println("C++ source: "+properties.<File>getAs("src.cpp"));
			System.out.println("Java source: "+properties.<File>getAs("src.java"));
			System.out.println("Assembly bin: "+properties.<File>getAs("bin.asm"));
			System.out.println("C++ bin: "+properties.<File>getAs("bin.cpp"));
			System.out.println("Java bin: "+properties.<File>getAs("bin.java"));
			System.out.println("\nSystem properties: ");
			for(Entry<Object, Object> prop: (System.getProperties().entrySet())) {
				System.out.println(prop.getKey()+": "+prop.getValue());
			}
		}else
			return;
		finished.add(command);
	}
	public static void printHelp(String command) {
		System.out.println("Help for command: "+command);
		if(command.equals("build")) {
			System.out.println("Compiles, links, and tests the code.\n"
					+ "You might want to run \"help [compile/link/test]\".");
		}else if(command.equals("compile")) {
			System.out.println("Compiles the bootloader and c++ code.");
		}else if(command.equals("link")) {
			System.out.println("Links c++ code, then links that to the bootloader.");
		}else if(command.equals("clean")) {
			System.out.println("Removes partially compiled binaries.");
		}else if(command.equals("test")) {
			
		}else if(command.equals("about")) {
			System.out.println("Prints information about");
		}
	}
	public static void printStdHelp() {
		System.out.println("Usage: make <command> [arguments]");
		System.out.println();
		System.out.println("Command       Description");
		System.out.println("build         Compiles, links, and tests the file");
		System.out.println("clean         Removes the precompiled binaries and intermediate files (i.e., non-source files)");
		System.out.println("compile       Compiles the code to the output folder");
		System.out.println("help          Displays this menu");
		System.out.println("link          Links precompiled code");
		System.out.println("test          Tests the output (not yet implemented)");
		System.out.println("about         Echo the version of this build");
		System.out.println("\n");
		System.out.println("Variable     Default Value	Description");
		System.out.println("TARCH        armv6           Target architecture to compile for");
		System.out.println("BIN          bin/            Where to put the compiled binaries/intermediate files");
		System.out.println("OUT          kern            Output file (compiled operating system)");
		System.out.println();
		System.out.println("Arguments:");
		System.out.println("Flag		Description");
		System.out.println("-arch		Target architechture");
		System.out.println("-targ		target system");
		System.out.println("--src-asm	Assembly source folder");
		System.out.println("--src-c++	C++ source folder");
		System.out.println("--src-java	Java source folder");
		System.out.println("--src-ld	Linker script source folder");
		System.out.println("--bin-asm	Assembly bin folder");
		System.out.println("--bin-c++	C++ bin folder");
		System.out.println("--bin-java	Java bin folder");
		System.out.println("--bin		Bin folder");
		System.out.println("-o			IMG output file");
		System.out.println("");
		System.out.println("Use make help <command> for more information about each command");
	}
}