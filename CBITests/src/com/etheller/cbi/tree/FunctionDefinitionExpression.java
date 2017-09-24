package com.etheller.cbi.tree;

public final class FunctionDefinitionExpression implements Expression {
	private final String name;
	private final ExpressionFunction function;

	public FunctionDefinitionExpression(final String name, final FunctionalStatementsExpression expression) {
		this.name = name;
		function = new ExpressionFunction(expression);
	}

	@Override
	public Handle evaluate(final HandleScope scope) {
		final FunctionHandle handle = new FunctionHandle(function);
		scope.createHandle(name, handle);
		scope.capture(name);
		return new FunctionInstanceHandle(new FunctionInstanceValue(function, scope));
	}
}
