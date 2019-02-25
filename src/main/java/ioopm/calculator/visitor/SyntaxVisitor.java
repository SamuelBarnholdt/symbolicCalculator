package ioopm.calculator.visitor;

import ioopm.calculator.ast.*;

import java.util.LinkedList;

public class SyntaxVisitor implements Visitor {
    protected Environment env;
    private FunctionEnvironment functions;
    protected EvaluationVisitor evaluator;

    public SyntaxVisitor(EvaluationVisitor evaluator) {
	this.evaluator = evaluator;
    }

    @Override
    public SymbolicExpression visit(Constant n) {
	return n;
    }

    @Override
    public SymbolicExpression visit(Variable n) {
	return n;
    }

    @Override
    public SymbolicExpression visit(Addition n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getRhs().accept(this);

	if (left == null || right == null) {
	    return null;
	}
	else return n;
    }

    @Override
    public SymbolicExpression visit(Division n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getRhs().accept(this);

	if (left == null || right == null) {
	    return null;
	}
	else return n;
    }


    @Override
    public SymbolicExpression visit(Subtraction n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getRhs().accept(this);

	if (left == null || right == null) {
	    return null;
	}
	else return n;
    }

    @Override
    public SymbolicExpression visit(Multiplication n) {
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getRhs().accept(this);

	if (left == null || right == null) {
	    return null;
	}
	else return n;
    }

    @Override
    public SymbolicExpression visit(Assignment n) {
	SymbolicExpression left = n.getLhs().accept(this);

	if (!(n.getRhs().isVariable())) {
	    return null;
	}
	if (left == null) {
	    return null;
	}
	if(!n.isValid(left,env)) return null;
	else return n;
    }

    @Override
    public SymbolicExpression visit(Negation n) {
	SymbolicExpression root = n.getRoot().accept(this);

	if (root == null) {
	    return null;
	}
	else return n;
    }

    @Override
    public SymbolicExpression visit(Sin n) {
	SymbolicExpression root = n.getRoot().accept(this);

	if (root == null) {
	    return null;
	}
	else return n;
    }

    @Override
    public SymbolicExpression visit(Cos n) {
	SymbolicExpression root = n.getRoot().accept(this);

	if (root == null) {
	    return null;
	}
	else return n;
    }

    @Override
    public SymbolicExpression visit(Log n) {
	SymbolicExpression root = n.getRoot().accept(this);
	SymbolicExpression copy = new Log(n.getRoot());
	SymbolicExpression evaluatedcopy = evaluator.evaluate(copy,env,functions);

	if (root == null || (evaluatedcopy != null && evaluatedcopy.isConstant() && copy.getValue() < 0)) {
	    return null;
	}
	else return n;
    }

    @Override
    public SymbolicExpression visit(Exp n) {
	SymbolicExpression root = n.getRoot().accept(this);

	if (root == null) {
	    return null;
	}
	else return n;
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
	return n;
    }

    @Override
    public SymbolicExpression visit(Conditional n) {
	SymbolicExpression ifs = n.getElse().accept(this);
	SymbolicExpression buts = n.getIf().accept(this);
	SymbolicExpression left = n.getLhs().accept(this);
	SymbolicExpression right = n.getLhs().accept(this);
	if (ifs == null || buts == null || left == null || right == null) return null;
	return n;
    }

    @Override
    public SymbolicExpression visit(Sequence n) {
	LinkedList<SymbolicExpression> expressions = n.getExpressions();
	for(SymbolicExpression expression : expressions) {
	    if (expression.accept(this) == null) return null;
	}
	return n;
    }

    @Override
    public SymbolicExpression visit(FunctionCall n) {
	return n;
    }

    @Override
    public SymbolicExpression visit(NamedConstant n) {
	return n;
    }
}
