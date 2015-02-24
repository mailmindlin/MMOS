package com.mindlin.assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineEntry {

	public static void main(String[] fred) {
		Program p = new Program();
		if(fred.length==0){
			printHelp();
			System.exit(0);
		}
		for(int i=0;i<fred.length;i++) {
			String line=fred[i];
			String lc = line.toLowerCase();
			if(lc.equals("-h")||lc.equals("--help")||lc.equals("/?")) {
				printHelp();
				System.exit(0);
			}else if(lc.equals("-I")) {
				p.resolver.add(new File(fred[++i]));
			}else if(!lc.startsWith("-")){
				p.setMain(new File(line));	
			}
		}
		p.begin();
	}
	/**
	 * Read help from file at com.mindlin.assembler/help.txt and print it to the screen.
	 */
	public static void printHelp() {
		BufferedReader buff = new BufferedReader(new InputStreamReader(
				Class.class.getResourceAsStream("/com/mindlin/assembler/help.txt")));
		try {
			String line;
			while ((line = buff.readLine()) != null)
				System.out.println(line);
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

}
