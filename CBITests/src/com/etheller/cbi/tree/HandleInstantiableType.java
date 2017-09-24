package com.etheller.cbi.tree;

public interface HandleInstantiableType {
	Handle makeNew(HandleScope scope);

	String getName();
}
