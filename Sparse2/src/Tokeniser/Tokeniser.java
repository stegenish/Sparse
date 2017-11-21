package Tokeniser;

import java.io.Reader;


/*
 * Created on 02-Feb-2004
 *
 * Components as members of society. 
 */

/**
 * @author Thomas Stegen 200111876
 *
 * Specifies the interface of a string tokeniser.
 */
public interface Tokeniser 
{
    /**
     * Returns the next token.
     * @return The token object that represents the token. null if there are no more tokens
     */
    public Token next();
    
    /**
     * Start parsing a new string. All unused tokens from the previous
     * String will be discarded.
     * @param s The new String.
     * @param source TODO
     */
    public void setString(Reader source);
}
