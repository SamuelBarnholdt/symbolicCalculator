package ioopm.calculator.ast;

public class Clear extends Command {
    @Override
    public void execute(Environment environment) {
	environment.clear();
    }
}
