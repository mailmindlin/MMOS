import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.fge.largetext.LargeText;
import com.github.fge.largetext.LargeTextException;
import com.github.fge.largetext.LargeTextFactory;


public class Main {
	static final LargeTextFactory factory = LargeTextFactory.defaultFactory();
	static final List<String> bad_extensions = Arrays.asList(".o",".a",".tar",".gz",".DS_STORE",".jar",".elf",".img",".class");
	public static void main(String[] argv) {
		if(argv.length==0) {
			System.out.println("-for [pattern]      Pattern to search for.");
			System.out.println("-literal [string]   String to look for.");
			System.out.println("-start [file/dir]   File to search or directory to search recursively.");
			System.out.println("-fc [pattern]       Pattern to choose which files to search.");
			return;
		}
		Pattern p=null;
		File origin=new File(".");
//		String ext="";
		Pattern fc=Pattern.compile(".*");
		for(int i=0;i<argv.length;i++) {
			String arg=argv[i].toLowerCase().trim();
			if(arg.equals("-for"))
				p=Pattern.compile(argv[++i]);
			else if(arg.equals("-literal"))
				p=Pattern.compile(Pattern.quote(argv[++i]));
			else if(arg.equals("-start"))
				origin=new File(argv[++i]);
			else if(arg.equals("-fc")) {
				fc = Pattern.compile(argv[++i], Pattern.MULTILINE);
				System.out.println("Fc: "+argv[i]);
			}else if(arg.equals("-ext")) {
//				ext=argv[++i];
			}
		}
//		final String x=ext;
		searchDir(origin,p,fc);
	}
	public static void searchDir(File dir, Pattern search, Pattern fc) {
		if(!dir.isDirectory()) {
			searchFile(dir,search);
			return;
		}
		for(File f:dir.listFiles()) {
			if(f.getName().startsWith(".") || isBinary(f))
				continue;
			if(f.isDirectory())
				searchDir(f,search,fc);
			else
				searchFile(f,search);
		}
	}
	public static void searchFile(File f, Pattern search) {
		final long length=f.length();
		if(length==0)
			return;
		String a="Searching: "+f.getPath()+ " ("+fileSize(length)+") ";
		clearLine();
		System.out.print(a);
		try {
			final LargeText tmp = factory.load(f.toPath());
			Matcher m = search.matcher(tmp);
			while(m.find()) {
				writeln("Found match at "+f.getPath()+":"+m.start()+": '"+m.group()+"'",a);
			}
			tmp.close();
		} catch (IOException | LargeTextException e) {
			System.out.println();
			e.printStackTrace();
			System.exit(-1);
		}
	}
	public static void writeln(String s, String bottom) {
		clearLine();
		System.out.println(s);
		System.out.print(bottom);
	}
	public static void clearLine() {
		System.out.print("\r\033[K");
//		System.out.println();
	}
	public static String fileSize(long size) {
		String[] sizes={"B","KB","MB","GB","TB"};
		int order = (int)Math.floor(Math.log(size)/Math.log(1024));
		if(order==0)
			return String.format("%d %s", size,sizes[order]);
		return String.format("%.5g %s", size/Math.pow(1024, order),sizes[order]);
	}
	public static boolean isBinary(File f) {
		final String path = f.getPath();
		if(f.isDirectory())
			return false;
		final AtomicBoolean b = new AtomicBoolean(false);
		bad_extensions.forEach((e)->{if(path.endsWith(e))b.set(true);});
		return b.get();
	}
}
