package com.etheller.cbi.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class DynamicAssignableHandleScope implements HandleScope {

	private final Map<String, Handle> globals;
	private final Map<String, Handle> nameToReference;
	private final List<String> capturedNames;
	private final Handle selfHandle;

	public DynamicAssignableHandleScope(final Map<String, Handle> globals) {
		this.globals = globals;
		this.nameToReference = new HashMap<>();
		this.capturedNames = new ArrayList<>();
		this.selfHandle = new FunctionInstanceHandle(new FunctionInstanceValue(new DoNothingFunction(), this, this));
	}

	public DynamicAssignableHandleScope(final Map<String, Handle> globals, final Map<String, Handle> nameToReference) {
		this.globals = globals;
		this.nameToReference = nameToReference;
		this.capturedNames = new ArrayList<>();
		this.selfHandle = new FunctionInstanceHandle(new FunctionInstanceValue(new DoNothingFunction(), this, this));
	}

	@Override
	public Handle getHandle(final String key) {
		final Handle handle = nameToReference.get(key);
		if (handle == null) {
			final Handle globalHandle = globals.get(key);
			if (globalHandle == null) {
				throw new IllegalStateException("No such variable: " + key);
				// final TypedVariableHandle nilHandle = new
				// TypedVariableHandle(HandleType.NULL);
				// createHandle(key, nilHandle);
				// return nilHandle;
			}
			return globalHandle;
		}
		return handle;
	}

	@Override
	public boolean createHandle(final String key, final Handle handle) {
		return nameToReference.put(key, handle) == null;
	}

	@Override
	public boolean hasHandle(final String key) {
		final Handle handle = nameToReference.get(key);
		if (handle == null) {
			return globals.get(key) != null;
		}
		return true;
	}

	@Override
	public void capture(final String key) {
		capturedNames.add(key);
		if (key.equals("self")) {
			nameToReference.put("self", new FunctionInstanceHandle(
					new FunctionInstanceValue(new DoNothingFunction(), crazyDeepCopy(), this)));
		}
	}

	private HandleScope crazyDeepCopy() {
		final Map<String, Handle> keyToDeepCopy = new HashMap<>();
		for (final Map.Entry<String, Handle> entry : nameToReference.entrySet()) {
			keyToDeepCopy.put(entry.getKey(), new TypedVariableHandle(entry.getValue().resolve()));
		}
		final DynamicAssignableHandleScope dynamicAssignableHandleScope = new DynamicAssignableHandleScope(globals,
				keyToDeepCopy);
		dynamicAssignableHandleScope.capturedNames.addAll(capturedNames);
		return dynamicAssignableHandleScope;
	}

	@Override
	public HandleScope captureScope(final boolean keepCaptureList) {
		final DynamicAssignableHandleScope otherScope = new DynamicAssignableHandleScope(globals);
		// for (final FunctionParameter parameter : parameters) {
		// final TypedDeclaration typedDeclaration = new
		// TypedDeclaration(parameter.getName(), parameter.getType());
		// typedDeclaration.capture(capturedScope);
		// }
		for (final String key : capturedNames) {
			otherScope.nameToReference.put(key, nameToReference.get(key));
		}
		if (keepCaptureList) {
			otherScope.capturedNames.addAll(capturedNames);
		}
		return otherScope;
	}

	@Override
	public HandleScope createFunctionScope(final List<FunctionParameter> parameters, final boolean anonymous) {
		final DynamicAssignableHandleScope otherScope = new DynamicAssignableHandleScope(globals);
		final Iterator<String> captureNamesIterator = capturedNames.iterator();
		final Iterator<FunctionParameter> funcParamsIterator = parameters.iterator();
		while (funcParamsIterator.hasNext() && captureNamesIterator.hasNext()) {
			// if (!captureNamesIterator.hasNext()) {
			// throw new IllegalArgumentException("Not enough captured arguments
			// passed to function");
			// }
			final FunctionParameter nextParameter = funcParamsIterator.next();
			final String nextCaptureName = captureNamesIterator.next();
			final Handle nextCaptureHandle = getHandle(nextCaptureName);
			final HandleType paramType = getHandleType(nextParameter.getType().getName());
			if (nextCaptureHandle.getType() != paramType) {
				throw new IllegalArgumentException("Argument type mismatch: got \"" + nextCaptureHandle.getType() + " "
						+ nextCaptureName + "\" expected \"" + paramType + " " + nextParameter.getName() + "\"");
			}
			// TODO maybe Handle.assign here?
			otherScope.nameToReference.put(nextParameter.getName(), nextCaptureHandle);
		}
		// HandleScope result = otherScope;
		// if (anonymous) {
		// // otherScope.capturedNames.addAll(capturedNames);
		// result = new NestableScope(this, result);
		// }
		return otherScope;
	}

	@Override
	public void putHandle(final String key, final Handle handle) {
		nameToReference.put(key, handle);
	}

	@Override
	public HandleType getHandleType(final String key) {
		if (hasHandle(key)) {
			final Handle handle = getHandle(key);
			if (handle.getType() == HandleType.FUNCTION_DEFINITION) {
				return HandleType.FUNCTION_INSTANCE;
			}
		}
		return HandleType.valueOf(key.toUpperCase());
	}

	@Override
	public HandleScope createEmptyScope() {
		final DynamicAssignableHandleScope otherScope = new DynamicAssignableHandleScope(globals);
		return otherScope;
	}

	private static final class DoNothingFunction implements Function {
		@Override
		public HandleScope doFunction(final HandleScope handleScope) {
			return handleScope;
		}
	}

	@Override
	public Handle getFirstCapture() {
		if (capturedNames.isEmpty()) {
			throw new IllegalStateException("Can't auto promote capture list to value: capture list is empty");
		}
		return getHandle(capturedNames.get(0));
	}
}
