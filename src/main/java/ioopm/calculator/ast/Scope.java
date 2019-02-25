package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

public class Scope extends Unary {
    public Scope(SymbolicExpression n) {
	root = n;
    }

    @Override
    public SymbolicExpression eval(Environment environment) throws IllegalExpressionException {
	return null;
    }

    @Override
    protected SymbolicExpression simplify() {
	return null;
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
	return v.visit(this);
    }

    @Override
    public String toString() {
	return "{" + root.toString() + "}";
    }

    @Override
    public double getValue() {
	return root.getValue();
    }

    @Override
    public double getPriority() {
	return 0;
    }

    @Override
    public String getName() {
	return "scope";
    }
}
