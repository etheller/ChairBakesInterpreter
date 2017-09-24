package com.etheller.cbi.tree;

public final class VariableReference implements Reference {
	private final String handleId;

	public VariableReference(final String handleId) {
		this.handleId = handleId;
	}

	@Override
	public Handle resolve(final HandleScope handleScope) {
		return handleScope.getHandle(handleId);
	}

}
