package com.etheller.cbi.tree;

public final class VariableReferenceExpression implements Expression {
	private final Reference reference;

	public VariableReferenceExpression(final Reference reference) {
		this.reference = reference;
	}

	@Override
	public Handle evaluate(final HandleScope scope) {
		return reference.resolve(scope);
	}
}
