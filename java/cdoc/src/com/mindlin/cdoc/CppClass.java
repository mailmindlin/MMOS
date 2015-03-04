package com.mindlin.cdoc;

public class CppClass extends Namespace {
	public CppClass(Namespace parent, String name) {
		
	}
	public CppMethod addMethod(Visibility vis, String name) {
		return new CppMethod(vis,name);
	}
	public class CppMethod implements Member {
		protected final String name;
		protected final Visibility vis;
		protected Declaration declaration;
		protected Definition definition;
		public CppMethod(Visibility vis, String name) {
			this.name=name;
			this.vis=vis;
			addChild(this);
		}
		@Override
		public String getName() {
			return name;
		}
		public Visibility getVisibility() {
			return vis;
		}
	}
}
