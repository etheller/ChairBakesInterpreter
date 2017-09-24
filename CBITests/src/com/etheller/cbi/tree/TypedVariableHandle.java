package com.etheller.cbi.tree;

public class TypedVariableHandle implements Handle {
	private Value value;
	private final HandleType type;

	public TypedVariableHandle(final HandleType type) {
		this.type = type;
		this.value = NilValue.INSTANCE;
	}

	public TypedVariableHandle(final Value value) {
		this.type = value.getType();
		this.value = value;
	}

	@Override
	public Handle assign(final Handle expression, final HandleScope handleScope) {
		if (expression.getType() != type) {
			if (expression.getType() == HandleType.FUNCTION_INSTANCE
					|| expression.getType() == HandleType.CAPTURE_LIST) {
				expression.resolve().apply(new ValueVisitor() {
					@Override
					public void accept(final FunctionInstanceValue funcValue) {
						value = funcValue.getHandleScope().getFirstCapture().resolve();
					}

					@Override
					public void accept(final FunctionDefinitionValue funcDefValue) {
						throw new IllegalStateException("type error");
					}

					@Override
					public void accept(final NilValue nilValue) {
						throw new IllegalStateException("type error");
					}

					@Override
					public void accept(final BooleanValue boolValue) {
						throw new IllegalStateException("type error");
					}

					@Override
					public void accept(final StringValue stringValue) {
						throw new IllegalStateException("type error");
					}

					@Override
					public void accept(final IntegerValue integerValue) {
						throw new IllegalStateException("type error");
					}

					@Override
					public void accept(final CaptureListValue captureListValue) {
						value = captureListValue.getHandleScope().getFirstCapture().resolve();
					}
				});
				return this;
			}
			throw new IllegalStateException(
					"Incompatible types: Cannot assign " + expression.getType() + " to " + type);
		}
		expression.resolve().apply(new ValueVisitor() {
			@Override
			public void accept(final FunctionInstanceValue funcValue) {
				value = new FunctionInstanceValue(funcValue.getFunction(), funcValue.getHandleScope(),
						funcValue.getDeclaringScope());
			}

			@Override
			public void accept(final FunctionDefinitionValue nilValue) {
				value = nilValue;
			}

			@Override
			public void accept(final NilValue nilValue) {
				value = nilValue;
			}

			@Override
			public void accept(final BooleanValue boolValue) {
				value = boolValue;
			}

			@Override
			public void accept(final StringValue stringValue) {
				value = stringValue;
			}

			@Override
			public void accept(final IntegerValue integerValue) {
				value = integerValue;
			}

			@Override
			public void accept(final CaptureListValue captureListValue) {
				value = captureListValue;
			}
		});
		return this;
	}

	@Override
	public Value resolve() {
		return value;
	}

	@Override
	public HandleType getType() {
		return type;
	}

}
