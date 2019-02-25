package ioopm.calculator.ast;

import java.util.HashMap;

public class Constants {
    /*!
     *\brief A HashMap contaning all the named constants of the parser
     */
    public static final java.util.HashMap<String,Double> namedConstants = new HashMap<String,Double>();

    static {
	Constants.namedConstants.put("pi",Math.PI);
	Constants.namedConstants.put("e",Math.E);
	Constants.namedConstants.put("Answer",42.0);
	Constants.namedConstants.put("L",6.022140857 * Math.pow(10,23));
    }
}
