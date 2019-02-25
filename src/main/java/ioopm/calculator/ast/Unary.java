package ioopm.calculator.ast;

public abstract class Unary extends SymbolicExpression {
    protected SymbolicExpression root;

    /*!
     *\brief Assigns the root expression of the unary expression
     * \param a The root expression to assign
     */
    public void assignExpression(SymbolicExpression a) {
        root = a;
    }

    /*!
     *\brief Gets the root expression of the unary expression
     */
    public SymbolicExpression getRoot() {
        return this.root;
    }

    @Override
    protected SymbolicExpression simplify() {
        return new Constant(this.getValue());
    }

    @Override
    public String toString() {
        return getName() + "(" + this.root.toString() + ")";
    }

    @Override
    public double getPriority() {
        return 100.0;
    }
}
