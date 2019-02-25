package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

public class Sin extends Unary {
    public Sin(SymbolicExpression a ) {
        this.root = a;
    }

    @Override
    public SymbolicExpression eval(Environment environment) throws IllegalExpressionException {
        if (root.eval(environment).isConstant()) 
            return this.simplify();
        else 
            return new Sin(root.eval(environment));
    }

    @Override
    public double getValue() {
        return Math.sin(root.getValue());
    }

    @Override
    public String getName() {
        return "sin";
    }

    public boolean equals(Sin a) {
        return this.root.equals(a.root);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Sin) {
            return this.equals((Sin) other);
        }
        else return false;
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }
}
