package com.mindlin.make;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import util.CmdUtil;

public abstract class Assembler<IMPL extends Assembler<IMPL>> implements StdCommand<IMPL> {
	protected Path relativeTo=null;
	protected JSONObject data = new JSONObject();
	private final IMPL self;
	@SuppressWarnings("unchecked")
	public Assembler() {
		this.self=(IMPL)this;
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

	/**
	 * Define the given symbol with the value of 1
	 * 
	 * @param definition
	 *            symbol to define
	 * @return self
	 */
	public IMPL define(String definition) {
		return define(definition,"1");
	}

	/**
	 * Define the given symbol as value
	 * 
	 * @param definition
	 *            symbol to define (should not contain spaces)
	 * @param value
	 *            value for symbol
	 * @return self
	 */
	public IMPL define(String definition, String value) {
		data.getJSONObject("defines").put(definition,value);
		return self;
	}
	@Override
	public IMPL addTarget(Path target) {
		if(relativeTo!=null &&(!target.isAbsolute()))
			data.getJSONArray("targets").put(relativeTo.resolve(target));
		else
			data.getJSONArray("targets").put(target);
		return self;
	}

	@Override
	public IMPL addTarget(String target) {
		if(relativeTo!=null)
			data.getJSONArray("targets").put(relativeTo.resolve(target));
		else
			data.getJSONArray("targets").put(Paths.get(target));
		return self;
	}
	
	public IMPL setCPU(String cpuName) {
		data.getJSONObject("options").put("cpu", cpuName);
		return self;
	}
	public IMPL setFPU(String fpuName) {
		data.getJSONObject("options").put("fpu", fpuName);
		return self;
	}
	public IMPL setArch(String arch) {
		data.getJSONObject("options").put("arch", arch);
		return self;
	}
	public IMPL setRelativeTo(Path other) {
		relativeTo=other;
		return self;
	}

	public IMPL setArchitecture(String arch) {
		data.getJSONObject("options").put("arch", arch);
		return self;
	}

	/**
	 * Explicitly suppress warnings of given type
	 * 
	 * @param type
	 * @return self
	 */
	public IMPL suppressWarning(String type) {
		data.getJSONObject("options").getJSONObject("warnings").getJSONArray("shown").put(type);
		return self;
	}

	/**
	 * Explicitly show warnings of given type
	 * 
	 * @param type
	 * @return self
	 */
	public IMPL showWarning(String type) {
		data.getJSONObject("options").getJSONObject("warnings").getJSONArray("shown").put(type);
		return self;
	}

	/**
	 * Whether explicitly show warnings
	 * 
	 * @param aflag
	 *            whether to show them
	 * @return self
	 */
	public IMPL showWarnings(boolean aflag) {
		data.getJSONObject("options").getJSONObject("warnings").put("show", aflag);
		return self;
	}

	/**
	 * Whether to explicitly suppress all warnings. May conflict with
	 * {@link #showWarnings(boolean)}.
	 * 
	 * @param aflag
	 *            whether to suppress them
	 * @return self
	 */
	public IMPL suppressWarnings(boolean aflag) {
		data.getJSONObject("options").getJSONObject("warnings").put("suppress", aflag);
		return self;
	}

	/**
	 * Whether to explicitly enable display of various data.
	 * 
	 * @param aflag
	 * @return self
	 */
	public IMPL enableStatistics(boolean aflag) {
		data.getJSONObject("options").put("statistics",aflag);
		return self;
	}

	/**
	 * Maximum warnings to show
	 * @param num
	 * @return self
	 */
	public IMPL maxWarnings(int num) {
		data.getJSONObject("options").getJSONObject("warnings").put("max",num);
		return self;
	}

	@Override
	public IMPL flag(String flag, String... arguments) {
		data.getJSONArray("flags").put(new JSONArray().put(flag).put(Arrays.asList(arguments)));
		return self;
	}
	public IMPL includeDir(String dir) {
		if(relativeTo!=null)
			includeDir(relativeTo.resolve(dir));
		else
			includeDir(Paths.get(dir));
		return self;
	}
	@Override
	public boolean execute() {
		return CmdUtil.exec(getCommand());
	}
	public abstract IMPL includeDir(Path dir);
	/**
	 * Add implementation-specific optimization. <br/>
	 * Calling this method multiple times may override which optimization is
	 * used, or use both, depending on the implementation.
	 * 
	 * @param optimization
	 *            to use
	 * @return self
	 */
	public abstract IMPL addOptimization(String optimization);

	/**
	 * Gets a set of available optimizations.
	 * 
	 * @return available optimizations
	 */
	public abstract Set<String> getOptimizations();
}
