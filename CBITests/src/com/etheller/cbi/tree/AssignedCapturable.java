package com.etheller.cbi.tree;

public class AssignedCapturable implements Capturable {
	private final String name;
	private final HandleType handleType;
	private final Expression initialExpression;

	public AssignedCapturable(final String name, final HandleType handleType, final Expression initialExpression) {
		this.name = name;
		this.handleType = handleType;
		this.initialExpression = initialExpression;
	}

	@Override
	public void capture(final HandleScope scope) {
		final Handle evaluatedHandle = initialExpression.evaluate(scope);
		if (scope.hasHandle(name)) {
			final Handle existingHandle = scope.getHandle(name);
			existingHandle.assign(evaluatedHandle, scope);
		} else {
			scope.createHandle(name, evaluatedHandle);
		}
		scope.capture(name);
	}

	@Override
	public String getName() {
		return name;
	}

}
