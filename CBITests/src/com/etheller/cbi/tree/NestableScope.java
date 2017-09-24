package com.etheller.cbi.tree;

import java.util.List;

public final class NestableScope implements HandleScope {
	private final HandleScope parent;
	private final HandleScope child;

	public NestableScope(final HandleScope parent, final HandleScope child) {
		this.parent = parent;
		this.child = child;
	}

	@Override
	public Handle getHandle(final String key) {
		if (child.hasHandle(key)) {
			return child.getHandle(key);
		}
		return parent.getHandle(key);
	}

	@Override
	public void putHandle(final String key, final Handle handle) {
		child.putHandle(key, handle);
	}

	@Override
	public boolean hasHandle(final String key) {
		return child.hasHandle(key) || parent.hasHandle(key);
	}

	@Override
	public boolean createHandle(final String key, final Handle handle) {
		return child.createHandle(key, handle);
	}

	@Override
	public void capture(final String key) {
		if (child.hasHandle(key) || "self".equals(key)) {
			child.capture(key);
		} else {
			parent.capture(key);
		}
	}

	@Override
	public HandleType getHandleType(final String key) {
		if (child.hasHandle(key)) {
			return child.getHandleType(key);
		}
		return parent.getHandleType(key);
	}

	@Override
	public HandleScope captureScope(final boolean keepCaptureList) {
		return new NestableScope(parent, child.captureScope(keepCaptureList));
	}

	@Override
	public HandleScope createEmptyScope() {
		return new NestableScope(parent, child.createEmptyScope());
	}

	@Override
	public HandleScope createFunctionScope(final List<FunctionParameter> parameter, final boolean isAnonymous) {
		return new NestableScope(parent, child.createFunctionScope(parameter, isAnonymous));
	}

	@Override
	public Handle getFirstCapture() {
		return child.getFirstCapture();
	}
}
