package com.etheller.cbi.tree;

import java.util.List;

public final class CaptureStatement implements Statement {
	private final List<Capturable> capturables;

	public CaptureStatement(final List<Capturable> capturables) {
		this.capturables = capturables;
	}

	@Override
	public void execute(final HandleScope scope) {
		for (final Capturable capturable : capturables) {
			capturable.capture(scope);
		}
	}

}
