package com.etheller.cbi;

import com.etheller.cbi.tree.Reference;
import com.etheller.cbi.tree.VariableReference;
import com.etheller.fiz.parser.CBIBaseVisitor;
import com.etheller.fiz.parser.CBIParser.DirectReferenceContext;

public final class CBIReferenceVisitor extends CBIBaseVisitor<Reference> {

	@Override
	public Reference visitDirectReference(final DirectReferenceContext ctx) {
		final Reference reference = new VariableReference(ctx.ID().getText());
		return reference;
	}
}