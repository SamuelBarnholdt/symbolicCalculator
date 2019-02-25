package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

public class Cos extends Unary {
    public Cos(SymbolicExpression a ) {
        this.root = a;
    }

    @Override
    public SymbolicExpression eval(Environment environment) throws IllegalExpressionException {
        if (root.eval(environment).isConstant()) return this.simplify();
        else return new Cos(root.eval(environment));
    }

    @Override
    public double getValue() {
        return Math.cos(root.getValue());
    }

    @Override
    public String getName() {
        return "cos";
    }

    public boolean equals(Cos a) {
        return this.root.equals(a.root);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Cos) {
            return this.equals((Cos) other);
        }
        else return false;
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }
}
