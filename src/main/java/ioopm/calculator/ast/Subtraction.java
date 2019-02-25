package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

public class Subtraction extends Binary{
    public Subtraction(SymbolicExpression a, SymbolicExpression b) {
        assignExpression(a,b);
    }

    @Override
    public SymbolicExpression eval(Environment environment) throws IllegalExpressionException {
        SymbolicExpression lhsEval = lhs.eval(environment);
        SymbolicExpression rhsEval = rhs.eval(environment);

        if (!lhsEval.isConstant() || !rhsEval.isConstant()) {
            if (rhsEval.isConstant()) 
                return new Subtraction(lhsEval, rhs.simplify());
            else if (lhsEval.isConstant())
                return new Subtraction(lhs.simplify(), rhs.eval(environment));
            else 
                return new Subtraction(lhsEval, rhsEval);
        }

        return this.simplify();
    }

    @Override
    public double getValue() {
        return this.lhs.getValue() - this.rhs.getValue();
    }

    @Override
    public double getPriority() {
        return 50.0;
    }

    @Override
    public String getName() {
        return "-";
    }

    public boolean equals(Subtraction other) {
        return other.lhs.equals(this.lhs) && other.rhs.equals(this.rhs);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Subtraction) {
            return this.equals((Subtraction) other);
        }
        else return false;
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }
}
