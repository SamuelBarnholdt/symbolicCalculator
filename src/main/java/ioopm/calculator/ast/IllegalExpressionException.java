package ioopm.calculator.ast;

import java.io.IOException;

public class IllegalExpressionException extends IOException {
    /*!
     *\brief Creates a new IllegalExpressionException
     * \param s The error message
     */
    public IllegalExpressionException(String s) {
	super(s);
    }
}
