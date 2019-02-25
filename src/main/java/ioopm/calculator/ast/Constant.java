package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

public class Constant extends Atom {
    private double value;

    public Constant(double value) {
	this.value = value;
    }

    @Override
    public SymbolicExpression eval(Environment environment) { return this; }

    @Override
    public double getValue() { return this.value; }

    @Override
    public boolean isConstant() { return true; }

    @Override
    public String toString() { return "" + this.value; }

    public boolean equals(Constant other) {
	return this.value == other.value;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Constant) {
            return this.equals((Constant) other);
        }
        else return false;
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
	return v.visit(this);
    }
}
