package com.mindlin.cdoc;

import java.util.concurrent.Callable;

public class Documentation implements Callable<String> {
	protected FileLocation start,end;
	protected transient String rawtext;
	protected transient String parsedtext;
	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
