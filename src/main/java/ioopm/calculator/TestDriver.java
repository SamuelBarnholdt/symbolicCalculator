package ioopm.calculator;

import ioopm.calculator.ast.*;
import ioopm.calculator.parser.CalculatorParser;
import ioopm.calculator.parser.SyntaxErrorException;
import ioopm.calculator.visitor.EvaluationVisitor;
import ioopm.calculator.visitor.ReassignmentVisitor;

import java.util.HashSet;
import java.util.Scanner;
import java.io.IOException;

public class TestDriver {
    /*!
     *\brief Main method containing the parsing loop
     * \param args IGNORED
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CalculatorParser cp = new CalculatorParser();

        ReassignmentVisitor re = new ReassignmentVisitor(new EvaluationVisitor());
        EvaluationVisitor eval = new EvaluationVisitor();

        log("Welcome to this calculator!");

        while (true) {
            try {
                String s = scanner.nextLine();

                if(s.startsWith("function")) {
                    readFunctionDeclaration(s, scanner, cp);
                    continue;
                }

                SymbolicExpression result = cp.parse(s);

                if (result != null) {
                    if(result.isCommand()) {
                        Command command = (Command) result;
                        command.execute(cp.getEnvironment());
                    }
                    else {
                        SymbolicExpression reassigned = re.evaluate(result,cp.getEnvironment());
                        if (reassigned == null) throw new SyntaxErrorException("Tried to reassign variable.");

                        SymbolicExpression evaluated = eval.evaluate(result,cp.getEnvironment(),cp.getFunctions());
                        if(evaluated == null) throw new SyntaxErrorException("Expression can't be evaluated.");

                        else log(result + " evaluated to: " + evaluated);
                    }
                }

                if (cp.shouldQuit()) break;
            } 
            catch (IOException e) {
                log(e.getMessage());
            }
        }
    }

    /*!
     *\brief Reads an function declaration
     * \param s The string containing the function head
     * \param scanner The scanner for reading lines from the input
     * \param cp The parser object used for creating expressions
     */
    static void readFunctionDeclaration(String s, Scanner scanner, CalculatorParser cp) throws IOException {
        FunctionDeclaration f = null;

        try {
            f = cp.parseFunction(s);
            Sequence sequence = new Sequence();
            String str = scanner.nextLine();

            while(!str.equals("end")) {
                SymbolicExpression exp = cp.parse(str);
                sequence.addExpression(exp);

                str = scanner.nextLine();
            }

            if (sequence.getExpressions().isEmpty())
                throw new SyntaxErrorException("Empty functiondeclaration");

            f.addSequence(sequence);
            cp.getFunctions().put(f.getName(), f);

            log("Created function: " + f);
        }
        catch (SyntaxErrorException e) {
            if (f != null) cp.getFunctions().remove(f.getName());
            throw e;
        }
    }

    /*!
     *\brief Helper function for printing
     * \param obj The object to print using toString()
     */
    static void log(Object obj) {
        System.out.println(obj);
    }
}
