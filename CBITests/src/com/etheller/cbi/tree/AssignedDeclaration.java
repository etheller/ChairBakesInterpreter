package com.etheller.cbi.tree;

public class AssignedDeclaration implements Capturable {
	private final String name;
	private final HandleInstantiableType handleType;
	private final Expression initialExpression;

	public AssignedDeclaration(final String name, final HandleInstantiableType handleType,
			final Expression initialExpression) {
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
			final Handle makeNew = handleType.makeNew(scope);
			scope.createHandle(name, makeNew);
			makeNew.assign(evaluatedHandle, scope);
		}
	}

	@Override
	public String getName() {
		return name;
	}

}
