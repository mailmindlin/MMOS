package compilers;

import java.io.File;

public class Win_8664 implements Compiler {
	static {
		Compilers.register(new Win_8664());
	}
	@Override
	public void compileCPP(File input, File output) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void compileBootloader(File output) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean accept(String os, String arch) {
		// TODO Auto-generated method stub
		return false;
	}

}
