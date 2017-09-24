package com.etheller.cbi.tree;

import com.etheller.cbi.tree.util.FunctionProcessor;

public final class FunctionHandle implements Handle {

	private final Function function;
	private final HandleScope parentDeclarationScope;

	public FunctionHandle(final Function function, final HandleScope parentDeclarationScope) {
		this.function = function;
		this.parentDeclarationScope = parentDeclarationScope;
	}

	@Override
	public Handle assign(final Handle expression, final HandleScope handleScope) {
		final FunctionProcessor visitor = new FunctionProcessor(handleScope);
		expression.resolve().apply(visitor);
		return new FunctionInstanceHandle(new FunctionInstanceValue(function,
				function.doFunction(new NestableScope(parentDeclarationScope, visitor.getReturnValScope())),
				parentDeclarationScope));
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
