package com.etheller.cbi.tree.util;

import com.etheller.cbi.tree.BooleanValue;
import com.etheller.cbi.tree.CaptureListValue;
import com.etheller.cbi.tree.FunctionDefinitionValue;
import com.etheller.cbi.tree.FunctionInstanceValue;
import com.etheller.cbi.tree.IntegerValue;
import com.etheller.cbi.tree.NilValue;
import com.etheller.cbi.tree.StringValue;
import com.etheller.cbi.tree.ValueVisitor;

public final class IntValueGetter implements ValueVisitor {
	private int value;

	@Override
	public void accept(final FunctionInstanceValue funcValue) {
		throw new IllegalStateException("Invalid type, expected integer");
	}

	@Override
	public void accept(final FunctionDefinitionValue funcDefValue) {
		throw new IllegalStateException("Invalid type, expected integer");
	}

	@Override
	public void accept(final NilValue nilValue) {
		throw new IllegalStateException("Invalid type, expected integer");
	}

	@Override
	public void accept(final StringValue stringValue) {
		throw new IllegalStateException("Invalid type, expected integer");
	}

	@Override
	public void accept(final IntegerValue integerValue) {
		value = integerValue.getValue();
	}

	@Override
	public void accept(final CaptureListValue captureListValue) {
		throw new IllegalStateException("Invalid type, expected integer");
	}

	public int getValue() {
		return value;
	}

	@Override
	public void accept(final BooleanValue stringValue) {
		throw new IllegalStateException("Invalid type, expected integer");

	}
}