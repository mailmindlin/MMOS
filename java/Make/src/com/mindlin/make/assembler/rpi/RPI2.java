package com.mindlin.make.assembler.rpi;

import java.io.File;
import java.nio.file.Path;

import util.CmdUtil;
import util.CmdUtil.Result;
import util.Properties;

import com.mindlin.make.CCompiler;
import com.mindlin.make.Compiler;
import com.mindlin.make.Compilers;

public class RPI2 implements Compiler {
	static {
		Compilers.register(new RPI2());
	}
	protected final String ARMGNU = "yagarto/bin/arm-none-eabi-";
	protected static final String CPU_NAME="cortex-a7";
	protected static final String FPU_NAME="neon-vfpv4";
	protected static final String ARCHITECTURE="armv7-a";

	@Override
	public boolean accept(Properties props) {
		return props.getOrDefault("target", "n/a").equals("rpi2") || props.getString("target.arch").equalsIgnoreCase("armv7");
	}

	@Override
	public ARM_EABI_CppCompiler CC() {
		return new ARM_EABI_CppCompiler(ARMGNU)
			.setCPU(CPU_NAME)
			.setFPU(FPU_NAME)
			.setArchitecture(ARCHITECTURE)
			.ldFlag("-nostdlib");
	}
	@Override
	public CCompiler<?> CC(String language) {
		if(language.equalsIgnoreCase("c++"))
			return CC();
		else
			return new ARM_EABI_CCompiler(ARMGNU)
				.setCPU(CPU_NAME)
				.setFPU(FPU_NAME)
				.setArchitecture(ARCHITECTURE)
				.ldFlag("-nostdlib");
	}

	@Override
	public ARM_EABI_Archiver AR() {
		return new ARM_EABI_Archiver(ARMGNU)
			.setCPU(CPU_NAME)
			.setFPU(FPU_NAME)
			.setArchitecture(ARCHITECTURE);
	}

	@Override
	public ARM_EABI_Linker LD() {
		return new ARM_EABI_Linker(ARMGNU)
		.setCPU(CPU_NAME)
		.setFPU(FPU_NAME)
		.setArchitecture(ARCHITECTURE);
	}
	@Override
	public ARM_EABI_Assembler AS() {
		return new ARM_EABI_Assembler(ARMGNU)
			.setCPU(CPU_NAME)
			.setFPU(FPU_NAME)
			.setArchitecture(ARCHITECTURE);
	}

	@Override
	public boolean objdump(Path elf, Path listing) {
		Result r=CmdUtil.exec(new File(ARMGNU+"objdump").getAbsolutePath()+" -d "+elf.toString()+" > "+listing.toString());
		r.printLog();
		r.printErrorLog();
		return r.wasSuccess();
	}

	@Override
	public boolean objcopy(Path elf, Path image) {
		Result r=CmdUtil.exec(new File(ARMGNU+"objcopy").getAbsolutePath()+" "+elf.toString()+" -O binary "+image.toString());
		r.printLog();
		r.printErrorLog();
		return r.wasSuccess();
	}
	

	/*
	 * @Override public boolean link(Properties props) { String
	 * includes=StrUtils.concatWithSpaces((s)->{ List<String> output = new
	 * ArrayList<String>(2); File f=new
	 * File(props.<File>getAs("bin.cpp"),s+".o"), // g=new
	 * File(props.<File>getAs("bin.cpp"),s+"_header.o"), h=new
	 * File(props.<File>getAs("bin.asm"),s+".o"); if(f.exists())
	 * output.add(f.getAbsolutePath()); // if(g.exists()) //
	 * output.add(g.getAbsolutePath()); if(h.exists())
	 * output.add(h.getAbsolutePath()); return
	 * StrUtils.concatWithSpaces(output); }, FILES);
	 * System.out.println("Linking..."); try {
	 * System.out.println("\tProducing ELF & map file..."); //make elf file
	 * exec(props,
	 * ARMGNU+"-ld","--no-undefined ",includes,"-Map",props.<File>getAs
	 * ("bin.map"
	 * ).getAbsolutePath(),"-o",props.<File>getAs("bin.elf").getAbsolutePath
	 * (),"-T",new
	 * File(props.<File>getAs("src.ld"),"rpi.ld").getAbsolutePath(),LDFLAGS);
	 * System.out.println("\tProducing listing..."); //make listing file
	 * exec(props, props.<File>getAs("bin.listing"),
	 * ARMGNU+"-objdump","-d",props.<File>getAs("bin.elf").getAbsolutePath());
	 * //make img file System.out.println("\tProducing image..."); exec(props,
	 * ARMGNU
	 * +"-objcopy",props.<File>getAs("bin.elf").getAbsolutePath(),"-O","binary"
	 * ,props.<File>getAs("out").getAbsolutePath()); } catch (IOException |
	 * InterruptedException e) { e.printStackTrace(); return false; }
	 * System.out.println("Done linking."); return true; }
	 */
}