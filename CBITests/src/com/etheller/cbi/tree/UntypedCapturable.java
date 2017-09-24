package com.etheller.cbi.tree;

public final class UntypedCapturable implements Capturable {
	private final String name;

	public UntypedCapturable(final String name) {
		this.name = name;
	}

	@Override
	public void capture(final HandleScope scope) {
		scope.capture(name);
	}

	@Override
	public String getName() {
		return name;
	}
}
