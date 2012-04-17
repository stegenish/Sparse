/*
 * Created on 04-Feb-2004
 *
 * Components as members of society.
 */
package Tokeniser;

import java.util.Stack;
import java.util.Vector;

import Tokeniser.Token;
import Tokeniser.Tokeniser;

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
    private TokeniserStrategy strategy;

    /**
     * The characters to parse.
     */
    private String string;

    /**
     * Holds all the tokens after they have been generated
     */
    private Vector<Token> tokens;

    /**
     * Holds the position of the next token in tokens to return.
     */
    private int pos;

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
        pos = 0;
        tokens = new Vector<Token>();
    }

    /**
     * Set up a StrategyTokeniser with the given TokeniserStrategy to
     * parse the given String
     *
     * @param strat The TokeniserStrategy to use.
     * @param str The string to tokenise.
     * @throws StrategyException when the TokeniserStrategy object
     *         does something the StrategyTokeniser does not know how
     *         to handle. The message given to the exception will describe
     *         the problem. This exception is a RuntimeException
     */
    public StrategyTokeniser(TokeniserStrategy strat, String str)
    {
        this(strat);
        string = str;
        getTokens();
    }

    /**
     * @see Tokeniser#next()
     */
    public Token next()
    {
        if(hasMore())
        {
            return tokens.get(pos++);
        }
        else
        {
            return null;
        }
    }

    /**
     * Does the internal work of generating each token.
     */
    private void getTokens()
    {
        char c;
        int strpos = 0;
        boolean haveToken = false;
        int back = 0;  /*how many positions to backtrack*/
        Token tok;
        int line = 1;  /*current line*/
        int column = 1; /*current column*/
        int tokLine = 1; /*The start of the currently parsed token*/
        int tokCol = 1;  /*The start of the currently parsed token*/
        Stack<Integer> colStack = new Stack<Integer>(); /*holds previous max values of column*/

        while(strpos < string.length())
        {
            c = string.charAt(strpos++);
            haveToken = strategy.nextLetter(c); /***HOOK***/

            if(c == '\n')
            {
                colStack.push(new Integer(column));
                column = 0;
                line++;
            }
            column++;

            if(haveToken)
            {
                tok = strategy.createToken();
                tok.setLine(tokLine);
                tok.setColumn(tokCol);
                back = strategy.goBack();
                strategy.reset();
                
                if(back < 0)
                {
                    throw new TokeniserException("goBack cannot return  "+
                                                "negative value");
                }

                /*Do not go before the beginning of the string*/
                if((strpos - back) < 0)
                {
                    throw new TokeniserException("goBack caused new string " +
                                                "position to be less than 0");
                }

                strpos -= back;
                tokens.add(tok);

                column -= back;
                while(column < 1)
                {
                    int tmp = colStack.pop().intValue();
                    column = tmp + column;
                    line--;
                }

                tokLine = line;
                tokCol = column;
            }
        }

        endOfInput(tokLine, tokCol);
    }

	private void endOfInput(int tokLine, int tokCol) {
		Token tok;
		tok = strategy.endOfInput();
        if(tok != null)
        {
            tok.setLine(tokLine);
            tok.setColumn(tokCol);
            tokens.add(tok);
        }
	}

    /**
     * @see Tokeniser#hasMore()
     */
    public boolean hasMore()
    {
        return pos < tokens.size();
    }

    /**
     * @see Tokeniser#countTokens()
     */
    public int countTokens()
    {
        return tokens.size() - pos;
    }

    /**
     * @see Tokeniser#move(int n)
     */
    public boolean move(int n)
    {
        if(((pos + n) < 0) || ((pos + n) >= tokens.size()))
        {
            /*out of bounds, failure*/
            return false;
        }
        pos += n;
        return true;
    }


    /**
     * @see Tokeniser#setString(java.lang.String)
     */
    public void setString(String s)
    {
        strategy.reset();        /***HOOK***/
        string = s;
        tokens.clear();
        pos = 0;
        getTokens();
    }

    /**
     * Sets a new strategy object for this class. Existing tokens are
     * discarded.
     *
     * @param strat The new Strategy object.
     * @param discard If true the String currently being parsed will
     *        parsed (from the beginning) using the new strategy. If false
     *        the string is parsed and no tokens will be available ie.
     *        hasMore will return false. Using setString it is possible
     *        to add new strings and tokens though.
     */
    public void setStrategy(TokeniserStrategy strat, boolean reuse)
    {
        strategy = strat;
        tokens.clear();
        pos = 0;

        if(reuse)
        {
            getTokens();
        }
    }
}
