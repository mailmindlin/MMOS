package com.mindlin.make;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Linker<IMPL extends Linker<IMPL>> implements StdCommand<IMPL>{
	protected Path relativeTo=null;
	protected JSONObject data = new JSONObject();
	private final IMPL self;
	@SuppressWarnings("unchecked")
	public Linker() {
		this.self=(IMPL)this;
		data.put("flags", new JSONArray())
			.put("targets", new JSONArray())
			.put("options", new JSONObject()
				.put("warnings", new JSONObject()
					.put("suppress", false)
					.put("show", false)
					.put("max", -1)
					.put("suppressed", new JSONArray())
					.put("shown", new JSONArray())));
	}
	@Override
	public IMPL setCPU(String cpuName) {
		data.getJSONObject("options").put("cpu", cpuName);
		return self;
	}
	@Override
	public IMPL setFPU(String fpuName) {
		data.getJSONObject("options").put("fpu", fpuName);
		return self;
	}
	@Override
	public IMPL setArchitecture(String arch) {
		data.getJSONObject("options").put("arch", arch);
		return self;
	}
	@Override
	public IMPL addTarget(Path target) {
		data.getJSONArray("targets").put(resolve(target));
		return self;
	}

	@Override
	public IMPL addTarget(String target) {
		data.getJSONArray("targets").put(resolve(target));
		return self;
	}
}
