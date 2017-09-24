package com.etheller.cbi.tree;

import com.etheller.cbi.tree.util.IntValueGetter;

public final class MinusExpression implements Expression {

	private final Expression left;
	private final Expression right;

	public MinusExpression(final Expression left, final Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Handle evaluate(final HandleScope scope) {
		final Value leftValue = left.evaluate(scope).resolve();
		final Value rightValue = right.evaluate(scope).resolve();
		if (leftValue.getType() == HandleType.INT && rightValue.getType() == HandleType.INT) {
			final IntValueGetter intValueGetter = new IntValueGetter();
			int sum = 0;
			leftValue.apply(intValueGetter);
			sum += intValueGetter.getValue();
			rightValue.apply(intValueGetter);
			sum -= intValueGetter.getValue();
			return new TypedVariableHandle(new IntegerValue(sum));
		}
		throw new IllegalArgumentException("Invalid types for operator");
	}

}
