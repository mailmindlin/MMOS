public class Main {
	final String OS_VERSION="experimental pre-alpha 0.0.1";
	final String VERSION="alpha 0.0.1";
	final ConcurrentHashMap<String, Object> properties = new ConcurrentHashMap<String, Object>();
	final ArrayList<String> tasks = new ArrayList<String>();
	File bin=new File("bin");
	File cppBin = new File("bin/cpp");
	File asmBin = new File("bin/asm");
	File javaBin = new File("bin/java");
	public static void main(String[] fred) {
		parseArguments(fred);
		inferUnset();
	}
	public static void parseArguments(String[] argv) {
		for(int i=0;i<argv.length;i++) {
			String command = argv[i];
			if(command.equals("help")) {
				if(i+1<argv.length)
					printHelp(argv[++i]);
				else
					printStdHelp();
				System.exit(0);
			} else if(command.equals("-arch")) {
				properties.put("arch",argv[++i]);
			} else if(command.equals("-C")) {
				properties.put("bin",argv[++i]);
			}
			} else
				tasks.add(argv[i]);
		}
	}
	public static void inferUnset() {
	}
	public static void printHelp(String command) {
		if(command.equals("build")) {
			compile();
			link();
		}else if(command.equals("compile")) {
			compile();
		}else if(command.equals("link")) {
			link();
		}else if(command.equals("clean") {
			clean();
		}else if(command.equals("test") {
			
		}else if(command.equals("about") {
			System.out.println("Version "+OS_VERSION+" of MMOS, written by Mailmindlin, 2015.");
			System.out.println("Compiler version " + VERSION);
		}
	}
	public static void printStdHelp() {
		System.out.println("Usage: make <command> [arguments]");
		System.out.println();
		System.out.println("Command       Description");
		System.out.println("build         Compiles, links, and tests the file");
		System.out.println("clean         Removes the precompiled binaries and intermediate files (i.e., non-source files)");
		System.out.println("compile       Compiles the code to the output folder");
		System.out.println("help          Displays this menu");
		System.out.println("link          Links precompiled code");
		System.out.println("test          Tests the output (not yet implemented)");
		System.out.println("about         Echo the version of this build");
		System.out.println("\n");
		System.out.println("Variable     Default Value	Description");
		System.out.println("TARCH        armv6           Target architecture to compile for");
		System.out.println("BIN          bin/            Where to put the compiled binaries/intermediate files");
		System.out.println("OUT          kern            Output file (compiled operating system)");
		System.out.println();
		System.out.println("Use make help <command> for more information about each command");
	}
	public static void compile() {
		
	}
	public static void link() {
		
	}
	public static String execute(String command) {
		p = Runtime.getRuntime().exec(command);
		
	}
}