package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

import java.util.LinkedList;

public class FunctionCall extends SymbolicExpression {
    private String identifier;
    private LinkedList<Atom> arguments;

    /*!
     *\brief Creates a new functioncall object
     * \param identifier The name of the function called
     * \param arguments A list of atoms that will be mapped to the arguments of the function
     */
    public FunctionCall(String identifier, LinkedList<Atom> arguments) {
	this.identifier = identifier;
	this.arguments = arguments;
    }

    /*!
     *\brief Gets the list of Atoms that represents the arguments
     */
    public LinkedList<Atom> getArguments() { return arguments; }

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
	StringBuilder builder = new StringBuilder();

	builder.append(identifier);
	builder.append('(');

	for(Atom s : arguments) {
	    builder.append(s);
	    builder.append(',');
	}

	builder.deleteCharAt(builder.length()-1);
	builder.append(')');

	return builder.toString();
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
	return identifier;
    }
}
