package com.etheller.cbi.tree;

public final class GreaterExpression extends AbstractComparisonExpression {
	public GreaterExpression(final Expression left, final Expression right) {
		super(left, right);
	}

	@Override
	protected boolean compare(final int leftAmount, final int rightAmount) {
		return leftAmount > rightAmount;
	}
}
