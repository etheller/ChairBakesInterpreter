package com.etheller.cbi.tree;

public final class AssignmentExpression implements Expression {
	private final Expression reference;
	private final Expression expression;

	public AssignmentExpression(final Expression reference, final Expression expression) {
		this.reference = reference;
		this.expression = expression;
	}

	@Override
	public Handle evaluate(final HandleScope scope) {
		final Handle value = expression.evaluate(scope);
		final HandleScope tempScope = scope.captureScope(false);
		tempScope.createHandle("--autocapture", value);
		tempScope.capture("--autocapture");
		final Handle handle = reference.evaluate(new NestableScope(scope, tempScope));
		return handle.assign(value, scope);
	}
}
