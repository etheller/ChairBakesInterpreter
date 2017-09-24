package com.etheller.cbi.tree;

public final class ExpressionFunction implements Function {

	private final FunctionalStatementsExpression expression;

	public ExpressionFunction(final FunctionalStatementsExpression expression) {
		this.expression = expression;
	}

	@Override
	public HandleScope doFunction(final HandleScope handleScope) {
		return expression.evaluate(handleScope).resolve().getHandleScope();
	}
}
