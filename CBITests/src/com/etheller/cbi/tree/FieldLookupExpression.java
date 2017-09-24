package com.etheller.cbi.tree;

public class FieldLookupExpression implements Expression {

	private final Expression parentObjectReference;
	private final String fieldName;

	public FieldLookupExpression(final Expression parentObjectReference, final String fieldName) {
		this.parentObjectReference = parentObjectReference;
		this.fieldName = fieldName;
	}

	@Override
	public Handle evaluate(final HandleScope handleScope) {
		final FieldFinder fieldFinder = new FieldFinder(fieldName, handleScope);
		parentObjectReference.evaluate(handleScope).resolve().apply(fieldFinder);
		return fieldFinder.getFieldFound();
	}

	private final class FieldFinder implements ValueVisitor {
		private Handle fieldFound;
		private final String fieldName;
		private final HandleScope handleScope;

		public FieldFinder(final String fieldName, final HandleScope handleScope) {
			this.fieldName = fieldName;
			this.handleScope = handleScope;
		}

		@Override
		public void accept(final FunctionInstanceValue funcValue) {
			fieldFound = funcValue.getHandleScope().getHandle(fieldName);
		}

		@Override
		public void accept(final FunctionDefinitionValue funcDefValue) {
			throw new IllegalArgumentException("Func def needs to resolve, has no fields");
		}

		@Override
		public void accept(final NilValue nilValue) {
			throw new IllegalArgumentException("nil has no fields");
		}

		@Override
		public void accept(final StringValue stringValue) {
			throw new IllegalArgumentException("string has no fields");
		}

		@Override
		public void accept(final IntegerValue integerValue) {
			throw new IllegalArgumentException("int has no fields");
		}

		@Override
		public void accept(final CaptureListValue captureListValue) {
			fieldFound = captureListValue.getHandleScope().getHandle(fieldName);
		}

		public Handle getFieldFound() {
			return fieldFound;
		}

		@Override
		public void accept(final BooleanValue booleanValue) {
			throw new IllegalArgumentException("bool has no fields");
		}
	}
}
