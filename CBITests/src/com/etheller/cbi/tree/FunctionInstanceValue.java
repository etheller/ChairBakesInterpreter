package com.etheller.cbi.tree;

public final class FunctionInstanceValue implements Value {
	private final Function function;
	private HandleScope handleScope;
	private final HandleScope declaringScope;

	public FunctionInstanceValue(final Function function, final HandleScope sourceScope,
			final HandleScope declaringScope) {
		this.function = function;
		this.handleScope = sourceScope;
		this.declaringScope = declaringScope;
	}

	@Override
	public HandleType getType() {
		return HandleType.FUNCTION_INSTANCE;
	}

	@Override
	public void apply(final ValueVisitor visitor) {
		visitor.accept(this);
	}

	public Function getFunction() {
		return function;
	}

	public HandleScope getHandleScope() {
		return handleScope;
	}

	public void setHandleScope(final HandleScope handleScope) {
		this.handleScope = handleScope;
	}

	public HandleScope getEvaluationScope() {
		return new NestableScope(declaringScope, handleScope);
	}

	public HandleScope getDeclaringScope() {
		return declaringScope;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((function == null) ? 0 : function.hashCode());
		result = prime * result + ((handleScope == null) ? 0 : handleScope.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final FunctionInstanceValue other = (FunctionInstanceValue) obj;
		if (function == null) {
			if (other.function != null) {
				return false;
			}
		} else if (!function.equals(other.function)) {
			return false;
		}
		if (handleScope == null) {
			if (other.handleScope != null) {
				return false;
			}
		} else if (!handleScope.equals(other.handleScope)) {
			return false;
		}
		return true;
	}

}
