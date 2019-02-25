package ioopm.calculator.parser;

import java.io.IOException;

public class SyntaxErrorException extends IOException {
    /*!
     *\brief Creates a new SyntaxErrorException
     * \param s The error message
     */
    public SyntaxErrorException(String s){
        super(s);
    }
}
