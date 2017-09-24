package com.etheller.cbi.tree;

import java.util.List;

public final class FunctionalStatementsExpression implements Expression {
	private final List<Statement> statements;
	private final List<FunctionParameter> parameters;
	private boolean anonymous;

	public FunctionalStatementsExpression(final List<FunctionParameter> parameters, final List<Statement> statements,
			final boolean isAnonymous) {
		this.parameters = parameters;
		this.statements = statements;
		this.anonymous = isAnonymous;
	}

	@Override
	public FunctionInstanceHandle evaluate(final HandleScope scope) {
		final HandleScope capturedScope = scope.createFunctionScope(parameters, anonymous);
		for (final Statement statement : statements) {
			statement.execute(capturedScope);
		}
		final ExpressionFunction anonymousFunction = new ExpressionFunction(this);
		return new FunctionInstanceHandle(new FunctionInstanceValue(anonymousFunction, capturedScope));
	}

	public void setAnonymous(final boolean anonymous) {
		this.anonymous = anonymous;
	}
}
