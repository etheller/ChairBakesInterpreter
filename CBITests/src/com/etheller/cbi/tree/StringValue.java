package com.etheller.cbi.tree;

public final class StringValue implements Value {
	private final String string;

	public StringValue(final String string) {
		this.string = string;
	}

	@Override
	public String toString() {
		return string;
	}

	@Override
	public HandleType getType() {
		return HandleType.STRING;
	}

	@Override
	public void apply(final ValueVisitor visitor) {
		visitor.accept(this);
	}

	public String getString() {
		return string;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((string == null) ? 0 : string.hashCode());
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
		final StringValue other = (StringValue) obj;
		if (string == null) {
			if (other.string != null) {
				return false;
			}
		} else if (!string.equals(other.string)) {
			return false;
		}
		return true;
	}
}
