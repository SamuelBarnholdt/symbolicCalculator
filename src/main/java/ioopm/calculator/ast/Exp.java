package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

public class Exp extends Unary {
    public Exp(SymbolicExpression a ) {
        this.root = a;
    }

    @Override
    public SymbolicExpression eval(Environment environment) throws IllegalExpressionException {
        if (root.eval(environment).isConstant()) return this.simplify();
        else return new Exp(root.eval(environment));
    }

    @Override
    public double getValue() {
        return Math.exp(root.getValue());
    }

    @Override
    public String getName() {
        return "exp";
    }

    public boolean equals(Exp a) {
        return this.root.equals(a.root);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Exp) {
            return this.equals((Exp) other);
        }
        else return false;
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }
}

