package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

public class Assignment extends Binary {
    public Assignment(SymbolicExpression a, SymbolicExpression b) {
        assignExpression(a, b);
    }

    @Override
    public SymbolicExpression eval(Environment environment) throws IllegalExpressionException {
        if (!(rhs.isVariable())) {
            if (Constants.namedConstants.containsKey(rhs.toString())) 
                throw new IllegalExpressionException("Tried to redefine constant");
            else 
                throw new IllegalExpressionException("Faulty expression");
        }

        SymbolicExpression left = lhs.eval(environment);

        if(!isValid(left,environment)) throw new IllegalExpressionException("Can't assign variable to itself");
        environment.put(rhs.toString(), left);

        return left;

    }

    /*!
     *\brief Evaluates if a symbolic expression is bound to the righthandside of the assignment.
     * \param expression Evaluated expression.
     * \param environment Enviroment containing recently assigned variables.
     * \return Simplified expression.p
     */
    public boolean isValid(SymbolicExpression expression,Environment environment) {
        if(expression instanceof Binary) {
            return isValid(((Binary) expression).lhs,environment) && isValid(((Binary) expression).rhs ,environment);
        }

        if(expression instanceof Constant) {
            return true;
        }

        if(expression instanceof Unary) {
            return isValid((((Unary) expression).root),environment);
        }

        if(expression instanceof NamedConstant) {
            return true;
        }

        return isValid((Variable) expression,environment);
    }

    /*!
     *\brief Evaluates if a variable is bound to the righthandside of the assignment.
     * \param expression Evaluated expression.
     * \param environment Enviroment containing recently assigned variables.
     * \return Simplified expression.p
     */
    public boolean isValid(Variable variable,Environment environment) {
        Variable rhs = (Variable) this.rhs;
        SymbolicExpression value = environment.get(((Variable) this.rhs).identifier);

        boolean instantiated = value != null;
        boolean isassociated = variable.isAssociated(rhs,environment);
        boolean isconstant;

        try {
            isconstant = (instantiated && value.eval(environment).isConstant());
        }
        catch(IllegalExpressionException e) {
            return false;
        }

        return ((isassociated || rhs.equals(lhs)) ^ !isconstant) || !isassociated;
    }

    @Override
    public SymbolicExpression simplify() {
        return this;
    }

    @Override
    public double getValue() {
        return lhs.getValue();
    }

    @Override
    public double getPriority() {
        return 40.0;
    }

    @Override
    public String getName() {
        return "=";
    }

    @Override
    public boolean isAssignment() {
        return true;
    }

    public boolean equals(Assignment other) {
        return other.lhs.equals(this.lhs) && other.rhs.equals(this.rhs);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Assignment) {
            return this.equals((Assignment) other);
        }
        else return false;
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
        return v.visit(this);
    }
}
