package com.mindlin.assembler.assemblers;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;

public class Assembler {
	protected Toolchain tc;
	public Assembler(Toolchain t) {
		tc=t;
	}
	/**
	 * Assembles the JSONArray of instructions with the given toolchain
	 * @param lexed array of semicompiled instructions
	 * @param output path to write to
	 * @throws IOException if there's a problem with writing to the output
	 */
	public void assemble(JSONArray lexed, Path output) throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(output));
		for(int i=0;i<lexed.length();i++) {
			JSONArray command = lexed.getJSONArray(i);
			Instruction x = command.getAs(0);
			String[] params = new String[command.size()-1];
			for(int j=0;j<command.size()-1;j++)
				params[i]=command.getString(i+1);
			write(tc.convert(x, params),out);
		}
		out.close();
	}
	protected void write(Object o, BufferedOutputStream out) throws IOException {
		if(o instanceof byte[])
			out.write((byte[])o);
		else if(o instanceof String)
			out.write(((String)o).getBytes());
	}
}
