package com.etheller.cbi.tree;

import java.util.List;

public interface HandleScope {
	Handle getHandle(String key);

	void putHandle(String key, Handle handle);

	boolean hasHandle(String key);

	boolean createHandle(String key, Handle handle);

	void capture(String key);

	HandleType getHandleType(String key);

	HandleScope captureScope(boolean keepCaptureList);

	HandleScope createEmptyScope();

	// TODO remove share, not used, always false
	HandleScope createFunctionScope(List<FunctionParameter> parameter, boolean isAnonymous);

	Handle getFirstCapture();
}
