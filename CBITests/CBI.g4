/**
 * Define a grammar called FIZ
 */
grammar CBI;

@header {
	package com.etheller.fiz.parser;
}

program :
	(funcDeclStatement)*
	;
	
statement :
	funcDeclStatement #FunctionDeclarationPassThroughStatement
	| CAPTURE (capturable) (',' capturable)*  ';' #CaptureStatement
	| CAPTURE '{' (capturable) (',' capturable)* '}'  ';' #CompoundCaptureStatement
	| (declaration) (',' declaration)* ';' #DeclarationStatement
	| expr  ';' # ExpressionStatement
	;

funcDeclStatement :
	FUNC ID functionalExpression # FunctionDefinitionStatement
	;
	
	
functionArgs :
	'(' ((functionArg) (',' functionArg)*)? ')'
	|
	;
	
functionArg :
	ID ID
	;

capturable :
	ID ID declarationGuy expr #AssignedCapturable
	|
	ID declarationGuy expr #AssignedImplicitTypeCapturable
	|
	ID ID #TypedCapturable
	|
	ID #UntypedCapturable
	;
	
declaration :
	ID ID declarationGuy expr #AssignedDeclaration
	|
	ID ID #TypedDeclaration
	;
	


declarationGuy:
	LEFT_ARROW
	|
	':='
	;

expr  :
	leftAssignSubexpression	 # LeftAssignmentSubExpression
	| expr RIGHT_ARROW assignableExpression	# RightAssignmentExpression
	| 'maybe' expr '?' expr 'or just' expr #MaybeExpression
	;
	
leftAssignSubexpression :
	booleanCheckerExpression # ExprWithValueSubExpression
	|
	assignableExpression LEFT_ARROW leftAssignSubexpression # LeftAssignmentExpression
	; 

booleanCheckerExpression :
	simpleArithmeticExpression # PassBooleanThroughExpression
	|
	assignableExpression '>=' booleanCheckerExpression # GreaterThanOrEqualsExpression
	|
	assignableExpression '==' booleanCheckerExpression # DoubleEqualsExpression
	|
	assignableExpression '<=' booleanCheckerExpression # LessThanOrEqualsExpression
	|
	assignableExpression '>' booleanCheckerExpression # GreaterExpression
	|
	assignableExpression '<' booleanCheckerExpression # LessExpression
	;
	
simpleArithmeticExpression :
	simpleArithmeticExpression '+' simpleFactorExpression #PlusExpression
	|
	simpleArithmeticExpression '-' simpleFactorExpression #MinusExpression
	|
	simpleFactorExpression #PassThroughMathExpression
	;
	
simpleFactorExpression :
	simpleFactorExpression '*' expressionWithValue #TimesExpression
	|
	simpleFactorExpression '/' expressionWithValue #DivideExpression
	|
	expressionWithValue #PassThroughMathExpression2
	;
	
expressionWithValue :
	constant # ConstantExpression
	|
	assignableExpression #AssignableSubexpression
	;
	
assignableExpression :
	assignableExpression '.' ID #DotOperatorReferenceExpression
	|
	ID # ReferenceExpression
	|
	'(' expr ')' #ParenthesisExpression
	|
	functionalExpression #FunctionalSubexpression
	;
	
	
	
functionalExpression :
	functionArgs '[' (statement)* ']' #FunctionalStatementsExpression
	;
	
	
reference :
	ID #DirectReference
	;

constant
	: NUM	# IntegerLiteral
	| STRING_LITERAL # StringLiteral
	;

FUNC : 'func' ;

CAPTURE : 'capture' ;

LEFT_ARROW : '<--' ;

RIGHT_ARROW : '-->' ;

ID : ([a-zA-Z_][a-zA-Z_0-9]*) ;

STRING_LITERAL : ('"'.*?'"');

NUM : [0]|([1-9][0-9]*) ;

WS : [ \t\r\n]+ -> skip ;

COMMENT : '@'.*?'\n' -> skip ;

