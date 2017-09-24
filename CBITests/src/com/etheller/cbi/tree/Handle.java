package com.etheller.cbi.tree;

public interface Handle {
	Handle assign(Handle expression, HandleScope handleScope);

	Value resolve();

	HandleType getType();
}
