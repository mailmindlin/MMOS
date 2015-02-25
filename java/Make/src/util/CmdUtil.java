package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import util.PipingStreams.PipingInputStream;
import util.PipingStreams.PipingOutputStream;

public class CmdUtil {
	public static void exec(Properties props, String...commands) throws IOException, InterruptedException {
		exec(props, StrUtils.concatWithSpaces(commands));
	}
	public static void exec(Properties props, File output, String...commands) throws IOException, InterruptedException {
		exec(props, StrUtils.concatWithSpaces(commands), output);
	}
	public static void exec(Properties props, String command) throws IOException, InterruptedException {
		PipingOutputStream procStdOut = null, procErrOut = null;
		PipingInputStream procStdIn = null;
		procStdOut = new PipingOutputStream(System.out);
		procErrOut = new PipingOutputStream(System.err);
		procStdIn = new PipingInputStream(System.in);
		exec(props, command, procStdOut, procErrOut, procStdIn);
	}
	public static void exec(Properties props, String command, File output) throws IOException, InterruptedException {
		PipingOutputStream procStdOut = null, procErrOut = null;
		PipingInputStream procStdIn = null;
		procStdOut = new PipingOutputStream(new FileOutputStream(output));
		procErrOut = new PipingOutputStream(System.err);
		procStdIn = new PipingInputStream(System.in);
		exec(props, command, procStdOut, procErrOut, procStdIn);
	}
	public static void exec(Properties props, String command, PipingOutputStream procStdOut, PipingOutputStream procErrOut, PipingInputStream procStdIn) throws IOException, InterruptedException {
		if ((props.<Integer> getOrUse("debug", 2)) > 1)
			System.out.println(command);
		Process proc = null;
		PipingOutputStream stdOut = null;
		PipingInputStream stdIn = null, errIn = null;
		try {
			procStdOut = new PipingOutputStream(System.out);
			procErrOut = new PipingOutputStream(System.err);
			procStdIn = new PipingInputStream(System.in);
			proc = props.<Runtime> getOrUse("runtime", Runtime.getRuntime())
					.exec(command);
			stdIn = new PipingInputStream(proc.getInputStream(), procStdOut);
			errIn = new PipingInputStream(proc.getInputStream(), procErrOut);
			stdOut = new PipingOutputStream(procStdIn, proc.getOutputStream());
			do {
				stdIn.pipe(-1);
				errIn.pipe(-1);
//				stdOut.pipe(-1);
				Thread.sleep(50);// don't hog all the CPU power
			} while (proc.isAlive());
			procStdOut.close();
			procErrOut.close();
			stdIn.close();
			errIn.close();
			procStdIn.close();
			stdOut.close();
		} finally {
			if (proc != null)
				proc.destroyForcibly();
			if (procStdOut != null)
				try {
					procStdOut.closeAll();
				} catch (Exception e) {
				}
			if (procErrOut != null)
				try {
					procErrOut.closeAll();
				} catch (Exception e) {
				}
			if (stdOut != null)
				try {
					stdOut.closeAll();
				} catch (Exception e) {
				}
			if (procStdIn != null)
				try {
					procStdIn.closeAll();
				} catch (Exception e) {
				}
			if (stdIn != null)
				try {
					stdIn.closeAll();
				} catch (Exception e) {
				}
			if (errIn != null)
				try {
					errIn.closeAll();
				} catch (Exception e) {
				}
		}
	}
	public static boolean exec(String...command) {
		try {
			Process proc = Runtime.getRuntime().exec(command);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

			// read the output from the command
			System.out.println("Here is the standard output of the command:\n");
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			// read any errors from the attempted command
			System.out.println("Here is the standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null) {
				System.err.println(s);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
