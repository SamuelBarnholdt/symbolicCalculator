package ioopm.calculator.visitor;

import ioopm.calculator.ast.*;

import java.util.ArrayDeque;
import java.util.HashSet;

/*!
 *\brief Evaluates if a symbolic expression contains reassignments.
 */
public class ReassignmentVisitor extends SyntaxVisitor {
    private ArrayDeque<HashSet<String>> identifiers;

    /*!
     *\brief Creates a new ReassignmentVisitor
     * \param evaluator An EvaluationVisitor used to catch bad syntax
     */
    public ReassignmentVisitor(EvaluationVisitor evaluator) {
        super(evaluator);
    }

    /*!
     *\brief Evaluates and expression to make sure i dosn't contain reassignments
     * \param topLevel The expression to evaluate
     * \param env An environment containing all assigned variables
     * \return The expression if it passed otherwise null
     */
    public SymbolicExpression evaluate(SymbolicExpression topLevel, Environment env) {
        this.env = env;

        this.identifiers = new ArrayDeque<>();
        identifiers.push(new HashSet<String>());

        return topLevel.accept(this);
    }

    @Override
    public SymbolicExpression visit(Assignment n) {
        if(identifiers.peek().contains(n.getRhs().getName())) return null;

        identifiers.peek().add(n.getRhs().getName());

        SymbolicExpression l = n.getLhs().accept(this);
        SymbolicExpression r = n.getRhs().accept(this);

        if(l == null || r == null) return null;
        else return n;
    }

    @Override
    public SymbolicExpression visit(Scope n) {
        identifiers.push(new HashSet<String>());

        SymbolicExpression returnval = n.getRoot().accept(this);
        identifiers.pop();

        if (returnval == null) return null;
        return n;
    }
}
