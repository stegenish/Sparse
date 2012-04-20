package Tokeniser;
/*
 * Created on 02-Feb-2004
 *
 * Components as members of society.
 */

/**
 * @author Thomas Stegen 200111876
 *
 * Represents a token and information about that token like line number
 * and column number.
 */
public class Token
{
    /**
     * The token as it appeared in the tokenised string.
     */
    private String token;

    /**
     * The number of the line the token appeared on
     */
    private int line;

    /**
     * The number of the column the token appeared on.
     */
    private int column;

    /**
     * Constructs a new Token with a given value.
     * @param tok The string that produced this Token.
     */
    public Token(String tok)
    {
        token = tok;
    }


    /**
     * Construct the Token object with a given line and column.
     *
     * @param tok
     * @param l
     * @param col
     */
    public Token(String tok, int l, int col)
    {
        token = tok;
        line = l;
        column = col;
    }

    /**
     * @return The token as it appeared in the tokenised string.
     */
    public String getToken()
    {
        return token;
    }

    /**
     * @return The line number this token appeared on.
     */
    public int getLine()
    {
        return line;
    }

    public void setLine(int l)
    {
        line = l;
    }

    /**
     * @return The column number this token appeared on.
     */
    public int getColumn()
    {
        return column;
    }

    public void setColumn(int col)
    {
        column = col;
    }

    public String toString()
    {
        String s = "'" +token + "': " + line + "/" + column;
        return s;
    }
}
