package com.etheller.cbi.tree;

public final class NameReferenceExpression implements Expression {
	private final String handleId;

	public NameReferenceExpression(final String handleId) {
		this.handleId = handleId;
	}

	@Override
	public Handle evaluate(final HandleScope handleScope) {
		return handleScope.getHandle(handleId);
	}

}
