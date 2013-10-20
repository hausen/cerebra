grammar Cerebra;

tokens {
	NOT='!';
	AND='&';
	XOR='+';
	OR='|';
}

@header {

}

@members {
ProblemDescription pd = new ProblemDescription();
}

file returns [ProblemDescription description] throws Exception
	:	NEWLINE* definition+ NEWLINE+ exoprobability+ NEWLINE+ equation+ NEWLINE* outputDef*
                {
                	$description = pd;
                }
	;

definition
	:	'EXO' ':' varlist NEWLINE
		{
			pd.commitVariableListToNonObservables();
		}
	|	'OBS' ':' varlist NEWLINE
		{
			pd.commitVariableListToObservables();
		}
	;

varlist
	:	VARIABLE ',' varlist
		{
			pd.pushVariable($VARIABLE.text);
		}
	|	VARIABLE
		{
			pd.pushVariable($VARIABLE.text);
		}
	;

exoprobability
	:	VARIABLE '=' num=(FRACTIONAL|INTEGER) NEWLINE
                {
                	pd.setNonObservable($VARIABLE.text, $num.text);
                }
	;

equation
	:	VARIABLE '=' expression NEWLINE
		{
			pd.setObservable($VARIABLE.text);
		}
	;

expression
	:	xorExpr 
		(	OR xorExpr
	        	{
	        		pd.pushOrToExpression();
	       		}
	       	)*
	;

xorExpr	:	andExpr
		(	XOR andExpr
	        	{
	        		pd.pushXorToExpression();
	       		}
	       	)*
	;

andExpr	:	atom
		(	AND atom
	        	{
	        		pd.pushAndToExpression();
	       		}
		)*
	;

atom	:	VARIABLE
	        {
	        	pd.pushVariableToExpression($VARIABLE.text);
	        }
	|	INTEGER
		{
			pd.pushConstantToExpression($INTEGER.text);
		}
	|	'(' expression ')'
	|	NOT atom
		{
			pd.pushNotToExpression();
		}
	;

outputDef throws Exception
	:	'OUTPUT' outputNum=INTEGER ':' comment=STRING? NEWLINE*
	        'MARGIN' ':' varlist NEWLINE*
	        'INTERV' ':' intervention? ( ',' intervention )* NEWLINE*
	        ( 'COND' ':' condition? ( ',' condition )* NEWLINE* )?
		{
			pd.commitInterventionListToOutput($outputNum.text, $comment.text);
			pd.commitVariableListToOutput($outputNum.text);
			pd.commitConditionListToOutput($outputNum.text);
		}
	;

intervention
	:	VARIABLE '=' INTEGER
		{
			pd.pushIntervention($VARIABLE.text, $INTEGER.text);
		}
	;

condition
	:	VARIABLE '=' INTEGER
		{
			pd.pushCondition($VARIABLE.text, $INTEGER.text);
		}
	;


VARIABLE:	('a'..'z'|'A'..'Z'|'_')('a'..'z'|'A'..'Z'|'_'|'0'..'9')* ;
FRACTIONAL:	'0'?'.''0'..'9'+ ;
INTEGER	:	'0'..'9'+ ;
NEWLINE	:	'\r'? '\n' ;
STRING	:	'"'((~'"')|'\\"')*'"' ;
WS	:	(' '|'\t')+ {skip();} ;
COMMENT	:	'#' ~('\r'|'\n')* {skip();} ;
