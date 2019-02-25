package ioopm.calculator.visitor;

import ioopm.calculator.ast.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;
public class EvaluationVisitor implements Visitor {
    private ArrayDeque<Environment> stack;
    private FunctionEnvironment functions;

    /*!
     *\brief Evaluates a symbolic expression and returns the evaluated and/or simplified expression.
     * \param topLevel Symbolic expression to evaluate.
     * \param env An environment containing all assigned variables
     * \param functions An environment containing all assigned functions
     * \return The evaluated expression.
     */
    public SymbolicExpression evaluate(SymbolicExpression topLevel, Environment env,FunctionEnvironment functions) {
        this.stack = new ArrayDeque<>();
        stack.push(env);
        this.functions = functions;

        return topLevel.accept(this);
    }

    @Override
    public SymbolicExpression visit(Constant n) {
	return n;
    }

    @Override
    public SymbolicExpression visit(Variable n) throws IllegalExpressionException {
	String identifier = n.getName();

	for (Environment e : stack) {
	    if(e.containsKey(identifier)) {
		SymbolicExpression returnval = e.get(identifier).accept(this);
		if (e == null) return null;

		n.assignExpression(returnval);
		return returnval;
	    }
	}

	return n;
    }

    @Override
    public SymbolicExpression visit(Addition n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getRhs().accept(this);

	if (left == null || right == null) return null;

	if (left.isConstant() && right.isConstant()) {
	    return new Constant(left.getValue() + right.getValue());
	}
	else return new Addition(left,right);
    }

    @Override
    public SymbolicExpression visit(Division n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getRhs().accept(this);

	if (left == null || right == null) return null;

	if (left.isConstant() && right.isConstant()) {
	    return new Constant(left.getValue() / right.getValue());
	}
	else return new Division(left,right);
    }


    @Override
    public SymbolicExpression visit(Subtraction n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getRhs().accept(this);

	if (left == null || right == null) return null;

	if (left.isConstant() && right.isConstant()) {
	    return new Constant(left.getValue() - right.getValue());
	}
	else return new Subtraction(left,right);
    }

    @Override
    public SymbolicExpression visit(Multiplication n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getRhs().accept(this);

	if (left == null || right == null) return null;

	if (left.isConstant() && right.isConstant()) {
	    return new Constant(left.getValue() * right.getValue());
	}
	else return new Multiplication(left,right);
    }

    @Override
    public SymbolicExpression visit(Assignment n) {
	if(!n.getRhs().isVariable()) return null;

	SymbolicExpression left = n.getLhs().accept(this);
	if (left == null) return null;

	for (Environment e : stack) {
	    if(!n.isValid(left,e)) return null;
	}

	stack.peek().put(n.getRhs().toString(), left);
	return left;
    }

    @Override
    public SymbolicExpression visit(Negation n) {
	SymbolicExpression root = n.getRoot().accept(this);
	if (root == null) return null;

	if (root.isConstant()) {
	    return new Constant(n.getValue());
	}
	else return new Negation(root);
    }

    @Override
    public SymbolicExpression visit(Sin n) {
	SymbolicExpression root = n.getRoot().accept(this);
	if (root == null) return null;

	if (root.isConstant()) {
	    return new Constant(n.getValue());
	}
	else return new Sin(root);
    }

    @Override
    public SymbolicExpression visit(Cos n) {
	SymbolicExpression root = n.getRoot().accept(this);
	if (root == null) return null;

	if (root.isConstant()) {
	    return new Constant(n.getValue());
	}
	else return new Cos(root);
    }

    @Override
    public SymbolicExpression visit(Log n) {
	SymbolicExpression root = n.getRoot().accept(this);
	if (root == null) return null;

	if (root.isConstant()) {
	    return new Constant(n.getValue());
	}
	else return new Log(root);
    }

    @Override
    public SymbolicExpression visit(Exp n) {
	SymbolicExpression root = n.getRoot().accept(this);
	if (root == null) return null;

	if (root.isConstant()) {
	    return new Constant(n.getValue());
	}
	else return new Negation(root);
    }

    @Override
    public SymbolicExpression visit(Vars n) {
	return null;
    }


    @Override
    public SymbolicExpression visit(Quit n) {
	return null;
    }

    @Override
    public SymbolicExpression visit(Scope n) {
	stack.push(new Environment());

	SymbolicExpression root = n.getRoot().accept(this);
	if (root == null) return null;

	stack.pop();
	return root;
    }

    @Override
    public SymbolicExpression visit(Conditional n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n .getRhs().accept(this);

	if (!(left.isConstant() && right.isConstant())) return null;

	String operator = n.getOperator();
	switch(operator) {
	    case ("<"):
		if (left.getValue() < right.getValue()) return n.getIf().accept(this);
		else return n.getElse().accept(this);
	    case (">"):
		if (left.getValue() > right.getValue()) return n.getIf().accept(this);
		else return n.getElse().accept(this);
	    case ("=="):
		if (left.getValue() == right.getValue()) return n.getIf().accept(this);
		else return n.getElse().accept(this);
	    case ("<="):
		if (left.getValue() <= right.getValue()) return n.getIf().accept(this);
		else return n.getElse().accept(this);
	    case(">="):
		if (left.getValue() >= right.getValue()) return n.getIf().accept(this);
		else return n.getElse().accept(this);
	    default:
		return null;
	}
    }

    @Override
    public SymbolicExpression visit(FunctionCall n) {
	if(!functions.containsKey(n.getName())) return null;
	FunctionDeclaration funcdec = functions.get(n.getName());

	LinkedList<String> arguments = funcdec.getArguments();
	LinkedList<Atom> atoms = n.getArguments();
	if (atoms.size() != arguments.size()) return null;

	stack.push(new Environment());

	for(int i = 0; i < arguments.size(); i++) {
	    SymbolicExpression argument = atoms.get(i).accept(this);

	    if (argument == null) return null;
	    if (arguments.contains(argument.toString())) return null;

	    stack.peek().put(arguments.get(i),argument);
	}

	SymbolicExpression seqresult = funcdec.getSequence().accept(this);
	stack.pop();

	return seqresult;
    }

    @Override
    public SymbolicExpression visit(Sequence n) {
	LinkedList<SymbolicExpression> sequence = n.getExpressions();

	for(int i = 0; i < sequence.size() - 1; i++) {
	    sequence.get(i).accept(this);
	}

	return sequence.getLast().accept(this);
    }

    @Override
    public SymbolicExpression visit(NamedConstant n) {
	return new Constant(n.getValue());
    }
}
