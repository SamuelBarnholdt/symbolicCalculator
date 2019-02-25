package ioopm.calculator.ast;

import java.util.HashSet;

public class Keywords {
    /*!
     *\brief A HashMap contaning all the keywords that can't be used as identifiers or names
     */
    public static final HashSet<String> keywords = new HashSet<>();

    static {
	keywords.add("sin");
	keywords.add("cos");
	keywords.add("log");
	keywords.add("exp");
	keywords.add("quit");
	keywords.add("vars");
	keywords.add("clear");
	keywords.add("if");
	keywords.add("else");
	keywords.add("end");
	keywords.add("function");
    }
}
