package com.mindlin.make.assembler.rpi;

import java.io.File;
import java.nio.file.Path;

import org.json.JSONArray;

import util.StrUtils;

import com.mindlin.make.Archiver;

public class ARM_EABI_Archiver extends Archiver<ARM_EABI_Archiver> {

	public ARM_EABI_Archiver(String ARMGNU) {
		super();
		data.put("cmd", new File(ARMGNU+"ar").getAbsolutePath());
	}

	@Override
	public String[] getCommand() {
		JSONArray result = new JSONArray();
		result.put(0, data.get("cmd")).put(1, "rvs");
		if (data.has("output"))
			result.put(data.<Path>getAs("output").toString());
		// add flags
		data.getJSONArray("flags").forEach(
				(o) -> {
					if (o instanceof JSONArray)
						result.addAll(1, StrUtils.convertList(
								(i) -> ("-" + ((i instanceof String) ? (String) i : i.toString())),
								(JSONArray) o));
					else
						throw new IllegalStateException("Illegal flag type: "
								+ o.getClass().getCanonicalName());
				});
		data.getJSONArray("targets").forEach(
				(o) -> {
					if (o instanceof Path) {
						result.put(((Path) o).toFile().getPath());
					} else {
						throw new IllegalStateException("Illegal target type: "
								+ o.getClass().getCanonicalName());
					}
				});
		return StrUtils.toStringArray(result);
	}
}