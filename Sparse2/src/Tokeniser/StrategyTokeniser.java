/*
 * Created on 04-Feb-2004
 *
 * Components as members of society.
 */
package Tokeniser;

import java.io.IOException;
import java.io.Reader;
import java.util.Stack;
import java.util.Vector;

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
    
    private static class TokenGetter {
    	private TokeniserStrategy strategy;
    	private Reader source;
    	public Vector<Token> tokens = new Vector<>();

        private int currentLine = 1;  /*current line*/
        private int currentColumn = 1; /*current column*/
        private int currentTokenLine = 1; /*The start of the currently parsed token*/
        private int currentTokenColumn = 1;  /*The start of the currently parsed token*/
        private Stack<Integer> columnStack = new Stack<Integer>(); /*holds previous max values of column*/
        private StringBuffer readBuffer = new StringBuffer();
        private int charsRead = 0;
        private int currentReadBufferPosition = 0;
        
    	public TokenGetter(TokeniserStrategy strategy, Reader source) {
    		this.strategy = strategy;
    		this.source = source;
		}
        
		public Token nextToken() throws IOException {
			int readChar;
			char c;
			while((readChar = nextChar()) != -1)
			{
				c = (char)readChar;
				
				boolean haveToken = strategy.nextLetter(c);
  	
			    calculateLineAndColumn(c);
  	
			    if(haveToken)
			    {
			        return processToken();
			    }
			}
			
			return endOfInput();
		}

		private int nextChar() throws IOException {
			if (currentReadBufferPosition < charsRead) {
				return readBuffer.charAt(currentReadBufferPosition++);
			}
			int read = source.read();
			updateReadBuffer((char)read);
			return read;
		}

		private void updateReadBuffer(char c) {
			readBuffer.append(c);
			currentReadBufferPosition++;
			charsRead++;
		}

		private Token processToken() throws IOException {
			Token tok;
			tok = strategy.createToken();
			tok.setLine(currentTokenLine);
			tok.setColumn(currentTokenColumn);
			
			strategy.reset();

        	handleGoBack();
			
			tokens.add(tok);
			return tok;
		}

		private void calculateLineAndColumn(char c) {
			if(c == '\n')
			{
			    columnStack.push(currentColumn);
			    currentColumn = 0;
			    currentLine++;
			}
			currentColumn++;
		}
    	
    	private void handleGoBack() throws IOException {
    		int backtrack = strategy.goBack();
    		currentReadBufferPosition -= backtrack;
    		if(backtrack < 0)
			{
			    throw new TokeniserException("goBack cannot return "+
			                                "negative value");
			}
    		
    		/*long skipped = source.skip(-backtrack);
    		// Do not go before the beginning of the string
    		if(skipped != 0 && skipped != -backtrack)
    		{
    		    throw new TokeniserException("goBack caused new string " +
    		                                "position to be less than 0");
    		}*/
    		
    		currentColumn -= backtrack;
			while(currentColumn < 1)
			{
			    int tmp = columnStack.pop().intValue();
			    currentColumn = tmp + currentColumn;
			    currentLine--;
			}
  	
			currentTokenLine = currentLine;
			currentTokenColumn = currentColumn;
    	}

    	private Token endOfInput() {
    		Token tok;
    		tok = strategy.endOfInput();
            if(tok != null)
            {
                tok.setLine(currentTokenLine);
                tok.setColumn(currentTokenColumn);
                tokens.add(tok);
            }
            
            return tok;
    	}
    }
}
