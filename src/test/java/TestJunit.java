import ioopm.calculator.ast.*;
import ioopm.calculator.parser.CalculatorParser;
import ioopm.calculator.visitor.EvaluationVisitor;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class TestJunit {
    static CalculatorParser cp = new CalculatorParser();
    static EvaluationVisitor evaluator = new EvaluationVisitor();

    static Constant c0 = new Constant(0.0);
    static Constant c1 = new Constant(1.0);
    static Constant c2 = new Constant(2.0);

    private static HashMap<String,SymbolicExpression> expressions() {
        try{
            HashMap<String,SymbolicExpression> expressions = new HashMap<String, SymbolicExpression>();
            SymbolicExpression single = cp.parse("x");
            SymbolicExpression expression = cp.parse("x + (5 * sin(5)) - (3/y) + sin(3+4)");
            SymbolicExpression singleconstant = cp.parse("10");
            SymbolicExpression expressionconstant = cp.parse("10 + sin(5*3)/exp(log(5))*3");
            String[] constantnames = {"single","expression","singleconstant","expressionconstant"};
            SymbolicExpression[] constantexpressions = {single,expression,singleconstant,expressionconstant};
            for(int i = 0; i < constantnames.length; i++) {
                expressions.put(constantnames[i],constantexpressions[i]);
            }

            SymbolicExpression mult = cp.parse("5*5");
            SymbolicExpression add = cp.parse("5+5");
            SymbolicExpression sub = cp.parse("5-5");
            SymbolicExpression div = cp.parse("5/5");
            SymbolicExpression sin = cp.parse("sin(5)");
            SymbolicExpression cos = cp.parse("cos(5)");
            SymbolicExpression log = cp.parse("log(5)");
            SymbolicExpression exp = cp.parse("exp(5)");
            SymbolicExpression negation = cp.parse("-5");
            String[] getnamenames = {"mult","add","sub","div","sin","cos","log","exp","negation"};
            SymbolicExpression[] getnameexpressions = {mult,add,sub,div,sin,cos,log,exp,negation};

            for(int i = 0; i < getnamenames.length; i++) {
                expressions.put(getnamenames[i],getnameexpressions[i]);
            }


            SymbolicExpression quit = cp.parse("quit");
            SymbolicExpression vars = cp.parse("vars");
            SymbolicExpression clear = cp.parse("clear");

            String[] commandnames = {"quit","vars","clear"};
            SymbolicExpression[] commandexpressions = {quit,vars,clear};

            for(int i = 0; i < commandnames.length; i++) {
                expressions.put(commandnames[i],commandexpressions[i]);
            }


            SymbolicExpression cdeq = cp.parse("if 5 == 4 {1} else {0}");
            SymbolicExpression cdleq = cp.parse("if 5 >=4 {1} else {0}");
            SymbolicExpression cdgeq = cp.parse("if 5 <= 4 {1} else {0}");
            SymbolicExpression cdg = cp.parse("if 5 > 4 {1} else {0}");
            SymbolicExpression cdl = cp.parse("if 5 < 4 {1} else {0}");


            String[] conditionalnames = {"cdeq","cdleq","cdgeq","cdg","cdl"};
            SymbolicExpression[] conditionals = {cdeq,cdleq,cdgeq,cdg,cdl};

            for(int i = 0; i < conditionalnames.length; i++) {
                expressions.put(conditionalnames[i],conditionals[i]);
            }

            SymbolicExpression rea = cp.parse("{1 = x} + 1 = x");
            SymbolicExpression reg = cp.parse("{5*10} + {3 * 4}");
            SymbolicExpression parenthesis = cp.parse("5 * {(((5)))}");
            SymbolicExpression conditional = cp.parse("{if 5 > 4 {1} else {2}} + {if 5 > 4 {1} else {2}} = x");

            String[] scopenames = {"rea","reg","parenthesis","conditional"};
            SymbolicExpression[] scopes = {rea,reg,parenthesis,conditional};

            for(int i = 0; i < scopenames.length; i++) {
                expressions.put(scopenames[i],scopes[i]);
            }

            return expressions;

        }catch(Exception e) {
            return null;
        }
    }

    private static FunctionEnvironment functions() {
        try {
            FunctionEnvironment functions = new FunctionEnvironment();
            FunctionDeclaration factorial = cp.parseFunction("function factorial(n)");
            Sequence facsequence = new Sequence();
            facsequence.addExpression(cp.parse("n-1=m"));
            facsequence.addExpression(cp.parse("if n > 1 {factorial(m)*n} else {1}"));
            factorial.addSequence(facsequence);
            functions.put(factorial.getName(), factorial);

            FunctionDeclaration gcd = cp.parseFunction("function gcd(a,b)");
            Sequence gcdsequence = new Sequence();
            gcdsequence.addExpression(cp.parse("a-b=ab"));
            gcdsequence.addExpression(cp.parse("b-a=ba"));
            gcdsequence.addExpression(cp.parse("if a == b {a} else {if a < b {gcd(a,ba)} else {gcd(ab,b)}}"));
            gcd.addSequence(gcdsequence);
            functions.put(gcd.getName(),gcd);

            return functions;
        }
        catch(Exception e) {
            return null;
        }
    }

    private static HashMap<String,SymbolicExpression> expressions = expressions();

    //Method Tests
    @Test
    public void isConstant() throws IOException {
        SymbolicExpression single = expressions.get("single");
        SymbolicExpression expression = expressions.get("expression").eval(cp.getEnvironment());
        SymbolicExpression singleconstant = expressions.get("singleconstant").eval(cp.getEnvironment());
        SymbolicExpression expressionconstant = expressions.get("expressionconstant").eval(cp.getEnvironment());

        assertFalse(single.isConstant());
        assertFalse(expression.isConstant());
        assertTrue(singleconstant.isConstant());
        assertTrue(expressionconstant.isConstant());
    }

    @Test
    public void getName() {
        SymbolicExpression mult = expressions.get("mult");
        SymbolicExpression add = expressions.get("add");
        SymbolicExpression sub = expressions.get("sub");
        SymbolicExpression div = expressions.get("div");
        SymbolicExpression sin = expressions.get("sin");
        SymbolicExpression cos = expressions.get("cos");
        SymbolicExpression log = expressions.get("log");
        SymbolicExpression exp = expressions.get("exp");
        SymbolicExpression negation = expressions.get("negation");

        assertEquals("*",mult.getName());
        assertEquals("+",add.getName());
        assertEquals("-",sub.getName());
        assertEquals("/",div.getName());
        assertEquals("sin",sin.getName());
        assertEquals("cos",cos.getName());
        assertEquals("log",log.getName());
        assertEquals("exp",exp.getName());
        assertEquals("-",negation.getName());
    }

    @Test
    public void isCommand() throws IllegalExpressionException {
        SymbolicExpression expression = expressions.get("expression").eval(cp.getEnvironment());
        SymbolicExpression mult = expressions.get("mult");
        SymbolicExpression add = expressions.get("add");
        SymbolicExpression sub = expressions.get("sub");
        SymbolicExpression div = expressions.get("div");
        SymbolicExpression sin = expressions.get("sin");
        SymbolicExpression cos = expressions.get("cos");
        SymbolicExpression log = expressions.get("log");
        SymbolicExpression exp = expressions.get("exp");
        SymbolicExpression negation = expressions.get("negation");

        assertFalse(expression.isCommand());
        assertFalse(mult.isCommand());
        assertFalse(add.isCommand());
        assertFalse(sub.isCommand());
        assertFalse(div.isCommand());
        assertFalse(sin.isCommand());
        assertFalse(cos.isCommand());
        assertFalse(log.isCommand());
        assertFalse(exp.isCommand());
        assertFalse(negation.isCommand());

        SymbolicExpression quit = expressions.get("quit");
        SymbolicExpression vars = expressions.get("vars");
        SymbolicExpression clear = expressions.get("clear");

        assertTrue(quit.isCommand());
        assertTrue(clear.isCommand());
        assertTrue(vars.isCommand());
    }

    @Test
    public void getValue() throws IllegalExpressionException {
        SymbolicExpression mult = expressions.get("mult");
        SymbolicExpression add = expressions.get("add");
        SymbolicExpression sub = expressions.get("sub");
        SymbolicExpression div = expressions.get("div");
        SymbolicExpression sin = expressions.get("sin");
        SymbolicExpression cos = expressions.get("cos");
        SymbolicExpression log = expressions.get("log");
        SymbolicExpression exp = expressions.get("exp");
        SymbolicExpression negation = expressions.get("negation");

        assertEquals(0,Double.compare(mult.getValue(),25));
        assertEquals(0,Double.compare(add.getValue(),10));
        assertEquals(0,Double.compare(sub.getValue(),0));
        assertEquals(0,Double.compare(div.getValue(),1));
        assertEquals(0,Double.compare(sin.getValue(),Math.sin(5)));
        assertEquals(0,Double.compare(cos.getValue(),Math.cos(5)));
        assertEquals(0,Double.compare(log.getValue(),Math.log(5)));
        assertEquals(0,Double.compare(exp.getValue(),Math.exp(5)));
        assertEquals(0,Double.compare(negation.getValue(),-5));
    }

    @Test
    public void priority() {
        SymbolicExpression mult = expressions.get("mult");
        SymbolicExpression add = expressions.get("add");
        SymbolicExpression sub = expressions.get("sub");
        SymbolicExpression div = expressions.get("div");
        SymbolicExpression sin = expressions.get("sin");
        SymbolicExpression negation = expressions.get("negation");
        SymbolicExpression scope = expressions().get("rea");
        SymbolicExpression conditional = expressions().get("cdleq");

        assertEquals(0,Double.compare(mult.getPriority(),div.getPriority()));
        assertEquals(0,Double.compare(sin.getPriority(),mult.getPriority()));
        assertEquals(0,Double.compare(add.getPriority(),sub.getPriority()));
        assertTrue(mult.getPriority() > add.getPriority());
        assertFalse(negation.getPriority() < add.getPriority());
        assertTrue(conditional.getPriority()< add.getPriority());


    }

    //Function tests
    @Test
    public void factorial() throws IOException {
        functions();
        EvaluationVisitor eval = new EvaluationVisitor();
        SymbolicExpression fac1 = eval.evaluate(cp.parse("factorial(1)"),cp.getEnvironment(),cp.getFunctions());
        SymbolicExpression fac5 = eval.evaluate(cp.parse("factorial(5)"),cp.getEnvironment(),cp.getFunctions());
        SymbolicExpression fac10 = eval.evaluate(cp.parse("factorial(10)"),cp.getEnvironment(),cp.getFunctions());
        assertEquals(0,Double.compare(fac1.getValue(),1.0));
        assertEquals(0,Double.compare(fac5.getValue(),120.0));
        assertEquals(0,Double.compare(fac10.getValue(),3628800.0));
    }

    @Test
    public void gcd() throws IOException {
        functions();
        FunctionEnvironment functions = functions();
        EvaluationVisitor eval = new EvaluationVisitor();
        SymbolicExpression gcd1 = eval.evaluate(cp.parse("gcd(188,36)"),cp.getEnvironment(),cp.getFunctions());
        SymbolicExpression gcdprimes = eval.evaluate(cp.parse("gcd(3121,3613)"),cp.getEnvironment(),cp.getFunctions());
        assertEquals(0,Double.compare(gcd1.getValue(),4.0));
        assertEquals(0,Double.compare(gcdprimes.getValue(),1.0));

    }

    @Test
    public void conditionals() throws IOException {
        SymbolicExpression cdeq = expressions.get("cdeq");
        SymbolicExpression cdgeq = expressions.get("cdgeq");
        SymbolicExpression cdleq = expressions.get("cdleq");
        SymbolicExpression cdl = expressions.get("cdl");
        SymbolicExpression cdg = expressions.get("cdg");

        assertEquals(0,Double.compare(0,evaluator.evaluate(cdeq,cp.getEnvironment(),null).getValue()));
        assertEquals(0,Double.compare(0,evaluator.evaluate(cdgeq,cp.getEnvironment(),null).getValue()));
        assertEquals(0,Double.compare(1,evaluator.evaluate(cdleq,cp.getEnvironment(),null).getValue()));
        assertEquals(0,Double.compare(0,evaluator.evaluate(cdl,cp.getEnvironment(),null).getValue()));
        assertEquals(0,Double.compare(1,evaluator.evaluate(cdg,cp.getEnvironment(),null).getValue()));
    }

    @Test
    public void scopes() throws IOException {
        SymbolicExpression rea = expressions().get("rea");
        SymbolicExpression reg = expressions().get("reg");
        SymbolicExpression parenthesis = expressions().get("parenthesis");
        SymbolicExpression conditional = expressions().get("conditional");

        assertEquals(0,Double.compare(2,evaluator.evaluate(rea,cp.getEnvironment(),null).getValue()));
        assertEquals(0,Double.compare(62,evaluator.evaluate(reg,cp.getEnvironment(),null).getValue()));
        assertEquals(0,Double.compare(25,evaluator.evaluate(parenthesis,cp.getEnvironment(),null).getValue()));
        assertEquals(0,Double.compare(2,evaluator.evaluate(conditional,cp.getEnvironment(),null).getValue()));

    }

    //Parsing Tests
    @Test
    public void constantParse() throws IOException {
        assertEquals(c0,(cp.parse("0.0")));
        assertEquals(c1,(cp.parse("1.0")));
        assertEquals(c2,(cp.parse("2.0")));
    }   

    @Test
    public void negativeConstantParse() throws IOException {
        Negation negC0 = new Negation(new Constant(0.0));
        Negation negC1 = new Negation(new Constant(1.0));
        Negation negC2 = new Negation(new Constant(2.0));

        assertEquals(negC0, cp.parse("-0.0"));
        assertEquals(negC1, cp.parse("-1.0"));
        assertEquals(negC2, cp.parse("-2.0"));
    }   

    @Test
    public void namedConstantParse() throws IOException {
        NamedConstant pi = new NamedConstant("pi");
        NamedConstant e = new NamedConstant("e");

        assertEquals(pi,(cp.parse("pi")));
        assertEquals(e,(cp.parse("e")));
    }   

    @Test
    public void sinParse() throws IOException {
        Sin s0 = new Sin(c0);
        Sin s1 = new Sin(c1);
        Sin s2 = new Sin(c2);

        assertEquals(s0,(cp.parse("sin(0.0)")));
        assertEquals(s1,(cp.parse("sin(1.0)")));
        assertEquals(s2,(cp.parse("sin(2.0)")));
    }

    @Test
    public void cosParse() throws IOException {
        Cos co0 = new Cos(c0);
        Cos co1 = new Cos(c1);
        Cos co2 = new Cos(c2);

        assertEquals(co0,(cp.parse("cos(0.0)")));
        assertEquals(co1,(cp.parse("cos(1.0)")));
        assertEquals(co2,(cp.parse("cos(2.0)")));
    }

    @Test
    public void logParse() throws IOException {

        Log l0 = new Log(c0);
        Log l1 = new Log(c1);
        Log l2 = new Log(c2);

        assertEquals(l0,(cp.parse("log(0.0)")));
        assertEquals(l1,(cp.parse("log(1.0)")));
        assertEquals(l2,(cp.parse("log(2.0)")));
    }

    @Test
    public void expParse() throws IOException {
        Exp e0 = new Exp(c0);
        Exp e1 = new Exp(c1);
        Exp e2 = new Exp(c2);

        assertEquals(e0,cp.parse("exp(0.0)"));
        assertEquals(e1,cp.parse("exp(1.0)"));
        assertEquals(e2,cp.parse("exp(2.0)"));
    }

    @Test
    public void additionParse() throws IOException {
        Addition a1 = new Addition(c0, c1);
        Addition a2 = new Addition(c1, c2);
        Addition a3 = new Addition(c2, c1);

        assertEquals(a1,(cp.parse("0 + 1")));
        assertEquals(a2,(cp.parse("1 + 2")));
        assertEquals(a3,(cp.parse("2 + 1")));
    }

    @Test
    public void subtractionParse() throws IOException {
        Subtraction s1 = new Subtraction(c0, c1);
        Subtraction s2 = new Subtraction(c1, c2);
        Subtraction s3 = new Subtraction(c2, c1);

        assertEquals(s1,(cp.parse("0 - 1")));
        assertEquals(s2,(cp.parse("1 - 2")));
        assertEquals(s3,(cp.parse("2 - 1")));
    }

    @Test
    public void multiplicationParse() throws IOException {
        Multiplication m1 = new Multiplication(c0, c1);
        Multiplication m2 = new Multiplication(c1, c2);
        Multiplication m3 = new Multiplication(c2, c1);

        assertEquals(m1,(cp.parse("0 * 1")));
        assertEquals(m2,(cp.parse("1 * 2")));
        assertEquals(m3,(cp.parse("2 * 1")));
    }

    @Test
    public void divisionParse() throws IOException {
        Division d1 = new Division(c0, c1);
        Division d2 = new Division(c1, c2);
        Division d3 = new Division(c2, c1);

        assertEquals(d1,(cp.parse("0 / 1")));
        assertEquals(d2,(cp.parse("1 / 2")));
        assertEquals(d3,(cp.parse("2 / 1")));
    }

    @Test
    public void compundParse() throws IOException {
        SymbolicExpression exp1 = mult(add(num(1), num(2)), num(3));
        assertEquals(exp1,(cp.parse("(1+2)*3")));

        SymbolicExpression exp2 = mult(mult(num(3), num(5)), sub(num(7), num(3)));
        assertEquals(exp2,(cp.parse("3*5*(7-3)")));

        SymbolicExpression exp3 = div(mult(num(5), add(num(1), num(2))), num(3));
        assertEquals(exp3,(cp.parse("(5*(1+2))/3")));

        SymbolicExpression exp4 = div(num(3), mult(neg(5), num(3)));
        assertEquals(exp4,(cp.parse("3/(-5)*3")));

        SymbolicExpression exp5 = mult(neg(5), neg(5));
        assertEquals(exp5, (cp.parse("(-5)*(-5)")));
    } 

    //Helper Functions
    static Constant num(double d) { 
        return new Constant(d); 
    }

    static Negation neg(double d) { 
        return new Negation(num(d)); 
    }

    static Addition add(SymbolicExpression exp1, SymbolicExpression exp2) { 
        return new Addition(exp1, exp2); 
    }

    static Subtraction sub(SymbolicExpression exp1, SymbolicExpression exp2) { 
        return new Subtraction(exp1, exp2); 
    }

    static Multiplication mult(SymbolicExpression exp1, SymbolicExpression exp2) { 
        return new Multiplication(exp1, exp2); 
    }

    static Division div(SymbolicExpression exp1, SymbolicExpression exp2) { 
        return new Division(exp1, exp2); 
    }
}
