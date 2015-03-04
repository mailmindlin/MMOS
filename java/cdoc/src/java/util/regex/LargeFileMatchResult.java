package java.util.regex;

public interface LargeFileMatchResult {
	long start();
	long start(long group);
	long end();
	long end(long group);
	String group(long group);
	long groupCount();
}
