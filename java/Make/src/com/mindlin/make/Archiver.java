package com.mindlin.make;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Archiver<IMPL extends Archiver<IMPL>> implements StdCommand<IMPL>{
	protected Path relativeTo=null;
	protected JSONObject data = new JSONObject();
	private final IMPL self;
	@SuppressWarnings("unchecked")
	public Archiver() {
		self=(IMPL)this;
		data.put("flags", new JSONArray())
			.put("targets", new JSONArray())
			.put("defines", new JSONObject())
			.put("options", new JSONObject()
				.put("warnings", new JSONObject()
					.put("suppress", false)
					.put("show", false)
					.put("max", -1)
					.put("suppressed", new JSONArray())
					.put("shown", new JSONArray())));
	}


	public abstract IMPL setOutput(File output);

	public IMPL flag(String flag, String... arguments) {
		data.getJSONArray("flags").put(new JSONArray().put(flag).put(Arrays.asList(arguments)));
		return self;
	}
}
