package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

public class NamedConstant extends Atom {
    protected String identifier;

    /*!
     *\brief Creates a new named constant object
     * \param s The name of the named constant
     */
    public NamedConstant(String s) {
	this.identifier = s;
    }

    @Override
    public SymbolicExpression eval(Environment environment) {
	return new Constant(this.getValue());
    }

    @Override
    public double getValue() {
	return Constants.namedConstants.get(this.identifier);
    }

    @Override
    public String toString() {
	return this.identifier;
    }

    public boolean equals(NamedConstant a) {
	return this.identifier.equals(a.identifier);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof NamedConstant) {
            return this.equals((NamedConstant) other);
        }
        else return false;
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
	return v.visit(this);
    }
}
