package com.mindlin.assembler.assemblers;

public enum Instruction {
	/**
	 * <dl>
	 * <dt>Immediate</dt>
	 * <dd>Load {@code #xx} (hex) into {@code <reg>}</dd>
	 * <dd>{@code LOAD_REG <reg> #xx}</dd>
	 * </dl>
	 */
	LOAD_REG_IMMEDIATE,
	/**
	 * <dl>
	 * <dt>Zero Page</dt>
	 * <dd>Load {@code <reg>} from Zero Page address {@code $xx}</dd>
	 * <dd>{@code LOAD_REG <reg> $xx}</dd>
	 * </dl>
	 */
	LOAD_REG_0PAGE,
	/**
	 * <dl>
	 * <dt>Zero page, other register</dt>
	 * <dd>Load {@code <reg>} from zero page with address calculated from
	 * {@code $xx} + value of register {@code [reg]}.</dd>
	 * <dd>{@code LOAD_REG <reg> $xx,[reg]}</dd>
	 * </dl>
	 */
	LOAD_REG_0PAGE_REG,
	/**
	 * <dl>
	 * <dt>Absolute</dt>
	 * <dd>Load {@code <reg>} from address {@code $xxxx}</dd>
	 * <dd>{@code LOAD_REG <reg> $xxxx}</dd>
	 * </dl>
	 */
	LOAD_REG_ABS,
	/**
	 * <dl>
	 * <dt>Absolute, register</dt>
	 * <dd>Load {@code <reg>} from address calculated from {@code $xxxx} + value
	 * of register {@code [reg]}</dd>
	 * <dd>{@code LOAD_REG <reg> $xxxx, [reg]}</dd>
	 * </dl>
	 */
	LOAD_REG_ABS_REG,
	/**
	 * <dl>
	 * <dt>(Indirect, register)</dt>
	 * <dd>Load {@code <reg>} from address obtained from the address calculated
	 * from "{@code xx} + value of register {@code [reg]}"</dd>
	 * <dd>{@code LOAD_REG <reg> ($xx,[reg])}</dd>
	 * </dl>
	 */
	LOAD_REG_INDREG,
	/**
	 * <dl>
	 * <dt>(Indirect), register</dt>
	 * <dd>Load {@code <reg>} from address obtained from the address calculated
	 * from "the value stored in the address {@code $xx}" + value of register
	 * {@code [reg]}</dd>
	 * <dd>{@code LOAD_REG <reg> ($xx),[reg]}</dd>
	 * </dl>
	 */
	LOAD_REG_IND_REG,
	/**
	 * <dl>
	 * <dt>Zero Page</dt>
	 * <dd>Store {@code <reg>} to Zero Page address {@code $xx}</dd>
	 * <dd>{@code STORE_REG <reg> $xx}</dd>
	 * </dl>
	 */
	STORE_REG_0PAGE,
	/**
	 * <dl>
	 * <dt>Zero page, other register</dt>
	 * <dd>Store {@code <reg>} to zero page with address calculated from
	 * {@code $xx} + value of register {@code [reg]}.</dd>
	 * <dd>{@code STORE_REG <reg> $xx,[reg]}</dd>
	 * </dl>
	 */
	STORE_REG_0PAGE_REG,
	/**
	 * <dl>
	 * <dt>Absolute</dt>
	 * <dd>Store {@code <reg>} to address {@code $xxxx}</dd>
	 * <dd>{@code STORE_REG <reg> $xxxx}</dd>
	 * </dl>
	 */
	STORE_REG_ABS,
	/**
	 * <dl>
	 * <dt>Absolute, register</dt>
	 * <dd>Store {@code <reg>} to address calculated from {@code $xxxx} + value
	 * of register {@code [reg]}</dd>
	 * <dd>{@code STORE_REG <reg> $xxxx, [reg]}</dd>
	 * </dl>
	 */
	STORE_REG_ABS_REG,
	/**
	 * <dl>
	 * <dt>(Indirect, register)</dt>
	 * <dd>Store {@code <reg>} to address obtained from the address calculated
	 * from "{@code xx} + value of register {@code [reg]}"</dd>
	 * <dd>{@code STORE_REG <reg> ($xx,[reg])}</dd>
	 * </dl>
	 */
	STORE_REG_INDREG,
	/**
	 * <dl>
	 * <dt>(Indirect), register</dt>
	 * <dd>Store {@code <reg>} to address obtained from the address calculated
	 * from "the value stored in the address {@code $xx}" + value of register
	 * {@code [reg]}</dd>
	 * <dd>{@code STORE_REG <reg> ($xx),[reg]}</dd>
	 * </dl>
	 */
	STORE_REG_IND_REG,
	/**
	 * <dl>
	 * <dt>Transfer from register {@code <reg1>} to register {@code <reg2>}</dt>
	 * <dd>{@code TRANSFER <reg1> <reg2>}</dd>
	 * </dl>
	 */
	TRANSFER,
	;
}
