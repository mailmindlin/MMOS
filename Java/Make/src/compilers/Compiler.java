package compilers;
import java.io.File;


public interface Compiler{
	public void compileCPP(File input, File output);
	public void compileBootloader(File output);
	public boolean accept(String os, String arch);
}
