package com.etheller.cbi.tree;

public final class GreaterOrEqualsExpression extends AbstractComparisonExpression {
	public GreaterOrEqualsExpression(final Expression left, final Expression right) {
		super(left, right);
	}

	@Override
	protected boolean compare(final int leftAmount, final int rightAmount) {
		return leftAmount >= rightAmount;
	}
}
