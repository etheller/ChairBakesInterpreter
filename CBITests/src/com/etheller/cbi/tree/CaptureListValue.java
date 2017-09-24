package com.etheller.cbi.tree;

public final class CaptureListValue implements Value {
	private final HandleScope handleScope;

	public CaptureListValue(final HandleScope scope) {
		this.handleScope = scope;
	}

	@Override
	public HandleType getType() {
		return HandleType.CAPTURE_LIST;
	}

	@Override
	public void apply(final ValueVisitor visitor) {
		visitor.accept(this);
	}

	public HandleScope getHandleScope() {
		return handleScope;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		final CaptureListValue other = (CaptureListValue) obj;
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
