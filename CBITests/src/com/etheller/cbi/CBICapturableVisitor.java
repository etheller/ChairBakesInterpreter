package com.etheller.cbi;

import com.etheller.cbi.tree.AssignedCapturable;
import com.etheller.cbi.tree.AssignedDeclaration;
import com.etheller.cbi.tree.Capturable;
import com.etheller.cbi.tree.HandleType;
import com.etheller.cbi.tree.StringHandleType;
import com.etheller.cbi.tree.TypedCapturable;
import com.etheller.cbi.tree.TypedDeclaration;
import com.etheller.cbi.tree.UntypedCapturable;
import com.etheller.fiz.parser.CBIBaseVisitor;
import com.etheller.fiz.parser.CBIParser.AssignedCapturableContext;
import com.etheller.fiz.parser.CBIParser.AssignedDeclarationContext;
import com.etheller.fiz.parser.CBIParser.AssignedImplicitTypeCapturableContext;
import com.etheller.fiz.parser.CBIParser.TypedCapturableContext;
import com.etheller.fiz.parser.CBIParser.TypedDeclarationContext;
import com.etheller.fiz.parser.CBIParser.UntypedCapturableContext;

public final class CBICapturableVisitor extends CBIBaseVisitor<Capturable> {
	private final CBIExpressionVisitor expressionVisitor;

	public CBICapturableVisitor(final CBIExpressionVisitor expressionVisitor) {
		this.expressionVisitor = expressionVisitor;
	}

	@Override
	public Capturable visitTypedCapturable(final TypedCapturableContext ctx) {
		return new TypedCapturable(ctx.ID(1).getText(), HandleType.valueOf(ctx.ID(0).getText().toUpperCase()));
	}

	@Override
	public Capturable visitUntypedCapturable(final UntypedCapturableContext ctx) {
		return new UntypedCapturable(ctx.ID().getText());
	}

	@Override
	public Capturable visitAssignedCapturable(final AssignedCapturableContext ctx) {
		return new AssignedCapturable(ctx.ID(1).getText(), HandleType.valueOf(ctx.ID(0).getText().toUpperCase()),
				expressionVisitor.visit(ctx.expr()));
	}

	@Override
	public Capturable visitAssignedImplicitTypeCapturable(final AssignedImplicitTypeCapturableContext ctx) {
		return new AssignedCapturable(ctx.ID().getText(), null, expressionVisitor.visit(ctx.expr()));
	}

	@Override
	public Capturable visitAssignedDeclaration(final AssignedDeclarationContext ctx) {
		return new AssignedDeclaration(ctx.ID(1).getText(), new StringHandleType(ctx.ID(0).getText()),
				expressionVisitor.visit(ctx.expr()));
	}

	@Override
	public Capturable visitTypedDeclaration(final TypedDeclarationContext ctx) {
		return new TypedDeclaration(ctx.ID(1).getText(), new StringHandleType(ctx.ID(0).getText()));
	}
}
