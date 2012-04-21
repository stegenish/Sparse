/*
 * Created on 05-Feb-2004
 *
 * Components as members of society.
 */
package Tokeniser;

/**
 * @author Thomas Stegen 200111876
 * An exception which is thrown when something is wrong with the strategy
 * object that customises the StrategyTokeniser.
 *
 * (At this stage, this is probably the case. There might be a bug in the
 * actual StrategyTokeniser.)
 */
public class TokeniserException extends RuntimeException
{
	private static final long serialVersionUID = 6896517541001713631L;

	/**
     * Create a new StrategyException.
     * @param msg The message that the exception holds.
     */
    public TokeniserException(String msg)
    {
        super(msg);
    }
}
