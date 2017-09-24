package com.etheller.cbi.tree;

public final class TypedCapturable implements Capturable {
	private final String name;
	private final HandleType handleType;

	public TypedCapturable(final String name, final HandleType handleType) {
		this.name = name;
		this.handleType = handleType;
	}

	@Override
	public void capture(final HandleScope scope) {
		if (scope.hasHandle(name)) {
			final Handle existingHandle = scope.getHandle(name);
			if (existingHandle.getType() != handleType) {
				throw new IllegalArgumentException("Unable to capture variable of a different type: " + name + " as "
						+ handleType + ", but it is of type " + existingHandle.getType());
			}
		} else {
			scope.createHandle(name, new TypedVariableHandle(handleType));
		}
		scope.capture(name);
	}

	@Override
	public String getName() {
		return name;
	}

}
