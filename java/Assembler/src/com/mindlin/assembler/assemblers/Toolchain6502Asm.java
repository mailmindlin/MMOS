package com.mindlin.assembler.assemblers;


public class Toolchain6502Asm implements Toolchain {
	protected static final String[] registers = new String[]{"A","X","Y","S"};
	@Override
	public String convert(Instruction x, String... ps) {
		switch(x) {
			case LOAD_REG_IMMEDIATE:
				return "LD"+reg(ps[0])+" "+ps[1];
			default:
				throw new IllegalStateException("Unknown instruction "+x.toString());
		}
	}
	protected String reg(String num) {
		return registers[Integer.parseInt(num)];
	}
}
