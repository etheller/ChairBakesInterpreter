package com.etheller.cbi.tree;

public final class StringHandleType implements HandleInstantiableType {
	private final class FunctionFinderImpl implements ValueVisitor {
		private Function function;

		@Override
		public void accept(final FunctionInstanceValue nilValue) {
			throw new IllegalStateException("not a type");
		}

		@Override
		public void accept(final FunctionDefinitionValue funcDefValue) {
			function = funcDefValue.getFunction();
		}

		@Override
		public void accept(final NilValue nilValue) {
			throw new IllegalStateException("not a type");
		}

		@Override
		public void accept(final StringValue stringValue) {
			throw new IllegalStateException("not a type");
		}

		@Override
		public void accept(final IntegerValue integerValue) {
			throw new IllegalStateException("not a type");
		}

		@Override
		public void accept(final CaptureListValue captureListValue) {
			throw new IllegalStateException("not a type");
		}

		public Function getFunction() {
			return function;
		}

		@Override
		public void accept(final BooleanValue booleanValue) {
			throw new IllegalStateException("not a type");
		}
	}

	private final String typeName;

	public StringHandleType(final String typeName) {
		this.typeName = typeName;
	}

	@Override
	public Handle makeNew(final HandleScope scope) {
		final HandleType handleType = scope.getHandleType(typeName);
		if (handleType == HandleType.FUNCTION_INSTANCE
				&& !HandleType.FUNCTION_INSTANCE.name().equals(typeName.toUpperCase())) {
			final Handle handle = scope.getHandle(typeName);
			final FunctionFinderImpl visitor = new FunctionFinderImpl();
			handle.resolve().apply(visitor);
			return new FunctionInstanceHandle(new FunctionInstanceValue(visitor.getFunction(), scope, scope));
		}
		final TypedVariableHandle typedVariableHandle = new TypedVariableHandle(handleType);
		return typedVariableHandle;
	}

	@Override
	public String getName() {
		return typeName;
	}
}
