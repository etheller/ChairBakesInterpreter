package com.etheller.cbi.tree;

public final class FunctionParameter {
	private final HandleInstantiableType type;
	private final String name;

	public FunctionParameter(final HandleInstantiableType type, final String name) {
		this.type = type;
		this.name = name;
	}

	public HandleInstantiableType getType() {
		return type;
	}

	public String getName() {
		return name;
	}
}
