package com.etheller.cbi.tree;

public final class TypedDeclaration implements Capturable {
	private final String name;
	private final HandleInstantiableType handleType;

	public TypedDeclaration(final String name, final HandleInstantiableType handleType) {
		this.name = name;
		this.handleType = handleType;
	}

	@Override
	public void capture(final HandleScope scope) {
		if (scope.hasHandle(name)) {
			final Handle existingHandle = scope.getHandle(name);
			if (existingHandle.getType() != scope.getHandleType(handleType.getName())) {
				throw new IllegalArgumentException("Unable to change type of variable: " + name + " to " + handleType
						+ ", but it is of type " + existingHandle.getType());
			}
			scope.createHandle(name, handleType.makeNew(scope));
		} else {
			scope.createHandle(name, handleType.makeNew(scope));
		}
	}

	@Override
	public String getName() {
		return name;
	}

}
