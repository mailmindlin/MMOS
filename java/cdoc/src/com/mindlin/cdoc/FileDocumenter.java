package com.mindlin.cdoc;

import java.nio.file.Path;

public abstract class FileDocumenter {
	protected final Path file;
	protected FileDocumenter(Path file) {
		this.file=file;
	}
	public abstract String getLanguage();
	public abstract Path getPath();
}
