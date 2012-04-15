/*
 * Created on 04-Feb-2004
 *
 * Components as members of society.
 */
package Tokeniser;

import Tokeniser.Token;

/**
 * @author Thomas Stegen 200111876
 *
 * The class that determines how a StrategyTokeniser is to behave.
 */
public abstract class TokeniserStrategy
{
    private StringBuffer token;
    private int goBack;

    /**
     *
     */
    public TokeniserStrategy()
    {
        token = new StringBuffer("");
        goBack = 0;
    }

    /**
     * Add a new letter to the current token. If a token is formed from the
     * last few letters true is returned.
     *
     * @param c The character to add
     * @return True if the characters since the last time this
     *  function returned true forms a token.
     */
    abstract public boolean nextLetter(char c);

    /**
     * Creates a token objects and sets it up with proper values. This
     * function is called after nextLetter returns true. The
     * StrategyTokeniser will set the line and column fields. This
     * method only works if append is used also.
     *
     * @return A Token object which represents the token.
     */
    public Token createToken()
    {
        String s = token.toString();
        Token tok = new Token(s);

        /*clear the buffer*/
        token.setLength(0);
        return tok;
    }

    /**
     * Adds a new character to the new token. If the method is overridden
     * or not used the TokeniserStrategy.createToken method will not work
     * as is and must be redefined.
     * @param c
     */
    public void append(char c) {
        token.append(c);
    }

    /**
     * @return The current token candidate
     */
    public String getCurrentToken()
    {
        return token.toString();
    }

    /**
     * Sometimes it cannot be determined whether something is a token or
     * not until some extra characters have been received. If this is the
     * case some of the characters might need to be scanned again. This
     * function tells the StrategyTokeniser to go back a certain number
     * of characters and start scanning from there. This is just one option
     * and everything can be taken care of in a subclass of this class.
     *
     * @return the number of characters to move back.
     */
    public int goBack()
    {
        return goBack;
    }

    /**
     * Set the value that the goBack method is to return
     * @param i
     */
    public void setGoBack(int i)
    {
        goBack = i;
    }

    /**
     * Called when there are no more characters to be parsed. If
     * there are any remaining characters these can be turned into
     * a Token. If there are is no Token null must be returned. As
     * implemented in this class any remaining characters are
     *
     * @return A Token made up of the remaining characters or null if no
     *         token exists.
     */
    public Token endOfInput()
    {
        Token tok = null;
        if(getCurrentToken().length() > 0)
        {
            tok = createToken();
        }
        return tok;
    }

    /**
     * Reset this object. This should make the TokeniserStrategy behave
     * properly if the following characters passed to it using nextLetter
     * has no relation to any previous characters. If this method is
     * overridden it must be called in the superclass
     */
    public void reset()
    {
        token = new StringBuffer("");
    }
}
