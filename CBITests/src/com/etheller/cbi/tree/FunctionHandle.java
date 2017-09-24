package com.etheller.cbi.tree;

import com.etheller.cbi.tree.util.FunctionProcessor;

public final class FunctionHandle implements Handle {

	private final Function function;

	public FunctionHandle(final Function function) {
		this.function = function;
	}

	@Override
	public Handle assign(final Handle expression, final HandleScope handleScope) {
		final FunctionProcessor visitor = new FunctionProcessor(handleScope);
		expression.resolve().apply(visitor);
		return new FunctionInstanceHandle(
				new FunctionInstanceValue(function, function.doFunction(visitor.getReturnValScope())));
	}

	@Override
	public Value resolve() {
		return new FunctionDefinitionValue(function);
	}

	@Override
	public final HandleType getType() {
		return HandleType.FUNCTION_DEFINITION;
	}
}
