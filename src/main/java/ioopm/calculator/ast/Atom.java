package ioopm.calculator.ast;

public abstract class Atom extends SymbolicExpression {
    @Override
    public SymbolicExpression simplify() { 
	return this; 
    }

    @Override
    public double getPriority() {
	return 100.0;
    }

    @Override
    public String getName() {
	return this.toString();
    }
}
