package com.etheller.cbi;

import java.util.ArrayList;
import java.util.List;

import com.etheller.cbi.tree.AssignmentExpression;
import com.etheller.cbi.tree.ConstantValueExpression;
import com.etheller.cbi.tree.DivideExpression;
import com.etheller.cbi.tree.DoubleEqualsExpression;
import com.etheller.cbi.tree.Expression;
import com.etheller.cbi.tree.FieldLookupExpression;
import com.etheller.cbi.tree.FunctionParameter;
import com.etheller.cbi.tree.FunctionalStatementsExpression;
import com.etheller.cbi.tree.GreaterExpression;
import com.etheller.cbi.tree.GreaterOrEqualsExpression;
import com.etheller.cbi.tree.IntegerValue;
import com.etheller.cbi.tree.LessExpression;
import com.etheller.cbi.tree.LessOrEqualsExpression;
import com.etheller.cbi.tree.MaybeExpression;
import com.etheller.cbi.tree.MinusExpression;
import com.etheller.cbi.tree.NameReferenceExpression;
import com.etheller.cbi.tree.PlusExpression;
import com.etheller.cbi.tree.Statement;
import com.etheller.cbi.tree.StringHandleType;
import com.etheller.cbi.tree.StringValue;
import com.etheller.cbi.tree.TimesExpression;
import com.etheller.fiz.parser.CBIBaseVisitor;
import com.etheller.fiz.parser.CBIParser.DivideExpressionContext;
import com.etheller.fiz.parser.CBIParser.DotOperatorReferenceExpressionContext;
import com.etheller.fiz.parser.CBIParser.DoubleEqualsExpressionContext;
import com.etheller.fiz.parser.CBIParser.FunctionArgContext;
import com.etheller.fiz.parser.CBIParser.FunctionalStatementsExpressionContext;
import com.etheller.fiz.parser.CBIParser.GreaterExpressionContext;
import com.etheller.fiz.parser.CBIParser.GreaterThanOrEqualsExpressionContext;
import com.etheller.fiz.parser.CBIParser.IntegerLiteralContext;
import com.etheller.fiz.parser.CBIParser.LeftAssignmentExpressionContext;
import com.etheller.fiz.parser.CBIParser.LeftAssignmentSubExpressionContext;
import com.etheller.fiz.parser.CBIParser.LessExpressionContext;
import com.etheller.fiz.parser.CBIParser.LessThanOrEqualsExpressionContext;
import com.etheller.fiz.parser.CBIParser.MaybeExpressionContext;
import com.etheller.fiz.parser.CBIParser.MinusExpressionContext;
import com.etheller.fiz.parser.CBIParser.ParenthesisExpressionContext;
import com.etheller.fiz.parser.CBIParser.PlusExpressionContext;
import com.etheller.fiz.parser.CBIParser.ReferenceExpressionContext;
import com.etheller.fiz.parser.CBIParser.RightAssignmentExpressionContext;
import com.etheller.fiz.parser.CBIParser.StatementContext;
import com.etheller.fiz.parser.CBIParser.StringLiteralContext;
import com.etheller.fiz.parser.CBIParser.TimesExpressionContext;

public final class CBIExpressionVisitor extends CBIBaseVisitor<Expression> {
	private final CBIReferenceVisitor referenceVisitor;
	private final CBIStatementVisitor statementVisitor;

	public CBIExpressionVisitor(final CBIReferenceVisitor referenceVisitor,
			final CBIStatementVisitor statementVisitor) {
		this.referenceVisitor = referenceVisitor;
		this.statementVisitor = statementVisitor;
	}

	@Override
	public Expression visitLeftAssignmentSubExpression(final LeftAssignmentSubExpressionContext ctx) {
		return visit(ctx.leftAssignSubexpression());
	}

	@Override
	public Expression visitParenthesisExpression(final ParenthesisExpressionContext ctx) {
		return visit(ctx.expr());
	}

	@Override
	public Expression visitLeftAssignmentExpression(final LeftAssignmentExpressionContext ctx) {
		final AssignmentExpression assignmentExpression = new AssignmentExpression(visit(ctx.assignableExpression()),
				visit(ctx.leftAssignSubexpression()));
		return assignmentExpression;
	}

	@Override
	public Expression visitRightAssignmentExpression(final RightAssignmentExpressionContext ctx) {
		final AssignmentExpression assignmentExpression = new AssignmentExpression(visit(ctx.assignableExpression()),
				visit(ctx.expr()));
		return assignmentExpression;
	}

	@Override
	public Expression visitPlusExpression(final PlusExpressionContext ctx) {
		return new PlusExpression(visit(ctx.simpleArithmeticExpression()), visit(ctx.simpleFactorExpression()));
	}

	@Override
	public Expression visitMinusExpression(final MinusExpressionContext ctx) {
		return new MinusExpression(visit(ctx.simpleArithmeticExpression()), visit(ctx.simpleFactorExpression()));
	}

	@Override
	public Expression visitTimesExpression(final TimesExpressionContext ctx) {
		return new TimesExpression(visit(ctx.simpleFactorExpression()), visit(ctx.expressionWithValue()));
	}

	@Override
	public Expression visitDivideExpression(final DivideExpressionContext ctx) {
		return new DivideExpression(visit(ctx.simpleFactorExpression()), visit(ctx.expressionWithValue()));
	}

	@Override
	public Expression visitMaybeExpression(final MaybeExpressionContext ctx) {
		return new MaybeExpression(visit(ctx.expr(0)), visit(ctx.expr(1)), visit(ctx.expr(2)));
	}

	@Override
	public Expression visitIntegerLiteral(final IntegerLiteralContext ctx) {
		return new ConstantValueExpression(new IntegerValue(Integer.parseInt(ctx.NUM().getText())));
	}

	@Override
	public Expression visitGreaterThanOrEqualsExpression(final GreaterThanOrEqualsExpressionContext ctx) {
		return new GreaterOrEqualsExpression(visit(ctx.assignableExpression()), visit(ctx.booleanCheckerExpression()));
	}

	@Override
	public Expression visitGreaterExpression(final GreaterExpressionContext ctx) {
		return new GreaterExpression(visit(ctx.assignableExpression()), visit(ctx.booleanCheckerExpression()));
	}

	@Override
	public Expression visitLessExpression(final LessExpressionContext ctx) {
		return new LessExpression(visit(ctx.assignableExpression()), visit(ctx.booleanCheckerExpression()));
	}

	@Override
	public Expression visitLessThanOrEqualsExpression(final LessThanOrEqualsExpressionContext ctx) {
		return new LessOrEqualsExpression(visit(ctx.assignableExpression()), visit(ctx.booleanCheckerExpression()));
	}

	@Override
	public Expression visitDoubleEqualsExpression(final DoubleEqualsExpressionContext ctx) {
		return new DoubleEqualsExpression(visit(ctx.assignableExpression()), visit(ctx.booleanCheckerExpression()));
	}

	@Override
	public Expression visitStringLiteral(final StringLiteralContext ctx) {
		final String text = ctx.STRING_LITERAL().getText();
		return new ConstantValueExpression(new StringValue(text.substring(1, text.length() - 1)));
	}

	@Override
	public Expression visitReferenceExpression(final ReferenceExpressionContext ctx) {
		return new NameReferenceExpression(ctx.ID().getText());
	}

	@Override
	public Expression visitDotOperatorReferenceExpression(final DotOperatorReferenceExpressionContext ctx) {
		final Expression parentExpression = visit(ctx.assignableExpression());
		return new FieldLookupExpression(parentExpression, ctx.ID().getText());
	}

	@Override
	public Expression visitFunctionalStatementsExpression(final FunctionalStatementsExpressionContext ctx) {
		final List<Statement> statements = new ArrayList<>();
		for (final StatementContext statementContext : ctx.statement()) {
			final Statement statement = statementVisitor.visit(statementContext);
			statements.add(statement);
		}
		final List<FunctionParameter> parameters = new ArrayList<>();
		for (final FunctionArgContext funcCtx : ctx.functionArgs().functionArg()) {
			parameters
					.add(new FunctionParameter(new StringHandleType(funcCtx.ID(0).getText()), funcCtx.ID(1).getText()));
		}
		return new FunctionalStatementsExpression(parameters, statements, true);
	}
}