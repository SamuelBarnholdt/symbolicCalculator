package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;
 /*!
     *\brief Describes an expression for the symbolic calculator.
     */
public abstract class SymbolicExpression {
    /*!
     *\brief Evaluates an expression, simplifying it as much as possible.
     * \param environment Enviroment containing recently assigned variables.
     * \return Simplified expression.
     */
    public abstract SymbolicExpression eval(Environment environment) throws IllegalExpressionException;

    /*!
     *\brief Simplifies an expression as much as possible.
     * \return Simplified expression.
     * \details Called by eval, forces simplification.
     * Does not work as intended if called from an object of type Variable,
     * or an object that has a variable in its subtree.
     */
    protected abstract SymbolicExpression simplify();

    /*!
     *\brief Gets the string representation of an expression
     */
    public abstract String toString();

    /*!
     *\brief Gets the numeric value of an expression.
     */
    public abstract double getValue();

    /*!
     *\brief Gets the priority of an expression.
     */
    public abstract double getPriority();

    /*!
     *\brief Gets the name of an expression.
     */
    public abstract String getName();

    /*!
     *\brief Returns true if called by an object of class Constant.
     */
    public boolean isConstant() { return false; }

    /*!
     *\brief Returns true if called by an object of class Assignment.
     */
    protected boolean isAssignment() { return false; }

    /*!
     *\brief Returns true if called by an object of class Variable.
     */
    public boolean isVariable() { return false; }

    /*!
     *\brief Returns true if called by an object of class Command.
     */
    public boolean isCommand() { return false; }

    /*!
     *\brief Method to enable a visitor to visit an expression
     * \param v The visitor which should be called back with the expression
     */
    public abstract SymbolicExpression accept(Visitor v);
}
