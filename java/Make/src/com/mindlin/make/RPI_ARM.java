package com.mindlin.make;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.Properties;
import util.StrUtils;
import static util.CmdUtil.exec;

public class RPI_ARM implements Compiler {
	static {
		Compilers.register(new RPI_ARM());
	}
	protected final String AFLAGS = "-mcpu=arm1176jzf-s -fpic -ffreestanding -c";
	protected final String CFLAGS = StrUtils.concatWithSpaces("-Wall", "-ffreestanding",
			"-fno-exceptions", "-fno-rtti", "-mcpu=arm1176jzf-s", "-fpic", "-Wextra", "-nostartfiles","-ffreestanding");
	protected final String LDFLAGS = "-O2 -nostdlib";
	protected final String ARMGNU = "/Users/wfeehery17/Dropbox/dev/MMOS/yagarto/bin/arm-none-eabi";
	protected final String[] FILES = new String[]{
			"Kernel",
//			"IO/Terminal",
//			"IO/VGAColor",
//			"util/stddef",
//			"util/stdint",
//			"util/strlen",
//			"util/stdasm",
			"start"
	};
	@Override
	public boolean compile(Properties props) throws IOException, InterruptedException {
		System.out.println("Compiling for architechture: armv6");
		if(!compileBootloader(props, new File(props.<File> getAs("bin.asm"), "start.o")))
			return false;
		System.out.println("Compiling assembly libraries...");
//		compileAsm(props,"memcpy.s");
		System.out.println("Compiling C++...");
		compileCpp(props, "Kernel", false);
//		compileCpp(props, "IO/Terminal", false);
//		compileHeader(props, "IO/VGAColor");
//		compileHeader(props, "util/stddef");
//		compileHeader(props, "util/stdint");
//		compileHeader(props, "util/strlen");
//		compileHeader(props, "util/stdasm");
		System.out.println("Done compiling.");
		return true;
	}
	protected void compileCpp(Properties props, String file) throws IOException, InterruptedException {
		compileCpp(props, file, false);
	}
	protected void compileCpp(Properties props, String file, boolean header) throws IOException,
			InterruptedException {
		File input = new File(props.<File> getAs("src.cpp"), file + ".cpp");
		File output = new File(props.<File> getAs("bin.cpp"), file + ".o");
		output.getParentFile().mkdirs();
		if (header) {
			File pch = compileHeader(props, file);
			compileCpp(props, input, pch, output);
		} else {
			compileCpp(props, input, output);
		}
	}

	protected void compileCpp(Properties props, File input, File output) throws IOException,
			InterruptedException {
		exec(props,
				StrUtils.concatWithSpaces(ARMGNU + "-g++ ", CFLAGS, input.getAbsolutePath(), "-o",
						output.getAbsolutePath()));
	}

	protected void compileCpp(Properties props, File input, File header, File output) throws IOException,
			InterruptedException {
		exec(props, StrUtils.concatWithSpaces(ARMGNU + "-g++", CFLAGS, input.getAbsolutePath(),
//				"-include-pch", header.getAbsolutePath(),
				"-o", output.getAbsolutePath()));
	}

	protected void compileC(Properties props, File input, File output) throws IOException,
			InterruptedException {
		exec(props, StrUtils.concatWithSpaces(ARMGNU + "-gcc", CFLAGS, input.getAbsolutePath(), "-o", output.getAbsolutePath()));
	}

	protected File compileHeader(Properties props, String file) throws IOException, InterruptedException {
		File input = new File(props.<File> getAs("src.cpp"), file + ".h");
		File output = new File(props.<File> getAs("bin.cpp"), file + ".o");
		output.getParentFile().mkdirs();
		compileC(props, input, output);
		return output;
	}

	protected void compileHeader(Properties props, File input, File output) throws IOException,
			InterruptedException {
		exec(props, StrUtils.concatWithSpaces(ARMGNU + "-g++", CFLAGS, input.getAbsolutePath(), "-o", output.getAbsolutePath()));
	}
	protected void compileAsm(Properties props, File input, File output) throws IOException, InterruptedException {
		exec(props, StrUtils.concatWithSpaces(ARMGNU+"-gcc",AFLAGS,input.getAbsolutePath(),"-o",output.getAbsolutePath()));
	}
	@Override
	public boolean compileBootloader(Properties props, File output) {
		try {
			compileAsm(props, new File(props.<File>getAs("src.asm"),"rpi.s"), output);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean accept(String os, String arch) {
		return arch.toLowerCase().equals("armv6");
	}

	@Override
	public boolean link(Properties props) {
		String includes=StrUtils.concatWithSpaces((s)->{
			List<String> output = new ArrayList<String>(2);
			File f=new File(props.<File>getAs("bin.cpp"),s+".o"),
//				g=new File(props.<File>getAs("bin.cpp"),s+"_header.o"),
				h=new File(props.<File>getAs("bin.asm"),s+".o");
			if(f.exists())
				output.add(f.getAbsolutePath());
//			if(g.exists())
//				output.add(g.getAbsolutePath());
			if(h.exists())
				output.add(h.getAbsolutePath());
			return StrUtils.concatWithSpaces(output);
		}, FILES);
		System.out.println("Linking...");
		try {
			System.out.println("\tProducing ELF & map file...");
			//make elf file
			exec(props, ARMGNU+"-ld","--no-undefined ",includes,"-Map",props.<File>getAs("bin.map").getAbsolutePath(),"-o",props.<File>getAs("bin.elf").getAbsolutePath(),"-T",new File(props.<File>getAs("src.ld"),"rpi.ld").getAbsolutePath(),LDFLAGS);
			System.out.println("\tProducing listing...");
			//make listing file
			exec(props, props.<File>getAs("bin.listing"), ARMGNU+"-objdump","-d",props.<File>getAs("bin.elf").getAbsolutePath());
			//make img file
			System.out.println("\tProducing image...");
			exec(props, ARMGNU+"-objcopy",props.<File>getAs("bin.elf").getAbsolutePath(),"-O","binary",props.<File>getAs("out").getAbsolutePath());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("Done linking.");
		return true;
	}
	@Override
	public boolean check(Properties props) throws IOException {
		System.out.println("Checking ELF");
		if(props.<File>getAs("bin.elf").exists()) {
			System.out.println("ELF created successfully.");
		} else {
			System.err.println("Failed to produce ELF!");
			System.exit(-1);
		}
		System.out.println("Checking IMG");
		if(props.<File>getAs("out").exists() && props.<File>getAs("out").length()>0) {
			System.out.println("Image created successfully.");
		}else {
			System.err.println("Failed to produce Image!");
			System.exit(-1);
		}
		return true;
	}

}
