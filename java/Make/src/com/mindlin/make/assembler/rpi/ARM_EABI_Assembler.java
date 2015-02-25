package com.mindlin.make.assembler.rpi;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import util.StrUtils;

import com.mindlin.make.Assembler;

public class ARM_EABI_Assembler extends Assembler<ARM_EABI_Assembler> {
	protected JSONObject data = new JSONObject();
	public ARM_EABI_Assembler(String ARMGNU) {
		super();
		data.put("cmd", new File(ARMGNU + "as").getAbsolutePath());
	}

	@Override
	public ARM_EABI_Assembler setOutput(String output) {
		if(relativeTo!=null)
			data.put("output", relativeTo.resolve(output));
		else
			data.put("output", Paths.get(output));
		return this;
	}

	@Override
	public ARM_EABI_Assembler addOptimization(String optimization) {
		if(optimization.equals("memory"))
			flag("-reduce-memory-overheads");
		return this;
	}

	@Override
	public Set<String> getOptimizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARM_EABI_Assembler setOutput(Path output) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ARM_EABI_Assembler setRelativeTo(String dir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getCommand() {
		LinkedList<String> result = new LinkedList<String>();
		result.addFirst(data.getString("cmd"));
		//add flags
		for(Object o: data.getJSONArray("flags")) {
			if(o instanceof JSONArray)
				result.addAll(1, StrUtils.convertList((i)->((i instanceof String)?(String)i:i.toString()), (JSONArray)o));
			else
				throw new IllegalStateException("Illegal flag type: "+o.getClass().getCanonicalName());
		}
		//warnings
		{
			JSONObject warnings = data.getJSONObject("options").getJSONObject("warnings");
			if(warnings.getBoolean("show")) {
				result.add("--warn");
				warnings.getJSONArray("shown").forEach((tmp)->{
					if(!(tmp instanceof String))
						throw new IllegalArgumentException("Argument '"+tmp.getClass().getCanonicalName()+"' isn't a string!\n"+tmp.toString());
					String warning = tmp.toString();
					if(warning.isEmpty()){
						//do nothing
					}else {
						System.out.println("Unknown warning: "+warning);
					}
				});
				warnings.getJSONArray("suppressed").forEach((tmp)->{
					if(!(tmp instanceof String))
						throw new IllegalArgumentException("Argument '"+tmp.getClass().getCanonicalName()+"' isn't a string!\n"+tmp.toString());
					String warning = tmp.toString();
					if(warning.equals("deprecated")){
						result.add("-mno-warn-deprecated");
					} else {
						System.out.println("Unknown warning: "+warning);
					}
				});
			}else if(warnings.getBoolean("suppress")) {
				result.add("--no-warn");
			}
		}
		//options
		{
			JSONObject options = data.getJSONObject("options");
			options.forEach((k,v) -> {
				if(!(v instanceof String)) {
					
				}else{
					String vstr=(String)v;
					if(k.equals("cpu")) {
						result.add("-mcpu");
						result.add(vstr);
					}else if(k.equals("fpu")) {
						result.add("-mfpu");
						result.add(vstr);
					}else if(k.equals("")) {
						
					}
				}
					
			});
		}
		return StrUtils.toStringArray(result);
	}

	@Override
	public ARM_EABI_Assembler includeDir(Path dir) {
		// TODO Auto-generated method stub
		return null;
	}
}