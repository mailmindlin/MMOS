package com.mindlin.assembler.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class FileUtils {
	public static final void readFile(File f, OutputStream os) {
		readFile(f.toPath(),os);
	}

	public static final void readFile(File f, Writer w) {
		readFile(f.toPath(),w);
	}

	public static final void readFile(File f, List<String> lines) throws FileNotFoundException {
		BufferedReader buff = new BufferedReader(new FileReader(f));
		try {
			String line;
			while ((line = buff.readLine()) != null)
				lines.add(line);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				buff.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static final void readFile(Path p, Writer w) {
		BufferedReader buff=null;
		try {
			buff = Files.newBufferedReader(p);
			String line;
			while ((line = buff.readLine()) != null)
				w.write(line);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(buff!=null)
					buff.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static final void readFile(Path p, OutputStream os) {
		readFile(p,os,2048);
	}
	public static final void readFile(Path p, OutputStream os, int bufferSize) {
		BufferedInputStream buff=null;
		try {
			buff = new BufferedInputStream(Files.newInputStream(p));
			byte[] buffer=new byte[bufferSize];
			int read;
			while ((read = buff.read(buffer)) > -1)
				os.write(buffer, 0, read);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(buff!=null)
					buff.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONArray readFile(File af, JSONArray jsonArray) throws FileNotFoundException {
		List<String> result = new ArrayList<String>();
		readFile(af,result);
		return new JSONArray(result);
	}
}
