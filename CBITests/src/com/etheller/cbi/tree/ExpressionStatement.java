package com.etheller.cbi.tree;

public final class ExpressionStatement implements Statement {
	private final Expression expression;

	public ExpressionStatement(final Expression expression) {
		this.expression = expression;
	}

	@Override
	public void execute(final HandleScope scope) {
		expression.evaluate(scope);
	}

}
