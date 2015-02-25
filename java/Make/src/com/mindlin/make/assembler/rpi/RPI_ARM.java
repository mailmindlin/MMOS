package com.mindlin.make.assembler.rpi;

import util.StrUtils;

import com.mindlin.make.Archiver;
import com.mindlin.make.Assembler;
import com.mindlin.make.CCompiler;
import com.mindlin.make.Compiler;
import com.mindlin.make.Compilers;
import com.mindlin.make.Linker;

public class RPI_ARM implements Compiler {
	static {
		Compilers.register(new RPI_ARM());
	}
	protected final String AFLAGS = "-mcpu=arm1176jzf-s -fpic -ffreestanding -c";
	protected final String CFLAGS = StrUtils.concatWithSpaces("-Wall", "-ffreestanding",
			"-fno-exceptions", "-fno-rtti", "-mcpu=arm1176jzf-s", "-fpic", "-Wextra", "-nostartfiles",
			"-ffreestanding");
	protected final String LDFLAGS = "-O2 -nostdlib";
	protected final String ARMGNU = "./yagarto/bin/arm-none-eabi-";

	@Override
	public boolean accept(String os, String arch) {
		return arch.toLowerCase().equals("armv6");
	}

	@Override
	public CCompiler<ARM_EABI_CCompiler> CC() {
		return new ARM_EABI_CCompiler(ARMGNU);
	}

	@Override
	public Archiver<ARM_EABI_Archiver> AR() {
		return new ARM_EABI_Archiver(ARMGNU);
	}

	@Override
	public Linker LD() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Assembler<ARM_EABI_Assembler> AS() {
		return new ARM_EABI_Assembler(ARMGNU);
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
