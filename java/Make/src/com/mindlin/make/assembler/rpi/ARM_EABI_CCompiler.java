package com.mindlin.make.assembler.rpi;

import java.io.File;
import java.nio.file.Path;
import java.util.Set;

import com.mindlin.make.CCompiler;

public class ARM_EABI_CCompiler extends CCompiler<ARM_EABI_CCompiler> {
	public ARM_EABI_CCompiler(String ARMGNU) {
		super();
		data.put("cmd", new File(ARMGNU + "g++").getAbsolutePath());
	}

	@Override
	public ARM_EABI_CCompiler setOutput(String output) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARM_EABI_CCompiler setOutput(Path output) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARM_EABI_CCompiler setRelativeTo(String dir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARM_EABI_CCompiler includeDir(Path dir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARM_EABI_CCompiler addOptimization(String optimization) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getOptimizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARM_EABI_CCompiler link(boolean aflag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARM_EABI_CCompiler setLanguage(String lang) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARM_EABI_CCompiler setWorkingDirectory(Path nwd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARM_EABI_CCompiler setWorkingDirectory(String nwd) {
		// TODO Auto-generated method stub
		return null;
	}
}