/*
 * SyntaxErrorException.java
 *
 * Created on 08 August 2004, 22:12
 */

package sparser;

/**
 *
 * @author  Administrator
 */
public class SyntaxErrorException extends RuntimeException
{
	private static final long serialVersionUID = -5315328595986100465L;

	SparseToken tok;
	
    public SyntaxErrorException(SparseToken token, String msg)
    {
        super(msg);
        tok = token;
    }
    
    public String toString()
    {
        if(tok == null)
        {
            return "null, " + getMessage();
        }
        return tok + " " + getMessage();
    }
}
