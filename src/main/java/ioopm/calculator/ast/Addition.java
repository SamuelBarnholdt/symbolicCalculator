package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

public class Addition extends Binary {
    public Addition(SymbolicExpression a, SymbolicExpression b) {
        assignExpression(a, b);
    }

    @Override
    public SymbolicExpression eval(Environment environment) throws IllegalExpressionException {
        SymbolicExpression lhsEval = lhs.eval(environment);
        SymbolicExpression rhsEval = rhs.eval(environment);

        if (!lhsEval.isConstant() || !rhsEval.isConstant()) {
            if (rhsEval.isConstant()) 
                return new Addition(lhsEval, rhs.simplify());
            else if (lhsEval.isConstant())
                return new Addition(lhs.simplify(), rhs.eval(environment));
            else 
                return new Addition(lhsEval, rhsEval);
        }

        return this.simplify();
    }

    @Override
    public double getValue() {
        return this.lhs.getValue() + this.rhs.getValue();
    }

    @Override
    public double getPriority() {
        return 50.0;
    }

    @Override
    public String getName() {
        return "+";
    }

    public boolean equals(Addition other) {
        return other.lhs.equals(this.lhs) && other.rhs.equals(this.rhs);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Addition) {
            return this.equals((Addition) other);
        }
        else return false;
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }
}

