package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

public class Negation extends Unary {
    public Negation(SymbolicExpression a ) {
        this.root = a;
    }

    @Override
    public SymbolicExpression eval(Environment environment) throws IllegalExpressionException {
        if (root.eval(environment).isConstant()) return this.simplify();
        else return new Negation(root.eval(environment));
    }

    @Override
    protected SymbolicExpression simplify() {
        return new Constant(this.getValue());
    }

    @Override
    public String toString() {
        return "(-" + this.root.toString() + ")";
    }

    @Override
    public double getPriority() {
        return 101;
    }

    @Override
    public double getValue() {
        return 0 - root.getValue();
    }

    @Override
    public String getName() {
        return "-";
    }

    public boolean equals(Negation a) {
        return this.root.equals(a.root);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Negation) {
            return this.equals((Negation) other);
        }
        else return false;
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }
}
