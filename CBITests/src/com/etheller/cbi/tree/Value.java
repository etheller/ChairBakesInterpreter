package com.etheller.cbi.tree;

public interface Value {
	HandleType getType();

	void apply(ValueVisitor visitor);
}
