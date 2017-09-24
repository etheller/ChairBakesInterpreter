package com.etheller.cbi.tree;

public interface Capturable {
	void capture(HandleScope scope);

	String getName();
}
