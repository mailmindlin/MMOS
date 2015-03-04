package com.mindlin.cdoc;

public class Typedef implements Member {
	protected final String name;
	protected final String supertype;
	protected final Namespace parent;
	public static Typedef get(Namespace location, String name) {
		return (Typedef) Namespace.resolve(location, false, name);
	}
	public Typedef(String supertype, String name, Namespace location) {
		this.name=name;
		this.supertype=supertype;
		this.parent=location;
	}
	@Override
	public String getName() {
		return name;
	}
}
