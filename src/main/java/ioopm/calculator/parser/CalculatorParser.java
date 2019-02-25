package ioopm.calculator.parser;

import ioopm.calculator.ast.*;
import ioopm.calculator.ast.Constant;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.*;

public class CalculatorParser {
    private StreamTokenizer st;
    private Environment environment;
    private FunctionEnvironment functions;

    private int parentheses;
    private int scopes;

    private boolean shouldQuit;


    /*!
     *\brief Creates a new parser object
     */
    public CalculatorParser() {
        this(new FunctionEnvironment());
    }

    /*!
     *\brief Creates a new parser object
     * \param f An existing function environment
     */
    public CalculatorParser(FunctionEnvironment f) {
        st = new StreamTokenizer(System.in);
        setTokenizerOptions();
        parentheses = 0;
        shouldQuit = false;
        this.environment = new Environment();
        this.functions = f;
    }

    /// PUBLIC FUNCTIONS
    /*!
     *\brief Parses a string into a symbolicexpression
     * \param s The string to be parsed
     * \return The symbolicexpression created from s
     */
    public SymbolicExpression parse(String s) throws IOException {
        newString(s);

        SymbolicExpression command = parseCommand();
        if(command != null) return command;

        SymbolicExpression conditional = parseConditional();
        if (conditional != null) {
            progNextToken();
            if (st.ttype != StreamTokenizer.TT_EOF) throw new SyntaxErrorException("Unexpected symbols.");
            return conditional;
        }

        SymbolicExpression returnval = parseExpression();
        if(st.ttype != StreamTokenizer.TT_EOF) throw new SyntaxErrorException("Unexpected symbols.");
        if (parentheses != 0 || scopes != 0) throw new SyntaxErrorException("Missing closing parenthesis/scope");
        return returnval;
    }

    /*!
     *\brief Parses a string into a function declaration
     * \param s The string to be parsed
     * \return The function declaration created from s
     */
    public FunctionDeclaration parseFunction(String s) throws IOException {
        newString(s);

        st.nextToken();
        st.nextToken();

        if(st.ttype != StreamTokenizer.TT_WORD) throw new SyntaxErrorException("Expected function name.");
        String identifier = st.sval;
        if(environment.containsKey(identifier)) environment.remove(identifier);
        if (Keywords.keywords.contains(identifier)) throw new SyntaxErrorException("Keywords can't be used as function names.");

        st.nextToken();
        if (st.ttype != '(') throw new SyntaxErrorException("Expected opening parentheses");

        LinkedList<String> arguments = new LinkedList<>();
        while(st.ttype != ')' && st.ttype != StreamTokenizer.TT_EOF) {
            st.nextToken();

            if (st.ttype != StreamTokenizer.TT_WORD) throw new SyntaxErrorException("Expected variable");
            if(Constants.namedConstants.containsKey(st.sval)) throw new SyntaxErrorException("Named constants can't be used as arguments.");
            if (arguments.contains(st.sval)) throw new SyntaxErrorException("Repeated argument");

            arguments.addLast(st.sval);
            st.nextToken();

            if(!(st.ttype == ',' || st.ttype == ')')) throw new SyntaxErrorException("Expected ,");
        }

        FunctionDeclaration funcdec = new FunctionDeclaration(identifier,arguments);
        functions.put(identifier,funcdec);

        return funcdec;
    }

    /*!
     *\brief Returns true if the quit command has been parsed otherwise false
     */
    public boolean shouldQuit() { return shouldQuit; }

    /*!
     *\brief Gets the variable enviroment
     */
    public Environment getEnvironment() { return this.environment; }

    /*!
     *\brief Gets the function enviroment
     */
    public FunctionEnvironment getFunctions() { return this.functions; }

    /// HELPER FUNCTIONS
    private void newString(String s) {
        st = new StreamTokenizer(new StringReader(s));
        setTokenizerOptions();

        parentheses = 0;
        scopes = 0;
    }

    private SymbolicExpression parseCommand() throws IOException {
        SymbolicExpression foundCommand = null;
        st.nextToken();

        if (st.ttype == StreamTokenizer.TT_WORD) {
            String val = st.sval;
            switch (st.sval) {
                case "vars":
                    foundCommand = new Vars();
                    break;
                case "clear":
                    foundCommand = new Clear();
                    break;
                case "quit":
                    shouldQuit = true;
                    foundCommand = new Quit();
                    break;
                default:
                    foundCommand = null;
                    break;
            }
        }

        st.pushBack();
        return foundCommand;
    }

    private SymbolicExpression parseConditional() throws IOException {
        progNextToken();

        if (st.ttype != StreamTokenizer.TT_WORD || !st.sval.equals("if")) {
            progPushBack();
            return null;
        }

        SymbolicExpression left = parseAtom();

        String operator = operator() + operator();
        if (operator.equals("") || operator.equals("=")) throw new SyntaxErrorException("Incorrect operator");

        SymbolicExpression right = parseAtom();
        Scope iftrue = parseConditionalScope();

        if (!nextIsElse()) throw new SyntaxErrorException("Expected else");

        progNextToken();
        Scope elsefalse = parseConditionalScope();

        return new Conditional(left,right,operator,iftrue,elsefalse);
    }
    private SymbolicExpression parseExpression() throws IOException {
        SymbolicExpression exp = parseTerm();
        progNextToken();

        while (st.ttype == '+' || st.ttype == '-' || st.ttype == '=' || st.ttype == StreamTokenizer.TT_EOF) {
            if (st.ttype == StreamTokenizer.TT_EOF) {
                return exp;
            }
            else if (st.ttype == '+') {
                if (nextIsSubtraction()) throw new SyntaxErrorException("Expected variable or number");
                exp = new Addition(exp, parseTerm());
                progNextToken();
            }
            else if (st.ttype == '-') {
                if (nextIsSubtraction()) throw new SyntaxErrorException("Expected variable or number");
                exp = new Subtraction(exp, parseTerm());
                progNextToken();
            }
            else if (st.ttype == '=') {
                exp = new Assignment(exp, parseAtom());
                progNextToken();
            }
        }

        progPushBack();
        return exp;
    }

    private SymbolicExpression parseTerm() throws IOException {
        SymbolicExpression exp = parseFactor();
        progNextToken();

        while (st.ttype == '*' || st.ttype == '/' )  {
            if (st.ttype == '*') {
                if (nextIsSubtraction()) throw new SyntaxErrorException("Expected variable or number");
                exp = new Multiplication(exp, parseFactor());
                progNextToken();
            }
            if (st.ttype == '/') {
                if (nextIsSubtraction()) throw new SyntaxErrorException("Expected variable or number");
                exp = new Division(exp,parseTerm());
                progNextToken();
            }
        }

        progPushBack();
        return exp;
    }

    private SymbolicExpression parseFactor() throws IOException {
        progNextToken();

        if (st.ttype == '(' ) {
            if (nextIsValid())
                throw new SyntaxErrorException("Unexpected symbol.");
            SymbolicExpression exp;

            if(nextIsIF()) exp = parseConditional();
            else exp = parseExpression();

            progNextToken();
            if (st.ttype != ')')
                throw new SyntaxErrorException("Unterminated parenthesis.");
            return exp;
        }
        if (st.ttype == '{' ) {
            if (nextIsValid()) throw new SyntaxErrorException("Unexpected symbol.");
            SymbolicExpression exp;

            if(nextIsIF()) exp = new Scope(parseConditional());

            else exp = new Scope(parseExpression());

            progNextToken();
            if (st.ttype != '}')
                throw new SyntaxErrorException("Unterminated scope.");
            return exp;
        }

        if (st.ttype == '-') {
            if (nextIsExpression())
                return new Negation(parseExpression());
            else
                return new Negation(parseAtom());
        }

        if (st.ttype == StreamTokenizer.TT_WORD) {
            if (functions.containsKey(st.sval))
                return parseFunctionCall();
            switch(st.sval) {
                case "sin":
                    return new Sin(parseFactor());
                case "cos":
                    return new Cos(parseFactor());
                case "log":
                    return new Log(parseFactor());
                case "exp":
                    return new Exp(parseFactor());
            }
        }

        progPushBack();
        return parseAtom();
    }

    private SymbolicExpression parseAtom() throws IOException {
        progNextToken();

        if (st.ttype == '+'
                || st.ttype == '-'
                || st.ttype == '*'
                || st.ttype == '/'
                || st.ttype == '('
                || st.ttype == ')')
            throw new SyntaxErrorException("Expected variable or number");

        if (st.ttype == StreamTokenizer.TT_WORD) {
            if (Keywords.keywords.contains(st.sval.toLowerCase()))
                throw new SyntaxErrorException("Keywords can't be used as variable names.");
            else if(st.sval.matches(".*\\d+.*")) //Checks if the string sval contains numbers
                throw new SyntaxErrorException("Variable names can't contain numbers.");
            else if (Constants.namedConstants.containsKey(st.sval))
                return new NamedConstant(st.sval);
            else if (functions.containsKey(st.sval)) {
                functions.remove(st.sval);
                return new Variable(st.sval);}
            else {
                Variable v = new Variable(st.sval);
                return v;
            }
        }

        if (st.ttype != StreamTokenizer.TT_NUMBER)
            throw new SyntaxErrorException("Expected number.");

        if (!nextIsSymbol())
            throw new SyntaxErrorException("Expected symbol.");

        return new Constant(st.nval);
    }

    private SymbolicExpression parseFunctionCall() throws IOException {
        FunctionDeclaration f = this.functions.get(st.sval);
        int length = f.getArguments().size();
        int noarguments = 0;
        String identifier = st.sval;

        progNextToken();
        if (st.ttype != '(') throw new SyntaxErrorException("Expected arguments to functioncall.");

        LinkedList<Atom> arguments = new LinkedList<>();
        while(st.ttype != ')') {
            Atom atom = (Atom) parseAtom();
            arguments.add(atom);
            noarguments++;

            progNextToken();
            if(!(st.ttype == ',' || st.ttype == ')')) throw new SyntaxErrorException("Expected ,");
        }

        if(noarguments != length) throw new SyntaxErrorException("Function " + identifier + " requires " + length + " arguments.");
        return new FunctionCall(identifier,arguments);
    }

    private boolean nextIsSymbol() throws IOException {
        st.nextToken();

        boolean result = (st.ttype == '{' || st.ttype == '<' || st.ttype == ',' || st.ttype == '+' || st.ttype == '-' || st.ttype == '*' || st.ttype == '/'  || st.ttype == '='
                || st.ttype == StreamTokenizer.TT_EOF || st.ttype == ')' || st.ttype == '}'|| st.ttype == '>');

        st.pushBack();
        return result;
    }

    private boolean nextIsOpening() throws IOException {
        st.nextToken();

        boolean result = (st.ttype == '(' || st.ttype == '{');

        st.pushBack();
        return result;
    }

    private boolean nextIsElse() throws IOException {
        st.nextToken();

        boolean iselse = st.ttype == StreamTokenizer.TT_WORD && st.sval.equals("else");

        st.pushBack();
        return iselse;
    }
    private boolean nextIsIF() throws IOException {
        st.nextToken();

        boolean isif = st.ttype == StreamTokenizer.TT_WORD && st.sval.equals("if");

        st.pushBack();
        return isif;
    }
    private boolean nextIsValid() throws IOException {
        st.nextToken();

        boolean result = (st.ttype == '+' || st.ttype == '*' || st.ttype == '/'  || st.ttype == '=' || st.ttype == StreamTokenizer.TT_EOF || st.ttype == ')' || st.ttype =='}');

        st.pushBack();
        return result;
    }

    private boolean nextIsExpression() throws IOException {
        progNextToken();

        if (st.ttype == StreamTokenizer.TT_WORD) {
            switch(st.sval) {
                case "sin":
                    progPushBack();
                    return true;

                case "cos":
                    progPushBack();
                    return true;

                case "log":
                    progPushBack();
                    return true;

                case "exp":
                    progPushBack();
                    return true;
                default:
                    progPushBack();
                    return false;
            }
        }
        else if (st.ttype == '(') {
            progPushBack();
            return true;
        }
        else {
            progPushBack();
            return false;
        }
    }

    private boolean nextIsSubtraction() throws IOException {
        st.nextToken();
        boolean result = (st.ttype == '-');
        st.pushBack();
        return result;
    }

    private void setTokenizerOptions() {
        st.ordinaryChar('+');
        st.ordinaryChar('-');
        st.ordinaryChar(')');
        st.ordinaryChar('(');
        st.ordinaryChar('=');
        st.ordinaryChar('/');
        st.ordinaryChar('*');
        st.ordinaryChar('{');
        st.ordinaryChar('}');
        st.ordinaryChar('<');
        st.ordinaryChar('>');
        st.ordinaryChar(',');
        st.eolIsSignificant(true);
    }

    private void progNextToken() throws IOException {
        st.nextToken();

        if (st.ttype == '(') this.parentheses += 1;
        if (st.ttype == ')') this.parentheses -= 1;

        if (st.ttype == '{') this.scopes += 1;
        if (st.ttype == '}') this.scopes -= 1;
        if (this.scopes < 0) throw new SyntaxErrorException("Too many closing scopes");
        if (this.parentheses < 0) throw new SyntaxErrorException("Too many closing parentheses.");
    }

    private void progPushBack() {
        if (st.ttype == '(') this.parentheses -= 1;
        if (st.ttype == ')') this.parentheses += 1;

        if (st.ttype == '{') this.scopes -= 1;
        if (st.ttype == '}') this.scopes += 1;

        st.pushBack();
    }

    private String operator() throws IOException {
        progNextToken();
        String operator = "";

        switch (st.ttype) {
            case '<' :
                operator = "<";
                break;
            case '>':
                operator = ">";
                break;
            case '=':
                operator = "=";
                break;
            default:
                progPushBack();
                return "";
        }

        return operator;
    }

    private Scope parseConditionalScope() throws IOException {
        progNextToken();
        if(st.ttype != '{') throw new SyntaxErrorException("Expected scope");

        Scope exp;
        if(nextIsIF()) exp = new Scope(parseConditional());
        else exp = new Scope(parseExpression());

        progNextToken();
        if(st.ttype != '}') throw new SyntaxErrorException("Expected closed scope");

        return exp;
    }
}
