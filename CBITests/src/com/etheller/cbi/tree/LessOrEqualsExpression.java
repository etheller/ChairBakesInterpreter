package com.etheller.cbi.tree;

public final class LessOrEqualsExpression extends AbstractComparisonExpression {
	public LessOrEqualsExpression(final Expression left, final Expression right) {
		super(left, right);
	}

	@Override
	protected boolean compare(final int leftAmount, final int rightAmount) {
		return leftAmount <= rightAmount;
	}
}
