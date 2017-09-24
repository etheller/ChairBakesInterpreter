package com.etheller.cbi.tree;

public final class LessExpression extends AbstractComparisonExpression {
	public LessExpression(final Expression left, final Expression right) {
		super(left, right);
	}

	@Override
	protected boolean compare(final int leftAmount, final int rightAmount) {
		return leftAmount < rightAmount;
	}
}
