package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

import java.util.HashSet;

public class Variable extends Atom implements Comparable<Variable> {
    protected String identifier;
    protected SymbolicExpression returnvalue;

    /*!
     *\brief Creates a new variable object
     * \param name Identifier of the variable
     */
    public Variable(String name) {
	this.identifier = name;
    }

    /*!
     *\brief Sets the expression that the variable maps to
     * \param returnvalue The new expression
     */
    public void assignExpression(SymbolicExpression returnvalue) {
	this.returnvalue = returnvalue;
    }

    @Override
    public SymbolicExpression eval(Environment environment) throws IllegalExpressionException {
	if (environment.containsKey(this.identifier)) {
	    this.returnvalue = environment.get(this.identifier).eval(environment);
	    return returnvalue;
	}
	else return this;
    }

    @Override
    public double getValue() {
	return returnvalue.getValue();
    }

    @Override
    public boolean isVariable() {
	return true;
    }

    @Override
    public String toString() {
	return this.identifier;
    }

    public boolean equals(Variable other) {

	return this.identifier.equals(other.identifier);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Variable) {
            return this.equals((Variable) other);
        }
        else return false;
    }

    @Override
    public int compareTo(Variable other) {
	return this.identifier.compareTo(other.identifier);
    }

    /*!
     *\brief Evaluates if this variable is bound to another expression.
     * \param expression Expression to check.
     * \param environment Enviroment containing recently assigned variables.
     * \param identifiers HashSet containing recently found identifiers.
     * \return True if it's bound false if it's not.
     */
    public boolean containsOther(SymbolicExpression expression, Environment environment, HashSet<String> identifiers) {
	if(expression instanceof Binary) {
	    return containsOther(((Binary) expression).lhs,environment, identifiers) && containsOther(((Binary) expression).rhs,environment, identifiers);
	}

	if(expression instanceof Constant) {
	    return false;
	}

	if(expression instanceof Unary) {
	    return containsOther((((Unary) expression).root),environment, identifiers);
	}

	if(expression instanceof NamedConstant) {
	    return false;
	}

	return (containsOther((Variable) expression,environment,identifiers));
    }

    /*!
     *\brief Evaluates if this variable is bound to another variable.
     * \param other Variable to check.
     * \param environment Environment containing recently assigned variables.
     * \param identifiers HashSet containing recently found identifiers.
     * \return True if it's bound false if it's not.
     */
    private boolean containsOther(Variable other, Environment environment,HashSet<String> identifiers) {
	identifiers.add(other.identifier);
	boolean found = identifiers.contains(this.identifier);

	if (found) return true;

	if(environment.containsKey(other.identifier)) {
	    return containsOther(environment.get(other.identifier),environment, identifiers);
	}
	else return false;
    }

    /*!
     *\brief Evaluates if this variable is bound to another variable.
     * \param other Variable to check.
     * \param environment Environment containing recently assigned variables.
     * \return True if it's bound false if it's not.
     */
    public boolean isAssociated(Variable other, Environment environment) {
	return other.containsOther(this,environment,new HashSet<String>());
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
	try {
	    return v.visit(this);
	}
	catch(IllegalExpressionException e) {
	    return this;
	}

    }
}
