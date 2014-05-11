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
     * @return The token object that represents the token.
     */
    public Token next();
    
    /**
     * @return True if the Tokeniser has more tokens.
     */
    public boolean hasMore();  
    
    /**
     * Count the number of tokens left in the string.
     * @return The number of tokens left.
     */
    public int countTokens();
    
    /**
     * Change to a different token.
     * @param n How many tokens to move. Negative numbers changes the
     *          next token to return to a previous one. If the function
     *          fails there will be no change in observable state. Failure
     *          can occur because it was attempted to move the position
     *          beyond the last or first token.
     * @return True on success, false on failure.
     */
    public boolean move(int n);
    
    /**
     * Start parsing a new string. All unused tokens from the previous
     * String will be discarded.
     * @param s The new String.
     * @param source TODO
     */
    public void setString(Reader source);
}
