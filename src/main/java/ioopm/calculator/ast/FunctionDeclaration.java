package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

import java.util.LinkedList;

public class FunctionDeclaration extends SymbolicExpression {
    private String identifier;
    private LinkedList<String> arguments;
    private Sequence sequence;

    /*!
     *\brief Creates a new function declaration
     * \param identifier The name of the function
     * \param arguments A list contaning the names of the arguments
     */
    public FunctionDeclaration(String identifier, LinkedList<String> arguments) {
	this.identifier = identifier;
	this.arguments = arguments;
    }

    /*!
     *\brief Gets the sequence of expressions that make up the function body
     */
    public Sequence getSequence() { return sequence; }

    /*!
     *\brief Gets the number of arguments
     */
    public int getArgNum() { return arguments.size(); }

    /*!
     *\brief Gets a list contaning the names of all arguments
     */
    public LinkedList<String> getArguments() { return arguments; }

    /*!
     *\brief Adds an expression to the function body
     * \param n The expression to add
     */
    public void addExpression(SymbolicExpression n) { sequence.addExpression(n); }

    /*!
     *\brief Sets function body sequence
     * \param n The new function body
     */
    public void addSequence(Sequence n) { this.sequence = n; }

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
	return null;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();

	builder.append(identifier);
	builder.append('(');

	for(String s : arguments) {
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
