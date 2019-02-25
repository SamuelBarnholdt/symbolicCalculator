package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

public class Division extends Binary{
    public Division(SymbolicExpression a, SymbolicExpression b) {
        if (b.isConstant() && b.getValue() == 0) {
            throw new RuntimeException("Division by zero");
        }

        assignExpression(a, b);
    }

    @Override
    public SymbolicExpression eval(Environment environment) throws IllegalExpressionException {
        SymbolicExpression lhsEval = lhs.eval(environment);
        SymbolicExpression rhsEval = rhs.eval(environment);

        if (!lhsEval.isConstant() || !rhsEval.isConstant()) {
            if (rhsEval.isConstant()) 
                return new Division(lhsEval, rhs.simplify());
            else if (lhsEval.isConstant())
                return new Division(lhs.simplify(), rhs.eval(environment));
            else 
                return new Division(lhsEval, rhsEval);
        }

        return this.simplify();
    }

    @Override
    public double getValue() {
        return this.lhs.getValue() / this.rhs.getValue();
    }

    @Override
    public double getPriority() {
        return 100.0;
    }

    @Override
    public String getName() {
        return "/";
    }

    public boolean equals(Division other) {
        return other.lhs.equals(this.lhs) && other.rhs.equals(this.rhs);
    }

    public boolean equals(Object other) {
        if (other instanceof Division) {
            return this.equals((Division) other);
        }
        else return false;
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }
}
