/*
 * Created on Aug 6, 2004
 */
package sparser;

import Tokeniser.Token;

/**
 * @author Thomas Stegen
 *
 * Extends Token to also hold the type of this token.
 */
public class SparseToken extends Token
{
    /**
     * The token value is a '('
     */
    public final static int LBRACKET   = 1;
    
    /**
     * The token value is a ')'
     */
    public final static int RBRACKET   = 2;
    
    /**
     * The token is a string.
     */
    public final static int STRING     = 3;
    
    /**
     * The token is anything not mentioned above
     */
    public final static int SYMBOL     = Integer.MAX_VALUE;
    
    /**
     * Holds the type of the Token
     */
    private int type;
    /**
     * Creates a new token of the given type which holds the given value.
     *
     * @param tok The actual token represented by this token
     * @param type The type of this token.
     */
    public SparseToken(String tok)
    {
        super(tok);
        findType(tok);
    }
    
    /**
     * Get the tyoe of this token.
     * @return
     */
    public int getType()
    {
        return type;
    }
    
    private void findType(String s)
    {
        if(s.equals("(") && s.length() == 1)
        {
            type = LBRACKET;
        }
        else if(s.equals(")") && s.length() == 1)
        {
            type = RBRACKET;
        }
        else if(s.charAt(0) == '"')
        {
            type = STRING;
        }
        else
        {
            type = SYMBOL;
        }
    }
}
