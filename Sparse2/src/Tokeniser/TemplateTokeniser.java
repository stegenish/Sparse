/*
 * Created on 09-Feb-2004
 *
 * Components as members of society.
 */
package Tokeniser;

import java.util.Stack;
import java.util.Vector;

/**
 * @author Thomas Stegen 200111876
 */
public abstract class TemplateTokeniser implements Tokeniser
{
    /**
     * Holds the string that is being tokenised.
     */
    private String string;

    /**
     * Holds the tokens that have been generated
     */
    private Vector<Token> tokens;

    /**
     * The next position in tokens to return
     */
    private int pos = 0;

    /*Attributes below here are for use while parsing the string. That is,
      used by the subclass of this class*/

    /**
     * The StringBuffer that the append method appends to
     */
    protected StringBuffer token;

    public TemplateTokeniser()
    {
    	//default constructor
    }

    /**
     * Constructs a new TemplateTokeniser.
     * @param s The string to tokenise
     */
    public TemplateTokeniser(String s)
    {
        string  = s;
        tokens = new Vector<Token>();
        pos = 0;
        token = new StringBuffer();
        /*getTokens();*/
    }
    /**
     * @see Tokeniser.Tokeniser#next()
     */
    public Token next()
    {
        return tokens.get(pos++);
    }

    /**
     *
     */
    public void getTokens()
    {
        char c;
        int strpos = 0; /*The position in the string*/
        boolean haveToken = false; /*indicates if we have a token or not*/
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
            haveToken = nextLetter(c); /***HOOK***/

            if(c == '\n')
            {
                colStack.push(new Integer(column));
                column = 0;
                line++;
            }
            column++;

            if(haveToken)
            {

                /*first create the token*/
                tok = createToken();  /***HOOK***/
                tok.setLine(tokLine);
                tok.setColumn(tokCol);
                back = goBack();      /***HOOK***/

                /*Negative values not allowed*/
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

                /*update column and line properly in case there is a need to
                  go back*/
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
        /*Check if there are any leftover characters and if
          they form a token.*/
        tok = endOfInput();      /***HOOK***/
        if(tok != null)
        {
            tok.setLine(tokLine);
            tok.setColumn(tokCol);
            tokens.add(tok);
        }
    }

    /**
     * @see Tokeniser.Tokeniser#hasMore()
     */
    public boolean hasMore()
    {
        return pos < tokens.size();
    }

    /**
     * @see Tokeniser.Tokeniser#countTokens()
     */
    public int countTokens()
    {
        return tokens.size() - pos;
    }

    /**
     * @see Tokeniser.Tokeniser#move(int)
     */
    public boolean move(int n)
    {
        if(((pos + n) < 0) || ((pos + n) > tokens.size()))
        {
            return false;
        }
        pos += n;
        return true;
    }

    /**
     * @see Tokeniser.Tokeniser#setString(java.lang.String)
     */
    public void setString(String s)
    {
    	//not implemented
    }

    /**
     * Add a new letter to the current token. If a token is formed from the
     * last few letters true is returned.
     *
     * @param c The character to add
     * @return True if the characters since the last time this
     *  function returned true forms a token.
     */
    public abstract boolean nextLetter(char c);

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
        return 1;
    }

    public Token createToken()
    {
        String tmp = token.toString();
        token.setLength(0);

        return new Token(tmp);
    }

    public Token endOfInput()
    {
        Token tok = null;
        if(token.length() > 0)
        {
            tok = createToken();
        }
        return tok;
    }
}
