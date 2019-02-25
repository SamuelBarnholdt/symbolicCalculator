import ioopm.calculator.ast.*;
import ioopm.calculator.ast.Constant;
import ioopm.calculator.parser.CalculatorParser;
import java.io.IOException;
//oldtests before junit
public class Tests {

    static CalculatorParser cp = new CalculatorParser();

    static Constant c0 = new Constant(0.0);
    static Constant c1 = new Constant(1.0);
    static Constant c2 = new Constant(2.0);

    public static void main(String[] args) {
	boolean successful = true;

	newline();

	try {
	    if(!testUnaryParse()) {
		successful = false;
		log("testUnaryParse failed");
	    }
	    if(!testAtomParse()) {
		successful = false;
		log("testAtomParse failed");
	    }
	    if(!testBinaryParse()) {
		successful = false;
		log("testBinaryParse failed");
	    }
	    if(!testCompundParse()) {
		successful = false;
		log("testCompundParse failed");
	    }

	    if(successful) log("All tests were successful!");
	}
	catch (IOException e) {
	    log(e.getMessage());
	}

	newline();
    }


    // Tests
    static boolean testAtomParse() throws IOException {
	boolean debug = false;

	if(debug) log("Starting atom parse test");
	Negation negC0 = new Negation(new Constant(0.0));
	Negation negC1 = new Negation(new Constant(1.0));
	Negation negC2 = new Negation(new Constant(2.0));

	if(!testEqual(negC0, cp.parse("-0.0"))) return false;
	if(!testEqual(negC1, cp.parse("-1.0"))) return false;
	if(!testEqual(negC2, cp.parse("-2.0"))) return false;

	if(!testEqual(negC0,(cp.parse("-0.0")))) return false;
	if(!testEqual(negC1,(cp.parse("-1.0")))) return false;
	if(!testEqual(negC2,(cp.parse("-2.0")))) return false;


	if(!testEqual(c0,(cp.parse("0.0")))) return false;
	if(!testEqual(c1,(cp.parse("1.0")))) return false;
	if(!testEqual(c2,(cp.parse("2.0")))) return false;


	NamedConstant pi = new NamedConstant("pi");
	NamedConstant e = new NamedConstant("e");

	if(!testEqual(pi,(cp.parse("pi")))) return false;
	if(!testEqual(e,(cp.parse("e"))) )return false;

	if(debug) log("Name constants passed");

	return true;
    }

    static boolean testUnaryParse() throws IOException {
	Sin s0 = new Sin(c0);
	Sin s1 = new Sin(c1);
	Sin s2 = new Sin(c2);

	if(!testEqual(s0,(cp.parse("sin(0.0)")))) return false;
	if(!testEqual(s1,(cp.parse("sin(1.0)")))) return false;
	if(!testEqual(s2,(cp.parse("sin(2.0)")))) return false;

	Cos co0 = new Cos(c0);
	Cos co1 = new Cos(c1);
	Cos co2 = new Cos(c2);

	if(!testEqual(co0,(cp.parse("cos(0.0)")))) return false;
	if(!testEqual(co1,(cp.parse("cos(1.0)")))) return false;
	if(!testEqual(co2,(cp.parse("cos(2.0)")))) return false;

	Log l0 = new Log(c0);
	Log l1 = new Log(c1);
	Log l2 = new Log(c2);

	if(!testEqual(l0,(cp.parse("log(0.0)")))) return false;
	if(!testEqual(l1,(cp.parse("log(1.0)")))) return false;
	if(!testEqual(l2,(cp.parse("log(2.0)")))) return false;

	Exp e0 = new Exp(c0);
	Exp e1 = new Exp(c1);
	Exp e2 = new Exp(c2);

	if(!e0.equals(cp.parse("exp(0.0)"))) return false;
	if(!e1.equals(cp.parse("exp(1.0)"))) return false;
		return e2.equals(cp.parse("exp(2.0)"));
	}

    static boolean testBinaryParse() throws IOException {
	Addition a1 = new Addition(c0, c1);
	Addition a2 = new Addition(c1, c2);
	Addition a3 = new Addition(c2, c1);

	if(!testEqual(a1,(cp.parse("0 + 1")))) return false;
	if(!testEqual(a2,(cp.parse("1 + 2")))) return false;
	if(!testEqual(a3,(cp.parse("2 + 1")))) return false;

	Subtraction s1 = new Subtraction(c0, c1);
	Subtraction s2 = new Subtraction(c1, c2);
	Subtraction s3 = new Subtraction(c2, c1);

	if(!testEqual(s1,(cp.parse("0 - 1")))) return false;
	if(!testEqual(s2,(cp.parse("1 - 2")))) return false;
	if(!testEqual(s3,(cp.parse("2 - 1")))) return false;

	Multiplication m1 = new Multiplication(c0, c1);
	Multiplication m2 = new Multiplication(c1, c2);
	Multiplication m3 = new Multiplication(c2, c1);

	if(!testEqual(m1,(cp.parse("0 * 1")))) return false;
	if(!testEqual(m2,(cp.parse("1 * 2")))) return false;
	if(!testEqual(m3,(cp.parse("2 * 1")))) return false;

	Division d1 = new Division(c0, c1);
	Division d2 = new Division(c1, c2);
	Division d3 = new Division(c2, c1);

	if(!testEqual(d1,(cp.parse("0 / 1")))) return false;
	if(!testEqual(d2,(cp.parse("1 / 2")))) return false;
	return true;
	}

    static boolean testCompundParse() throws IOException {
	SymbolicExpression exp1 = mult(add(num(1), num(2)), num(3));
	SymbolicExpression exp2 = mult(mult(num(3), num(5)), sub(num(7), num(3)));
	SymbolicExpression exp3 = div(mult(num(5), add(num(1), num(2))), num(3));
	SymbolicExpression exp4 = div(num(3), mult(neg(5), num(3)));
	SymbolicExpression exp5 = mult(neg(5), neg(5));

	if(!testEqual(exp1,(cp.parse("(1+2)*3"))) )return false;
	if(!testEqual(exp2,(cp.parse("3*5*(7-3)")))) return false;
	if(!testEqual(exp3,(cp.parse("(5*(1+2))/3")))) return false;
	if(!testEqual(exp4,(cp.parse("3/(-5)*3")))) return false;
		return exp5.equals(cp.parse("(-5)*(-5)"));
	}

    // Helper Functions
    static void testPrinting(String expected, SymbolicExpression e) {
	if (expected.equals("" + e)) {
	    System.out.println("Passed: " + e);
	} else {
	    System.out.println("Error: expected '" + expected + "' but got '" + e + "'");
	}
    }

    static void testEvaluating(SymbolicExpression expected, SymbolicExpression e) throws Exception {
	SymbolicExpression r = e.eval(cp.getEnvironment());
	if (r.equals(expected)) {
	    System.out.println("Passed: " + e);
	} else {
	    System.out.println("Error: expected '" + expected + "' but got '" + e + "'");
	}
    }

    static boolean testEqual(SymbolicExpression expected, SymbolicExpression e) {
	if (e.equals(expected)) {
	    System.out.println("Passed: " + e);
	    return true;
	} else {
	    System.out.println("Error: expected '" + expected + "' but got '" + e + "'");
	    return true;
	}
    }

    static Constant num(double d) { return new Constant(d); }
    static Negation neg(double d) { return new Negation(num(d)); }
    static Addition add(SymbolicExpression exp1, SymbolicExpression exp2) { return new Addition(exp1, exp2); }
    static Subtraction sub(SymbolicExpression exp1, SymbolicExpression exp2) { return new Subtraction(exp1, exp2); }
    static Multiplication mult(SymbolicExpression exp1, SymbolicExpression exp2) { return new Multiplication(exp1, exp2); }
    static Division div(SymbolicExpression exp1, SymbolicExpression exp2) { return new Division(exp1, exp2); }

    static void newline() {
	log("");
    }

    static void log(Object obj) {
	System.out.println(obj);
    }
}
