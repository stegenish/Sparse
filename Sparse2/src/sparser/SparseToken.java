/*
 * Created on Aug 6, 2004
 */
package sparser;

import Tokeniser.Token;

/**
 * @author Thomas Stegen
 * 
 *         Extends Token to also hold the type of this token.
 */
public class SparseToken extends Token {
	/**
	 * The token value is a '('
	 */
	public final static int LBRACKET = 1;

	/**
	 * The token value is a ')'
	 */
	public final static int RBRACKET = 2;

	/**
	 * The token is a string.
	 */
	public final static int STRING = 3;

	/**
	 * The token is a reader macro.
	 */
	public final static int READER_MACRO = 4;
	
	/**
	 * The token is a number
	 */
	public final static int INTEGER = 5;

	/**
	 * The token is anything not mentioned above
	 */
	public final static int SYMBOL = Integer.MAX_VALUE;

	/**
	 * Holds the type of the Token
	 */
	private int type;

	/**
	 * Creates a new token of the given type which holds the given value.
	 * 
	 * @param tok
	 *            The actual token represented by this token
	 * @param readerMacro
	 * @param type
	 *            The type of this token.
	 */
	public SparseToken(String tok, boolean readerMacro) {
		super(tok);
		findType(tok, readerMacro);
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		return super.toString() + " " + typeString();
	}
	
	private String typeString() {

		switch(type)
		{
		case LBRACKET:
			return "LBRACKET";
		case RBRACKET:
			return "RBRACKET";
		case STRING:
			return "STRING";
		case READER_MACRO:
			return "READER_MACRO";
		case SYMBOL:
			return "SYMBOL";
		default:
			return "Death and decay, an unknown token type";
		}
	}

	private void findType(String s, boolean readerMacro)
    {
    	if(readerMacro) {
    		type = READER_MACRO;
    	} else if(s.equals("(") && s.length() == 1) {
            type = LBRACKET;
        }
        else if(s.equals(")") && s.length() == 1) {
            type = RBRACKET;
        }
        else if(s.charAt(0) == '"') {
            type = STRING;
        }
        else {
        	if(s.matches("^-?\\d+$")) {
        		type = INTEGER;
        	} else {
        		type = SYMBOL;
        	}
        }
    }
}
