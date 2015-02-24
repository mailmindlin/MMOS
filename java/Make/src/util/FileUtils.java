package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Function;

public class FileUtils {
	public static String readInternal(String path) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(FileUtils.class.getResourceAsStream(path)));
		StringBuilder sb = new StringBuilder();
		String line;
		while((line=br.readLine())!=null)
			sb.append(line);
		return sb.toString();
	}
	public static void deleteIfMatches(Function<File, Boolean> fa, File dir) {
		File[] children = dir.listFiles();
		for(File child:children) {
			if(child.isDirectory())
				deleteIfMatches(fa, child);
			if(fa.apply(child))
				child.delete();
		}
	}
}
