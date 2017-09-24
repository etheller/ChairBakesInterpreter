package com.etheller.cbi.tree;

public class ConstantValueExpression implements Expression {
	private final Value value;

	public ConstantValueExpression(final Value value) {
		this.value = value;
	}

	@Override
	public Handle evaluate(final HandleScope scope) {
		return new TypedVariableHandle(value);
	}

}
