package com.etheller.cbi.tree;

public interface ValueVisitor {
	void accept(CaptureListValue captureListValue);

	void accept(IntegerValue integerValue);

	void accept(StringValue stringValue);

	void accept(BooleanValue booleanValue);

	void accept(NilValue nilValue);

	void accept(FunctionDefinitionValue funcDefValue);

	void accept(FunctionInstanceValue funcValue);
}
