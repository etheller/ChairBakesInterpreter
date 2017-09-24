package com.etheller.cbi.tree.util;

import com.etheller.cbi.tree.BooleanValue;
import com.etheller.cbi.tree.CaptureListValue;
import com.etheller.cbi.tree.FunctionDefinitionValue;
import com.etheller.cbi.tree.FunctionInstanceValue;
import com.etheller.cbi.tree.HandleScope;
import com.etheller.cbi.tree.IntegerValue;
import com.etheller.cbi.tree.NilValue;
import com.etheller.cbi.tree.StringValue;
import com.etheller.cbi.tree.TypedVariableHandle;
import com.etheller.cbi.tree.ValueVisitor;

public final class FunctionProcessor implements ValueVisitor {
	private static final String AUTOCAPTURE_NAME = "--autocapture";
	private HandleScope returnValScope;
	private final HandleScope handleScope;

	public FunctionProcessor(final HandleScope handleScope) {
		this.handleScope = handleScope;
	}

	@Override
	public void accept(final NilValue nilValue) {
		final HandleScope tempScope = handleScope.captureScope(false);
		tempScope.createHandle(AUTOCAPTURE_NAME, new TypedVariableHandle(nilValue));
		tempScope.capture(AUTOCAPTURE_NAME);
		returnValScope = tempScope;
	}

	@Override
	public void accept(final StringValue stringValue) {
		final HandleScope tempScope = handleScope.captureScope(false);
		tempScope.createHandle(AUTOCAPTURE_NAME, new TypedVariableHandle(stringValue));
		tempScope.capture(AUTOCAPTURE_NAME);
		returnValScope = tempScope;
	}

	@Override
	public void accept(final IntegerValue integerValue) {
		final HandleScope tempScope = handleScope.captureScope(false);
		tempScope.createHandle(AUTOCAPTURE_NAME, new TypedVariableHandle(integerValue));
		tempScope.capture(AUTOCAPTURE_NAME);
		returnValScope = tempScope;
	}

	@Override
	public void accept(final CaptureListValue captureListValue) {
		returnValScope = captureListValue.getHandleScope();
	}

	@Override
	public void accept(final FunctionDefinitionValue nilValue) {
		throw new IllegalArgumentException("Functions can't be passed other function definitions");
	}

	@Override
	public void accept(final FunctionInstanceValue funcValue) {
		returnValScope = funcValue.getHandleScope();
	}

	public HandleScope getReturnValScope() {
		return returnValScope;
	}

	@Override
	public void accept(final BooleanValue boolValue) {
		final HandleScope tempScope = handleScope.captureScope(false);
		tempScope.createHandle(AUTOCAPTURE_NAME, new TypedVariableHandle(boolValue));
		tempScope.capture(AUTOCAPTURE_NAME);
		returnValScope = tempScope;
	}
}