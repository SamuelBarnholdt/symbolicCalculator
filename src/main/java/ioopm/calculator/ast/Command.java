package ioopm.calculator.ast;

import ioopm.calculator.visitor.Visitor;

import java.io.IOException;

public abstract class Command extends SymbolicExpression {
    /*!
     *\brief Executes the command 
     * \param environment An environment containing all the assigned variables
     */
    public abstract void execute(Environment environment) throws IOException;

    @Override
    public SymbolicExpression eval(Environment environment) {
	throw new RuntimeException("You shouldn't be here");
    }

    @Override
    protected SymbolicExpression simplify() {
	throw new RuntimeException("You shouldn't be here");
    }

    @Override
    public String toString() {
	throw new RuntimeException("You shouldn't be here");
    }

    @Override
    public double getValue() {
	throw new RuntimeException("You shouldn't be here");
    }

    @Override
    public double getPriority() {
	throw new RuntimeException("You shouldn't be here");
    }

    @Override
    public String getName() {
	throw new RuntimeException("You shouldn't be here");
    }

    @Override
    public boolean isCommand() {
	return true;
    }

    @Override
    public SymbolicExpression accept(Visitor v) {
	throw new RuntimeException("You shouldn't be here");
    }
}
