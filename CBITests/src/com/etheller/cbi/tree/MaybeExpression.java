package com.etheller.cbi.tree;

public final class MaybeExpression implements Expression {
	private final Expression condition;
	private final Expression thenAction;
	private final Expression elseAction;

	public MaybeExpression(final Expression condition, final Expression thenAction, final Expression elseAction) {
		this.condition = condition;
		this.thenAction = thenAction;
		this.elseAction = elseAction;
	}

	@Override
	public Handle evaluate(final HandleScope scope) {
		final Handle conditionHandle = condition.evaluate(scope);
		final Value resolve = conditionHandle.resolve();
		if (resolve != NilValue.INSTANCE && resolve != BooleanValue.FALSE
		/* or is false?? */) {
			return thenAction.evaluate(scope);
		}
		return elseAction.evaluate(scope);
	}
}
