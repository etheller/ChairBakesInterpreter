package com.etheller.cbi.tree;

import com.etheller.cbi.tree.exception.CBIExecutionException;

public class StatementWithLineNumber implements Statement {
	private final Statement delegate;
	private final String sourceName;
	private final int lineNumber;

	public StatementWithLineNumber(final Statement delegate, final String sourceName, final int lineNumber) {
		this.delegate = delegate;
		this.sourceName = sourceName;
		this.lineNumber = lineNumber;
	}

	@Override
	public void execute(final HandleScope scope) {
		try {
			delegate.execute(scope);
		} catch (final Exception exc) {
			throw new CBIExecutionException(String.format("%s\n\t at %s:%d", exc.getMessage(), sourceName, lineNumber),
					exc);
		}
	}

}
