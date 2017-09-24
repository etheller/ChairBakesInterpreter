package com.etheller.cbi.tree;

public final class IntegerValue implements Value {
	private final int value;

	public IntegerValue(final int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}

	@Override
	public HandleType getType() {
		return HandleType.INT;
	}

	@Override
	public void apply(final ValueVisitor visitor) {
		visitor.accept(this);
	}

	public int getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
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
		final IntegerValue other = (IntegerValue) obj;
		if (value != other.value) {
			return false;
		}
		return true;
	}
}
