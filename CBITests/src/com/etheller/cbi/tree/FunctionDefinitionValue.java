package com.etheller.cbi.tree;

public final class FunctionDefinitionValue implements Value {
	private final Function function;

	public FunctionDefinitionValue(final Function function) {
		this.function = function;
	}

	@Override
	public HandleType getType() {
		return HandleType.FUNCTION_DEFINITION;
	}

	@Override
	public void apply(final ValueVisitor visitor) {
		visitor.accept(this);
	}

	public Function getFunction() {
		return function;
	}

}
