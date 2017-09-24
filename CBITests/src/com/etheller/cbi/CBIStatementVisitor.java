package com.etheller.cbi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etheller.cbi.tree.BooleanValue;
import com.etheller.cbi.tree.Capturable;
import com.etheller.cbi.tree.CaptureListValue;
import com.etheller.cbi.tree.CaptureStatement;
import com.etheller.cbi.tree.DynamicAssignableHandleScope;
import com.etheller.cbi.tree.Expression;
import com.etheller.cbi.tree.ExpressionStatement;
import com.etheller.cbi.tree.Function;
import com.etheller.cbi.tree.FunctionDefinitionStatement;
import com.etheller.cbi.tree.FunctionDefinitionValue;
import com.etheller.cbi.tree.FunctionHandle;
import com.etheller.cbi.tree.FunctionInstanceValue;
import com.etheller.cbi.tree.FunctionParameter;
import com.etheller.cbi.tree.FunctionalStatementsExpression;
import com.etheller.cbi.tree.Handle;
import com.etheller.cbi.tree.HandleScope;
import com.etheller.cbi.tree.IntegerValue;
import com.etheller.cbi.tree.NilValue;
import com.etheller.cbi.tree.Statement;
import com.etheller.cbi.tree.StringHandleType;
import com.etheller.cbi.tree.StringValue;
import com.etheller.cbi.tree.TypedVariableHandle;
import com.etheller.cbi.tree.ValueVisitor;
import com.etheller.fiz.parser.CBIBaseVisitor;
import com.etheller.fiz.parser.CBIParser.CapturableContext;
import com.etheller.fiz.parser.CBIParser.CaptureStatementContext;
import com.etheller.fiz.parser.CBIParser.CompoundCaptureStatementContext;
import com.etheller.fiz.parser.CBIParser.DeclarationContext;
import com.etheller.fiz.parser.CBIParser.DeclarationStatementContext;
import com.etheller.fiz.parser.CBIParser.ExpressionStatementContext;
import com.etheller.fiz.parser.CBIParser.FuncDeclStatementContext;
import com.etheller.fiz.parser.CBIParser.FunctionDefinitionStatementContext;
import com.etheller.fiz.parser.CBIParser.ProgramContext;

public final class CBIStatementVisitor extends CBIBaseVisitor<Statement> {
	private final HandleScope globalScope;
	private final CBIReferenceVisitor referenceVisitor;
	private final CBIExpressionVisitor expressionVisitor;
	private final CBICapturableVisitor capturableVisitor;

	public CBIStatementVisitor() {
		final Map<String, Handle> defaultHandles = new HashMap<>();
		createNatives(defaultHandles);
		this.globalScope = new DynamicAssignableHandleScope(defaultHandles, defaultHandles);
		this.referenceVisitor = new CBIReferenceVisitor();
		this.expressionVisitor = new CBIExpressionVisitor(referenceVisitor, this);
		this.capturableVisitor = new CBICapturableVisitor(expressionVisitor);
	}

	@Override
	public Statement visitExpressionStatement(final ExpressionStatementContext ctx) {
		final Expression expression = expressionVisitor.visit(ctx.expr());
		return new ExpressionStatement(expression);
	}

	@Override
	public Statement visitCaptureStatement(final CaptureStatementContext ctx) {
		final List<Capturable> capturables = new ArrayList<>();
		for (final CapturableContext capturableContext : ctx.capturable()) {
			final Capturable capturable = capturableVisitor.visit(capturableContext);
			capturables.add(capturable);
		}
		return new CaptureStatement(capturables);
	}

	@Override
	public Statement visitDeclarationStatement(final DeclarationStatementContext ctx) {
		final List<Capturable> capturables = new ArrayList<>();
		for (final DeclarationContext capturableContext : ctx.declaration()) {
			final Capturable capturable = capturableVisitor.visit(capturableContext);
			capturables.add(capturable);
		}
		return new CaptureStatement(capturables);
	}

	@Override
	public Statement visitCompoundCaptureStatement(final CompoundCaptureStatementContext ctx) {
		final List<Capturable> capturables = new ArrayList<>();
		for (final CapturableContext capturableContext : ctx.capturable()) {
			final Capturable capturable = capturableVisitor.visit(capturableContext);
			capturables.add(capturable);
		}
		return new CaptureStatement(capturables);
	}

	@Override
	public Statement visitFunctionDefinitionStatement(final FunctionDefinitionStatementContext ctx) {
		final String funcName = ctx.ID().getText();
		final Expression expression = expressionVisitor.visit(ctx.functionalExpression());
		// TODO fix bad cast
		final FunctionalStatementsExpression functionalStatementsExpression = (FunctionalStatementsExpression) expression;
		functionalStatementsExpression.setAnonymous(false);
		return new FunctionDefinitionStatement(funcName, functionalStatementsExpression);
	}

	@Override
	public Statement visitProgram(final ProgramContext ctx) {
		for (final FuncDeclStatementContext statement : ctx.funcDeclStatement()) {
			visit(statement).execute(globalScope);
		}
		globalScope.getHandle("main").resolve().apply(new ValueVisitor() {

			@Override
			public void accept(final FunctionInstanceValue funcValue) {
				System.out.println(funcValue);
			}

			@Override
			public void accept(final FunctionDefinitionValue functionDeclarationValue) {
				functionDeclarationValue.getFunction().doFunction(globalScope.createEmptyScope());
			}

			@Override
			public void accept(final NilValue nilValue) {
				System.out.println(nilValue);
			}

			@Override
			public void accept(final StringValue stringValue) {
				System.out.println(stringValue);
			}

			@Override
			public void accept(final IntegerValue integerValue) {
				System.out.println(integerValue);
			}

			@Override
			public void accept(final CaptureListValue captureListValue) {
				System.out.println(captureListValue);
			}

			@Override
			public void accept(final BooleanValue booleanValue) {
				System.out.println(booleanValue.isTruthy());
			}
		});
		return null;
	}

	private void createNatives(final Map<String, Handle> defaultHandles) {
		defaultHandles.put("print", new FunctionHandle(new Function() {
			@Override
			public HandleScope doFunction(final HandleScope handleScope) {
				final HandleScope createFunctionScope = handleScope.createFunctionScope(
						Arrays.asList(new FunctionParameter(new StringHandleType("string"), "text")), false);
				createFunctionScope.getHandle("text").resolve().apply(new ValueVisitor() {
					@Override
					public void accept(final NilValue nilValue) {
						System.out.println(nilValue.toString());
					}

					@Override
					public void accept(final StringValue stringValue) {
						System.out.println(stringValue.toString());
					}

					@Override
					public void accept(final IntegerValue integerValue) {
						System.out.println(integerValue.toString());
					}

					@Override
					public void accept(final CaptureListValue captureListValue) {
						System.out.println("{captureList}");
					}

					@Override
					public void accept(final FunctionDefinitionValue nilValue) {
						System.out.println("{functionDefinition}");
					}

					@Override
					public void accept(final FunctionInstanceValue nilValue) {
						System.out.println(nilValue.getFunction().doFunction(handleScope));
					}

					@Override
					public void accept(final BooleanValue booleanValue) {
						System.out.println(booleanValue.isTruthy());
					}
				});
				return globalScope;
			}
		}));
		defaultHandles.put("itoa", new FunctionHandle(new Function() {
			@Override
			public HandleScope doFunction(final HandleScope handleScope) {
				final HandleScope createFunctionScope = handleScope.createFunctionScope(
						Arrays.asList(new FunctionParameter(new StringHandleType("int"), "integer")), false);
				createFunctionScope.getHandle("integer").resolve().apply(new ValueVisitor() {
					@Override
					public void accept(final NilValue nilValue) {
						throw new IllegalStateException("invalid type for native func");
					}

					@Override
					public void accept(final StringValue stringValue) {
						throw new IllegalStateException("invalid type for native func");
					}

					@Override
					public void accept(final IntegerValue integerValue) {
						createFunctionScope.createHandle("result",
								new TypedVariableHandle(new StringValue(Integer.toString(integerValue.getValue()))));
						createFunctionScope.capture("result");
					}

					@Override
					public void accept(final CaptureListValue captureListValue) {
						throw new IllegalStateException("invalid type for native func");
					}

					@Override
					public void accept(final FunctionDefinitionValue nilValue) {
						throw new IllegalStateException("invalid type for native func");
					}

					@Override
					public void accept(final FunctionInstanceValue nilValue) {
						throw new IllegalStateException("invalid type for native func");
					}

					@Override
					public void accept(final BooleanValue booleanValue) {
						throw new IllegalStateException("invalid type for native func");

					}
				});
				return createFunctionScope;
			}
		}));
	}
}