package com.etheller.cbi;

import com.etheller.cbi.parser.CBIBaseVisitor;
import com.etheller.cbi.parser.CBIParser.DirectReferenceContext;
import com.etheller.cbi.tree.Reference;
import com.etheller.cbi.tree.VariableReference;

public final class CBIReferenceVisitor extends CBIBaseVisitor<Reference> {

	@Override
	public Reference visitDirectReference(final DirectReferenceContext ctx) {
		final Reference reference = new VariableReference(ctx.ID().getText());
		return reference;
	}
}