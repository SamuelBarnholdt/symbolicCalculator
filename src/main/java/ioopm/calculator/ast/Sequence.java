package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

import java.util.LinkedList;

public class Sequence extends SymbolicExpression {
    private LinkedList<SymbolicExpression> expressions;

    /*!
     *\brief Creates a new Sequence
     */
    public Sequence() {
	this.expressions = new LinkedList<SymbolicExpression>();
    }

    /*!
     *\brief Gets the list of expressions that make up the sequence
     */
    public LinkedList<SymbolicExpression> getExpressions() {
	return expressions;
    }

    /*!
     *\brief Adds an expression to the sequence
     * \param n The expression to add
     */
    public void addExpression(SymbolicExpression n) {
	expressions.add(n);
    }

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
	return null;
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
	return null;
    }
}
