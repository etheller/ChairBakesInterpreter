package com.etheller.cbi.tree;

public class BooleanValue implements Value {
	public static final BooleanValue TRUE = new BooleanValue(true);
	public static final BooleanValue FALSE = new BooleanValue(false);

	private final boolean truthy;

	public BooleanValue(final boolean truthy) {
		this.truthy = truthy;
	}

	@Override
	public HandleType getType() {
		return HandleType.BOOL;
	}

	@Override
	public void apply(final ValueVisitor visitor) {
		visitor.accept(this);
	}

	public boolean isTruthy() {
		return truthy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (truthy ? 1231 : 1237);
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
		final BooleanValue other = (BooleanValue) obj;
		if (truthy != other.truthy) {
			return false;
		}
		return true;
	}

}
