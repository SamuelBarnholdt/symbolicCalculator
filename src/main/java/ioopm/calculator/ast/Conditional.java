package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

public class Conditional extends SymbolicExpression {
    protected SymbolicExpression lhs;
    protected SymbolicExpression rhs;

    protected String operator;

    protected Scope iftrue;
    protected Scope elsefalse;

    /*!
     *\brief Creates a new conditional expression
     * \param lhs The left expression of the condition
     * \param rhs The right expression of the condition
     * \param operator The operator used in the condition
     * \param iftrue The result if the condition is true
     * \param elsefalse The result if the condition is false
     */
    public Conditional(SymbolicExpression lhs, SymbolicExpression rhs, String operator, Scope iftrue, Scope elsefalse) {
	this.lhs = lhs;
	this.rhs = rhs;
	this.operator = operator;
	this.iftrue = iftrue;
	this.elsefalse = elsefalse;
    }

    /*!
     *\brief Gets the left expression of the condition
     */
    public SymbolicExpression getLhs() { return lhs; }

    /*!
     *\brief Gets the right expression of the condition
     */
    public SymbolicExpression getRhs() { return rhs; }

    /*!
     *\brief Gets the operator of the condition
     */
    public String getOperator() { return operator; }

    /*!
     *\brief Gets the result if the condition is true
     */
    public Scope getIf() { return iftrue; }

    /*!
     *\brief Gets the result if the condition is false
     */
    public Scope getElse() { return elsefalse; }

    @Override
    public SymbolicExpression eval(Environment environment) throws IllegalExpressionException {
	return null;
    }

    @Override
    protected SymbolicExpression simplify() {
	return null;
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
	return v.visit(this);
    }

    @Override
    public String toString() {
	return "if " + lhs + " " + operator + " " + rhs + " " + iftrue + " else " + elsefalse;
    }

    @Override
    public double getValue() {
	return 0;
    }

    @Override
    public double getPriority() {
	return 0;
    }

    @Override
    public String getName() {
	return operator;
    }
}
