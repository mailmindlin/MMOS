package com.mindlin.assembler.assemblers;

public interface Toolchain {
	public Object convert(Instruction x, String...params);
}
