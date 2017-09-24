package com.etheller.cbi.tree;

public interface Expression {
	Handle evaluate(final HandleScope scope);
}
