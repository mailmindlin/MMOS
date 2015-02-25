package com.mindlin.make.assembler.rpi;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;

import com.mindlin.make.Archiver;

public class ARM_EABI_Archiver extends Archiver<ARM_EABI_Archiver> {

	public ARM_EABI_Archiver(String ARMGNU) {
		super();
		data.put("cmd", new File(ARMGNU + "ar").getAbsolutePath()+" rvs");
	}

	@Override
	public ARM_EABI_Archiver setOutput(String output) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARM_EABI_Archiver setOutput(Path output) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARM_EABI_Archiver addTarget(Path target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARM_EABI_Archiver addTarget(String target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARM_EABI_Archiver setRelativeTo(String dir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARM_EABI_Archiver setRelativeTo(Path dir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARM_EABI_Archiver includeDir(Path dir) throws IllegalStateException, FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARM_EABI_Archiver includeDir(String dir) throws IllegalStateException, FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ARM_EABI_Archiver setOutput(File output) {
		// TODO Auto-generated method stub
		return null;
	}
}