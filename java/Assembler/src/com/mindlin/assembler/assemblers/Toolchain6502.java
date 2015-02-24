package com.mindlin.assembler.assemblers;

import java.io.ByteArrayOutputStream;

//Registers: A,X,Y,S
public class Toolchain6502 implements Toolchain {
	@Override
	public byte[] convert(Instruction x, String... ps) {
		switch (x) {
		case LOAD_REG_IMMEDIATE: {
			Register reg = reg(ps[0]);
			byte val = addr(ps[1])[0];
			switch (reg) {
			case A:
				return bcat(0xA9, val);
			case X:
				return bcat(0xA2, val);
			case Y:
				return bcat(0xA0, val);
			default:
				throw new IllegalArgumentException("Invalid register " + reg + "!");
			}
		}
		case LOAD_REG_0PAGE: {
			Register reg = reg(ps[0]);
			byte addr = addr(ps[1])[0];
			switch (reg) {
			case A:
				return bcat(0xA5, addr);
			case X:
				return bcat(0xA6, addr);
			case Y:
				return bcat(0xA4, addr);
			default:
				throw new IllegalArgumentException("Invalid register " + reg + "!");
			}
		}
		case LOAD_REG_0PAGE_REG: {
			Register reg1 = reg(ps[0]);
			Register reg2 = reg(ps[2]);
			byte addr = addr(ps[1])[0];
			switch (reg1) {
			case A:
				if (reg2 == Register.X)
					return bcat(0xB5, addr);
				else
					throw new IllegalArgumentException("Invalid instruction: LDA " + ps[1] + ","
							+ reg2.toString());
			case X:
				if (reg2 == Register.Y)
					return bcat(0xB6, addr);
				else
					throw new IllegalArgumentException("Invalid instruction: LDX " + ps[1] + ","
							+ reg2.toString());
			case Y:
				if (reg2 == Register.X)
					return bcat(0xB4, addr);
				else
					throw new IllegalArgumentException("Invalid instruction: LDY " + ps[1] + ","
							+ reg2.toString());
			default:
				throw new IllegalArgumentException("Invalid register " + reg1 + "!");
			}
		}
		case LOAD_REG_ABS: {
			Register reg = reg(ps[0]);
			byte addr = addr(ps[1])[0];
			switch (reg) {
			case A:
				return bcat(0xAD, addr);
			case X:
				return bcat(0xAE, addr);
			case Y:
				return bcat(0xAC, addr);
			default:
				throw new IllegalArgumentException("Invalid register " + reg + "!");
			}
		}
		case LOAD_REG_ABS_REG: {
			Register reg = reg(ps[0]);
			Register reg2=reg(ps[2]);
			byte addr = addr(ps[1])[0];
			switch (reg) {
			case A:
				if(reg2==Register.X)
					return bcat(0xBD, addr);
				else if(reg2==Register.Y)
					return bcat(0xB9, addr);
			case X:
				if(reg2!=Register.Y)
					throw new IllegalArgumentException("Invalid instruction: LDX " + ps[1] + "," + reg2.toString());
				return bcat(0xBE, addr);
			case Y:
				if(reg2!=Register.X)
					throw new IllegalArgumentException("Invalid instruction: LDX " + ps[1] + "," + reg2.toString());
				return bcat(0xBC, addr);
			default:
				throw new IllegalArgumentException("Invalid register " + reg + "!");
			}
		}
		case LOAD_REG_INDREG:
			//only valid for LDA (value,X)
			if (reg(ps[0]) == Register.A && reg(ps[2]) == Register.X)
				return bcat(0xA1, addr(ps[1])[0]);
			throw new IllegalArgumentException("Invalid register " + reg(ps[0]) + "!");
		case LOAD_REG_IND_REG:
			//only valid for LDA (value),y
			if (reg(ps[0]) == Register.A && reg(ps[2]) == Register.Y)
				return bcat(0xB1, addr(ps[1])[0]);
			throw new IllegalArgumentException("Invalid register combo " + reg(ps[0]) + " & "+reg(ps[2])+"!");
		case STORE_REG_0PAGE: {
			Register reg = reg(ps[0]);
			byte addr = addr(ps[1])[0];
			switch (reg) {
			case A:
				return bcat(0x85, addr);
			case X:
				return bcat(0x86, addr);
			case Y:
				return bcat(0x84, addr);
			default:
				throw new IllegalArgumentException("Invalid register " + reg + "!");
			}
		}
		case STORE_REG_0PAGE_REG: {
			Register reg1 = reg(ps[0]);
			Register reg2 = reg(ps[2]);
			byte addr = addr(ps[1])[0];
			switch (reg1) {
			case A:
				if (reg2 == Register.X)
					return bcat(0x95, addr);
				else
					throw new IllegalArgumentException("Invalid instruction: STA " + ps[1] + ","
							+ reg2.toString());
			case X:
				if (reg2 == Register.Y)
					return bcat(0x96, addr);
				else
					throw new IllegalArgumentException("Invalid instruction: STX " + ps[1] + ","
							+ reg2.toString());
			case Y:
				if (reg2 == Register.X)
					return bcat(0x94, addr);
				else
					throw new IllegalArgumentException("Invalid instruction: STY " + ps[1] + ","
							+ reg2.toString());
			default:
				throw new IllegalArgumentException("Invalid register " + reg1 + "!");
			}
		}
		case STORE_REG_ABS: {
			Register reg = reg(ps[0]);
			byte addr = addr(ps[1])[0];
			switch (reg) {
			case A:
				return bcat(0x8D, addr);
			case X:
				return bcat(0x8E, addr);
			case Y:
				return bcat(0x8C, addr);
			default:
				throw new IllegalArgumentException("Invalid register " + reg + "!");
			}
		}
		case STORE_REG_ABS_REG: {
			Register reg = reg(ps[0]);
			Register reg2=reg(ps[2]);
			byte addr = addr(ps[1])[0];
			switch (reg) {
			case A:
				if(reg2==Register.X)
					return bcat(0x9D, addr);
				else if(reg2==Register.Y)
					return bcat(0x99, addr);
				else
					throw new IllegalArgumentException("Invalid instruction: STA " + ps[1] + "," + reg2.toString());
			default:
				throw new IllegalArgumentException("Invalid register " + reg + "!");
			}
		}
		case STORE_REG_INDREG:
			//only valid for STA (value,X)
			if (reg(ps[0]) == Register.A && reg(ps[2]) == Register.X)
				return bcat(0x81, addr(ps[1])[0]);
			throw new IllegalArgumentException("Invalid register " + reg(ps[0]) + "!");
		case STORE_REG_IND_REG:
			//only valid for STA (value),y
			if (reg(ps[0]) == Register.A && reg(ps[2]) == Register.Y)
				return bcat(0x91, addr(ps[1])[0]);
			throw new IllegalArgumentException("Invalid register combo " + reg(ps[0]) + " & "+reg(ps[2])+"!");
		case TRANSFER:{
			Register reg1 = reg(ps[0]);
			Register reg2 = reg(ps[1]);
			switch(reg1) {
			case A:
				switch(reg2) {
				case X:
					return bcat(0xAA);
				case Y:
					return bcat(0xA8);
				default:
					throw new IllegalArgumentException("Invalid instruction: TA"+reg2.toString());
				}
			case X:
				switch(reg2) {
				case A:
					return bcat(0x8A);
				case S:
					return bcat(0x9A);
				default:
					throw new IllegalArgumentException("Invalid instruction: TA"+reg2.toString());
				}
			case Y:
				if(reg2!=Register.A)
					throw new IllegalArgumentException("Invalid instruction: TY"+reg2.toString());
				return bcat(0x98);
			case S:
				if(reg2!=Register.X)
					throw new IllegalArgumentException("Invalid instruction: TS"+reg2.toString());
				return bcat(0xBA);
			}
		}
		default:
			throw new IllegalStateException("Unknown instruction " + x.toString());
		}
	}

	protected Register reg(String num) {
		try {
			return Register.values()[Integer.parseInt(num)];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Invalid Register: " + num);
		}
	}

	protected byte[] addr(String param) {
		char[] tmp;
		if (param.startsWith("$"))
			tmp = param.substring(1).toCharArray();
		else
			tmp = param.toCharArray();

		byte[] data = new byte[tmp.length / 2];
		for (int i = 0; i < tmp.length; i += 2)
			data[i / 2] = (byte) ((Character.digit(tmp[i], 16) << 4) + Character.digit(tmp[i + 1], 16));
		return data;
	}

	protected byte[] bcat(Object... bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (Object e : bytes)
			if (e instanceof byte[])
				for (byte b : ((byte[]) e))
					baos.write(b);
			else
				baos.write((byte) (0xFF & ((int)e)));
		return baos.toByteArray();
	}

	protected static enum Register {
		A, X, Y, S;
	}
}
