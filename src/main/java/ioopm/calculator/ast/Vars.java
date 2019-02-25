package ioopm.calculator.ast;

import java.util.Collection;
import java.util.TreeSet;

public class Vars extends Command { 
    @Override
    public void execute(Environment environment) {
	printVariables(environment);
    }

    private void printVariables(Environment environment) {
	Collection<String> keys = environment.keySet();
	int amountofkeys = keys.size();

	if (amountofkeys == 0) 
	    System.out.println("No variables have been assigned.");

	TreeSet<String> sortedKeys = new TreeSet<>(keys);

	for(String key : sortedKeys) {
	    SymbolicExpression value = environment.get(key);
	    System.out.println("Variable " + key + " is bound to: " + value);
	}
    }
}
