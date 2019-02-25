package ioopm.calculator.ast;

public abstract class Binary extends SymbolicExpression {
    protected SymbolicExpression lhs;
    protected SymbolicExpression rhs;

    /*!
     *\brief Assigns the left and right sub expressions of the binary expression
     * \param a The left expression to assign
     * \param a The right expression to assign
     */
    protected void assignExpression(SymbolicExpression a, SymbolicExpression b) {
        this.lhs = a;
        this.rhs = b;
    }

    /*!
     *\brief Gets the left sub expression of a binary expression
     */
    public SymbolicExpression getLhs() { return lhs; }

    /*!
     *\brief Gets the right sub expression of a binary expression
     */
    public SymbolicExpression getRhs() { return rhs; }

    @Override
    public SymbolicExpression simplify() {
        return new Constant(this.getValue());
    }

    @Override
    public String toString() {
        String left = "";
        String right = "";

        if (lhs.getPriority() < this.getPriority()) 
            left = left + "(" + lhs.toString() + ")";
        else 
            left = lhs.toString();

        if (rhs.getPriority() < this.getPriority()) 
            right = right + "(" + rhs.toString() + ")";
        else 
            right = rhs.toString();

        return left + " " + this.getName() + " " + right;
    }
}
