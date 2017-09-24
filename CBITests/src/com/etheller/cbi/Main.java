package com.etheller.cbi;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import com.etheller.fiz.parser.CBILexer;
import com.etheller.fiz.parser.CBIParser;

public class Main {
	public static final boolean REPORT_SYNTAX_ERRORS = true;

	public static void main(final String[] args) {
		try {
			final CBILexer lexer = new CBILexer(CharStreams.fromFileName("fib.cb"));
			final CBIParser parser = new CBIParser(new CommonTokenStream(lexer));
			parser.addErrorListener(new BaseErrorListener() {
				@Override
				public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol, final int line,
						final int charPositionInLine, final String msg, final RecognitionException e) {
					if (!REPORT_SYNTAX_ERRORS) {
						return;
					}

					String sourceName = recognizer.getInputStream().getSourceName();
					if (!sourceName.isEmpty()) {
						sourceName = String.format("%s:%d:%d: ", sourceName, line, charPositionInLine);
					}

					System.err.println(sourceName + "line " + line + ":" + charPositionInLine + " " + msg);
				}
			});
			final CBIStatementVisitor fizEvaluator = new CBIStatementVisitor();
			fizEvaluator.visit(parser.program());
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}
