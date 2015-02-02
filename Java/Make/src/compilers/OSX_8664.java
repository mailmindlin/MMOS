package compilers;
import java.io.File;


public class OSX_8664 implements Compiler {
	static {
		Compilers.register(new OSX_8664());
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
