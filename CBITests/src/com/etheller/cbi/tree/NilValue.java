package com.etheller.cbi.tree;

public final class NilValue implements Value {
	public static final NilValue INSTANCE = new NilValue();

	@Override
	public String toString() {
		return "(nil)";
	}

	@Override
	public HandleType getType() {
		return HandleType.NULL;
	}

	@Override
	public void apply(final ValueVisitor visitor) {
		visitor.accept(this);
	}
}
