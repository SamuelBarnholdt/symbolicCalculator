package ioopm.calculator.visitor;

import ioopm.calculator.ast.*;

import java.util.LinkedList;

public class FullEvaluationVisitor implements Visitor {
    private Environment env;
    private EvaluationVisitor evaluator;
    private LinkedList<SymbolicExpression> backtrace;

    /*!
     *\brief Evaluates a symbolic expression.
     * \param topLevel Symbolic expression to evaluate.
     * \param env assigns an environment to the visitor where variables are stored.
     * \return Returns the evaluated and/or simplified expression.
     */
    public SymbolicExpression evaluate(SymbolicExpression topLevel, Environment env) {
	this.env = env;
	topLevel.accept(this);

	return null;
    }

    @Override
    public SymbolicExpression visit(NamedConstant n) {
	return null;
    }

    @Override
    public SymbolicExpression visit(Addition n) {
	backtrace.add(n);

	return null;
    }

    /*!
     *\brief Evaluates a symbolic expression.
     * \param n Symbolic expression to evaluate.
     * \return Returns the evaluated and/or simplified expression.
     */
    @Override
    public SymbolicExpression visit(Assignment n) {
	return null;
    }

    /*!
     *\brief Evaluates a symbolic expression.
     * \param n Symbolic expression to evaluate.
     * \return Returns the evaluated and/or simplified expression.
     */
    @Override
    public SymbolicExpression visit(Constant n) {
	return null;
    }

    /*!
     *\brief Evaluates a symbolic expression.
     * \param n Symbolic expression to evaluate.
     * \return Returns the evaluated and/or simplified expression.
     */
    @Override
    public SymbolicExpression visit(Cos n) {
	return null;
    }

    /*!
     *\brief Evaluates a symbolic expression.
     * \param n Symbolic expression to evaluate.
     * \return Returns the evaluated and/or simplified expression.
     */
    @Override
    public SymbolicExpression visit(Division n) {
	return null;
    }

    /*!
     *\brief Evaluates a symbolic expression.
     * \param n Symbolic expression to evaluate.
     * \return Returns the evaluated and/or simplified expression.
     */
    @Override
    public SymbolicExpression visit(Exp n) {
	return null;
    }

    /*!
     *\brief Evaluates a symbolic expression.
     * \param n Symbolic expression to evaluate.
     * \return Returns the evaluated and/or simplified expression.
     */
    @Override
    public SymbolicExpression visit(Log n) {
	return null;
    }

    @Override
    public SymbolicExpression visit(Multiplication n) {
	return null;
    }

    /*!
     *\brief Evaluates a symbolic expression.
     * \param n Symbolic expression to evaluate.
     * \return Returns the evaluated and/or simplified expression.
     */
    @Override
    public SymbolicExpression visit(Sequence n) {
	return null;
    }

    /*!
     *\brief Evaluates a symbolic expression.
     * \param n Symbolic expression to evaluate.
     * \return Returns the evaluated and/or simplified expression.
     */
    @Override
    public SymbolicExpression visit(Negation n) {
	return null;
    }

    /*!
     *\brief Evaluates a symbolic expression.
     * \param n Symbolic expression to evaluate.
     * \return Returns the evaluated and/or simplified expression.
     */
    @Override
    public SymbolicExpression visit(Quit n) {
	return null;
    }

    /*!
     *\brief Evaluates a symbolic expression.
     * \param n Symbolic expression to evaluate.
     * \return Returns the evaluated and/or simplified expression.
     */
    @Override
    public SymbolicExpression visit(Sin n) {
	return null;
    }

    /*!
     *\brief Evaluates a symbolic expression.
     * \param n Symbolic expression to evaluate.
     * \return Returns the evaluated and/or simplified expression.
     */
    @Override
    public SymbolicExpression visit(Subtraction n) {
	return null;
    }

    /*!
     *\brief Evaluates a symbolic expression.
     * \param n Symbolic expression to evaluate.
     * \return Returns the evaluated and/or simplified expression.
     */
    @Override
    public SymbolicExpression visit(Variable n) throws IllegalExpressionException {
	return null;
    }

    /*!
     *\brief Evaluates a symbolic expression.
     * \param n Symbolic expression to evaluate.
     * \return Returns the evaluated and/or simplified expression.
     */
    @Override
    public SymbolicExpression visit(FunctionCall n) {
	return null;
    }

    /*!
     *\brief Evaluates a symbolic expression.
     * \param n Symbolic expression to evaluate.
     * \return Returns the evaluated and/or simplified expression.
     */
    @Override
    public SymbolicExpression visit(Vars n) {
	return null;
    }

    /*!
     *\brief Evaluates a symbolic expression.
     * \param n Symbolic expression to evaluate.
     * \return Returns the evaluated and/or simplified expression.
     */
    @Override
    public SymbolicExpression visit(Scope n) {
	return null;
    }

    /*!
     *\brief Evaluates a symbolic expression.
     * \param n Symbolic expression to evaluate.
     * \return Returns the evaluated and/or simplified expression.
     */
    @Override
    public SymbolicExpression visit(Conditional n) {
	return null;
    }
}
