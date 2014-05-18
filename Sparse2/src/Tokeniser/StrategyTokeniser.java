/*
 * Created on 04-Feb-2004
 *
 * Components as members of society.
 */
package Tokeniser;

import java.io.IOException;
import java.io.Reader;

/**
 * @author Thomas Stegen 200111876
 *
 * A tokeniser that can be customised using the strategy patterm
 */
public class StrategyTokeniser implements Tokeniser
{
    /**
     * The strategy object for this StrategyTokeniser
     * Any point where the strategy object is used is marked ***HOOK***
     * as a comment
     */
    protected TokeniserStrategy strategy;

	private TokenGetter tokenGetter;

    /**
     * Set up a StrategyTokeniser with the given TokeniserStrategy
     *
     * @param strat The TokeniserStrategy to use.
     */
    public StrategyTokeniser(TokeniserStrategy strat)
    {
    	if(strat == null)
		{
			throw new TokeniserException("Strategy object cannot be null.");
		}
        strategy = strat;
    }

    /**
     * @see Tokeniser#next()
     */
    public Token next()
    {
    	try {
			return tokenGetter.nextToken();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }

    public void setString(Reader source)
    {
        strategy.reset();
        
        tokenGetter = new TokenGetter(strategy, source);
    }
    
    public TokenGetter getTokenGetter() {
    	return tokenGetter;
    }
    
    public void setGoBack(int n) {
    	strategy.setGoBack(n);
    }
}