package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

public class Log extends Unary {
    public Log(SymbolicExpression a ) {
        this.root = a;
    }

    @Override
    public SymbolicExpression eval(Environment environment) throws IllegalExpressionException {
        if (root.eval(environment).isConstant()) return this.simplify();
        else return new Log(root.eval(environment));
    }

    @Override
    public double getValue() {
        return Math.log(root.getValue());
    }

    @Override
    public String getName() {
        return "log";
    }

    public boolean equals(Log a) {
        return this.root.equals(a.root);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Log) {
            return this.equals((Log) other);
        }
        else return false;
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }
}
