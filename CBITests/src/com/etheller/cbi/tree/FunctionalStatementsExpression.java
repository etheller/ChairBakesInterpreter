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
		final HandleScope nestedScopeForExecution = new NestableScope(scope, capturedScope);
		for (final Statement statement : statements) {
			statement.execute(nestedScopeForExecution);
		}
		final ExpressionFunction anonymousFunction = new ExpressionFunction(this);
		// TODO double check scoping on returned function!
		// Should anonymous functions who get executed later be able to execute
		// again, still knowing the scope of their declaration?
		return new FunctionInstanceHandle(new FunctionInstanceValue(anonymousFunction, capturedScope, scope));

	}

	public void setAnonymous(final boolean anonymous) {
		this.anonymous = anonymous;
	}
}
