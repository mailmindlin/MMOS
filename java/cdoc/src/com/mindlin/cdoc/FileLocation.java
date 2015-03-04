package com.mindlin.cdoc;

import java.nio.file.Path;

public class FileLocation {
	protected final Path file;
	protected final long line,ch;
	public FileLocation(Path file, long line,long ch) {
		this.file=file;
		this.line=line;
		this.ch=ch;
	}
	@Override
	public String toString() {
		return file.toString()+":"+line+":"+ch;
	}
}
