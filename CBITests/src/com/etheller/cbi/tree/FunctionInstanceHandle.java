package com.etheller.cbi.tree;

import com.etheller.cbi.tree.util.FunctionProcessor;

public final class FunctionInstanceHandle implements Handle {

	private final FunctionInstanceValue functionInstanceValue;

	public FunctionInstanceHandle(final FunctionInstanceValue functionInstanceValue) {
		this.functionInstanceValue = functionInstanceValue;
	}

	@Override
	public Handle assign(final Handle expression, final HandleScope handleScope) {
		final FunctionProcessor visitor = new FunctionProcessor(handleScope);
		expression.resolve().apply(visitor);
		final HandleScope doFunctionResult = functionInstanceValue.getFunction()
				.doFunction(visitor.getReturnValScope());
		functionInstanceValue.setHandleScope(doFunctionResult.captureScope(true));
		return this;
	}

	@Override
	public FunctionInstanceValue resolve() {
		return functionInstanceValue;
	}

	@Override
	public final HandleType getType() {
		return HandleType.FUNCTION_INSTANCE;
	}
}
