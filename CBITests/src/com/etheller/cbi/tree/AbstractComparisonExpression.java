package com.etheller.cbi.tree;

import com.etheller.cbi.tree.util.IntValueGetter;

public abstract class AbstractComparisonExpression implements Expression {
	private final Expression left;
	private final Expression right;

	public AbstractComparisonExpression(final Expression left, final Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Handle evaluate(final HandleScope scope) {
		final Value leftValue = left.evaluate(scope).resolve();
		final Value rightValue = right.evaluate(scope).resolve();
		if (leftValue.getType() == HandleType.INT && rightValue.getType() == HandleType.INT) {
			final IntValueGetter intValueGetter = new IntValueGetter();
			int leftAmount = 0;
			leftValue.apply(intValueGetter);
			leftAmount += intValueGetter.getValue();
			rightValue.apply(intValueGetter);
			final int rightAmount = intValueGetter.getValue();
			if (compare(leftAmount, rightAmount)) {
				return new TypedVariableHandle(BooleanValue.TRUE);
			}
			return new TypedVariableHandle(BooleanValue.FALSE);
		}
		throw new IllegalArgumentException("Invalid types for operator");
	}

	protected abstract boolean compare(int leftAmount, int rightAmount);

}
