package com.etheller.cbi.tree;

public final class FunctionDefinitionStatement implements Statement {
	private final String name;
	private final ExpressionFunction function;

	public FunctionDefinitionStatement(final String name, final FunctionalStatementsExpression expression) {
		this.name = name;
		function = new ExpressionFunction(expression);
	}

	@Override
	public void execute(final HandleScope scope) {
		scope.createHandle(name, new FunctionHandle(function));
		scope.capture(name);
	}
}
