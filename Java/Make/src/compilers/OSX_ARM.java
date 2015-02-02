package compilers;
import java.io.File;


public class OSX_ARM implements Compiler{
	static {
		Compilers.register(new OSX_ARM(6));
		Compilers.register(new OSX_ARM(7));
	}
	protected int armv;
	protected OSX_ARM(int armv) {
		this.armv=armv;
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
