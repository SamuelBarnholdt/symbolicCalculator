package ioopm.calculator.visitor;

import ioopm.calculator.ast.*;

/*!
 *\brief Evaluates if an expression reassigns namedconstants.
 */
public class NamedConstantChecker extends SyntaxVisitor implements Visitor {
    public NamedConstantChecker(EvaluationVisitor e) {
	super(e);
    }

    @Override
    public SymbolicExpression visit(Assignment n) {
	n.getLhs().accept(this);
	if (Constants.namedConstants.containsKey(n.getRhs().getName())) {
	    return null;
	}
	return n;
    }

    @Override
    public SymbolicExpression visit(Scope n) {
	return null;
    }

    @Override
    public SymbolicExpression visit(Conditional n) {
	return null;
    }
}
