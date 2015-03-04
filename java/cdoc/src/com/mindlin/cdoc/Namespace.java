package com.mindlin.cdoc;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Namespace implements Member {
	protected static final String emptystr = "";

	public static Member resolve(Namespace root, boolean create, String... names) {
		String[] actualNames = splitNames(names);
		Member current = root;
		for (int i = 0; i < actualNames.length; i++) {
			if(!(current instanceof Namespace))
				return null;
			if (((Namespace)current).hasChild(actualNames[i]))
				current = ((Namespace)current).children.get(actualNames[i]).get();
			else if (create)
				current = new Namespace(((Namespace)current), actualNames[i]);
			else
				return null;
		}
		return current;
	}
	public static Namespace resolveNamespace(Namespace root, boolean create, String... names) {
		String[] actualNames = splitNames(names);
		Namespace current = root;
		for (int i = 0; i < actualNames.length; i++)
			if (current.hasChild(actualNames[i])) {
				Member nc = current.children.get(actualNames[i]).get();
				if(nc instanceof Namespace)
					current=((Namespace)nc);
				return null;
			}
			else if (create)
				current = new Namespace(current, actualNames[i]);
			else
				return null;
		return current;
	}

	public static Member resolve(Namespace root, Namespace current, boolean create, String... names) {
		Member result = resolve(current, false, names);
		if (result != null)
			return result;
		result = resolve(root, false, names);
		if (result != null)
			return result;
		if (create)
			return resolve(current, true, names);
		return null;
	}
	public static Namespace resolveNamespace(Namespace root, Namespace current, boolean create, String... names) {
		Namespace result = resolveNamespace(current, false, names);
		if (result != null)
			return result;
		result = resolveNamespace(root, false, names);
		if (result != null)
			return result;
		if (create)
			return resolveNamespace(current, true, names);
		return null;
	}

	public static String[] splitNames(String... names) {
		final LinkedList<String> actualNames = new LinkedList<String>();
		for (int i = 0; i < names.length; i++)
			Arrays.asList(names[i].split("::")).forEach(actualNames::addLast);
		return actualNames.toArray(new String[actualNames.size()]);
	}
	protected final ArrayList<Definition> definition = new ArrayList<Definition>(); 
	protected final Namespace parent;
	protected final String name;
	protected transient String fullName;
	protected final ConcurrentHashMap<String, AtomicReference<Member>> children = new ConcurrentHashMap<String, AtomicReference<Member>>();
	protected Path staticResidence;

	public Namespace() {
		parent = null;
		name = emptystr;
	}

	/**
	 * Resolves the path relative to the parent, and creates any nonexistent
	 * paths in the middle
	 * 
	 * @param parent
	 *            parent namespace
	 * @param path
	 *            path to resolve relative to parent.
	 */
	public Namespace(Namespace parent, String name) {
		this.parent = parent;
		this.name = name;
		parent.addChild(this);

	}

	public boolean isRoot() {
		return parent == null;
	}

	protected void addChild(Member child) {
		children.put(child.getName(), new AtomicReference<Member>(child));
	}

	public boolean hasChild(String name) {
		return children.containsKey(name) && children.get(name).get() != null;
	}

	public Member getOrCreateChild(String... names) {
		if (names.length == 0)
			return this;
		if (names.length == 1 && hasChild(names[0]))
			return children.get(names[0]).get();
		return resolve(this, true, names);
	}

	public Member getChild(String... names) {
		if (names.length == 0)
			return this;
		if (names.length == 1 && hasChild(names[0]))
			return children.get(names[0]).get();
		return resolve(this, false, names);
	}
	public Namespace getOrCreateChildNamespace(String... names) {
		if (names.length == 0)
			return this;
		if (names.length == 1 && hasChild(names[0])) {
			Member nc = children.get(names[0]).get();
			if(nc instanceof Namespace)
				return ((Namespace)nc);
			return null;
		}
		return resolveNamespace(this, true, names);
	}

	public Namespace getChildNamespace(String... names) {
		if (names.length == 0)
			return this;
		if (names.length == 1 && hasChild(names[0])) {
			Member nc = children.get(names[0]).get();
			if(nc instanceof Namespace)
				return ((Namespace)nc);
			return null;
		}
		return resolveNamespace(this, false, names);
	}

	/**
	 * Returns the <em>parent namespace</em>, or {@code null} if this namespace
	 * does not have a parent.
	 *
	 * <p>
	 * The parent of this namespace object consists of this namespace's root
	 * component, if any, and each element in the path except for the
	 * <em>farthest</em> from the root. This method may be used with the
	 * {@link #normalize normalize} method, to eliminate redundant names, for
	 * cases where <em>shell-like</em> navigation is required.
	 *
	 * <p>
	 * If this path has one or more elements, and no root component, then this
	 * method is equivalent to evaluating the expression: <blockquote>
	 * 
	 * <pre>
	 * subpath(0, getNameCount() - 1);
	 * </pre>
	 * 
	 * </blockquote>
	 *
	 * @return a namespace representing the namespace's parent
	 */
	public Namespace getParent() {
		return parent;
	}

	public int getNameCount() {
		return isRoot() ? 0 : parent.getNameCount() + 1;
	}

	/**
	 * Returns the name of this namespace.
	 * 
	 * @return this's name
	 */
	public String getName() {
		return name;
	}

	public String getFullName() {
		if (fullName != null) {
			// do nothing
		} else if (isRoot())
			fullName = "";
		else if (parent.isRoot())
			fullName = name;
		else {
			StringBuffer sb = new StringBuffer();
			sb.append(parent.getFullName());
			sb.append("::");
			sb.append(name);
			fullName = sb.toString();
		}
		return fullName;
	}

	/**
	 * Returns the root component of this namespace as a {@code Namespace}
	 * object, or {@code this} element if this namespace is the root component.
	 *
	 * @return a namespace representing the root component of this namespace,
	 */
	public Namespace getRoot() {
		return isRoot() ? this : parent.getRoot();
	}

	/**
	 * Determines whether this is a static namespace. A root namespace cannot be
	 * static.
	 * 
	 * @return if this is static
	 */
	public boolean isStatic() {
		return !(isRoot() || (!name.isEmpty()));
	}

	public Namespace setResidence(Path p) {
		this.staticResidence = p;
		return this;
	}

	protected boolean isChild(Namespace other) {
		if (other.getRoot() != this.getRoot())
			return false;
		Namespace ptr = other;
		while (!ptr.isRoot())
			if ((ptr = ptr.getParent()).equals(this))
				return true;
		return false;
	}

	protected String childString(Namespace other) {
		if (!isChild(other))
			return null;
		return "";
		//TODO finish
	}

	@Override
	public String toString() {
		if (isRoot())
			return "[root]_" + Integer.toHexString(this.hashCode()) + "@" + getClass().getName();
		else
			return getFullName() + "_" + Integer.toHexString(this.hashCode()) + "@"
					+ getClass().getName();
	}
}
