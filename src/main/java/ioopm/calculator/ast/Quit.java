package ioopm.calculator.ast;

import java.util.Collection;
import java.io.IOException;

public class Quit extends Command {
    @Override
    public void execute(Environment environment) throws IOException {
	printSolvedVariables(environment);
    }

    private void printSolvedVariables(Environment environment) throws IOException {
	Collection<String> keys = environment.keySet();
	int solvedvariables = 0;
	int unsolved = keys.size();

	for(String key : keys) {
	    SymbolicExpression value = environment.get(key).eval(environment);

	    if (value.isConstant()) {
		solvedvariables += 1;
		System.out.println("Variable " + key + " was solved. Result: " + value);
	    }
	}

	int remainingvariables = unsolved - solvedvariables;
	System.out.println("Of " + unsolved + " variables, " + solvedvariables + " was solved. " + remainingvariables + " variables remain unsolved.");
    }
}

