package com.etheller.cbi.tree;

public final class DoubleEqualsExpression implements Expression {
	private final Expression left;
	private final Expression right;

	public DoubleEqualsExpression(final Expression left, final Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Handle evaluate(final HandleScope scope) {
		final Handle leftHandle = left.evaluate(scope);
		final Handle rightHandle = right.evaluate(scope);
		final Value leftValue = leftHandle.resolve();
		final Value rightValue = rightHandle.resolve();
		if (leftValue.getType() != rightValue.getType()) {
			return new TypedVariableHandle(BooleanValue.FALSE);
		} else {
			return leftValue.equals(rightValue) ? new TypedVariableHandle(BooleanValue.TRUE)
					: new TypedVariableHandle(BooleanValue.FALSE);
		}
	}
}
