/*
 * Created on Aug 6, 2004
 */
package sparser;

import Tokeniser.Token;
import Tokeniser.TokeniserStrategy;

/**
 * @author Thomas Stegen
 */
public class SparseTokeniserStrategy extends TokeniserStrategy
{
	private static final int WHITE             = 1;
	private static final int COLLECT_TOKEN     = 2;
	private static final int RETURNED_BRACKET  = 3;
	private static final int RETURNED_TOKEN    = 4;
	private static final int COLLECT_STRING    = 5;

	/*Holds the current state of the tokeniser*/
	private int state = WHITE;

	public SparseTokeniserStrategy()
	{
		setGoBack(0);
	}

	public boolean nextLetter(char c)
	{
		boolean retVal = false;
		setGoBack(0);
		switch(state)
		{
			case WHITE:
				retVal = handleWHITE(c);
				break;
			case COLLECT_TOKEN:
				retVal = handleCOLLECT_TOKEN(c);
				break;
			case RETURNED_BRACKET:
				retVal = handleRETURNED_BRACKET(c);
				break;
			case RETURNED_TOKEN:
				retVal = handleRETURNED_TOKEN(c);
				break;
			case COLLECT_STRING:
				retVal = handleCOLLECT_STRING(c);
				break;
		}
		return retVal;
	}

	private boolean handleWHITE(char c)
	{
		if(Character.isWhitespace(c))
		{
			state = WHITE;
			return false;
		}
		else if(c == '(' || c == ')')
		{
			state = RETURNED_BRACKET;
			append(c);
			return true;
		}
		else if(c == '"')
		{
			state = COLLECT_STRING;
			append(c);
			return false;
		}
		else
		{
			state = COLLECT_TOKEN;
			append(c);
			return false;
		}
	}

	private boolean handleCOLLECT_TOKEN(char c)	{
		if(Character.isWhitespace(c)) {
			state = RETURNED_TOKEN;
			return true;
		}
		else if(c == '(' || c == ')') {
			state = RETURNED_TOKEN;
			setGoBack(1);
			return true;
		}
		else if(c == '"') {
			state = COLLECT_STRING;
			append(c);
			return true;
		}
		else {
			state = COLLECT_TOKEN;
			append(c);
			return false;
		}
	}

	private boolean handleRETURNED_BRACKET(char c) {
		if(Character.isWhitespace(c)) {
			state = WHITE;
			return false;
		}
		else if(c == '(' || c == ')') {
			state = RETURNED_BRACKET;
			append(c);
			return true;
		}
		else if(c == '"') {
			state = COLLECT_STRING;
			append(c);
			return false;
		}
		else {
			state = COLLECT_TOKEN;
			append(c);
			return false;
		}
	}
	private boolean handleRETURNED_TOKEN(char c) {
		if(Character.isWhitespace(c)) {
			state = WHITE;
			return false;
		}
		else if(c == '(' || c == ')') {
			state = RETURNED_BRACKET;
			append(c);
			return true;
		}
		else if(c == '"') {
			state = COLLECT_STRING;
			append(c);
			return false;
		}
		else {
			state = COLLECT_TOKEN;
			append(c);
			return false;
		}
	}

	private boolean handleCOLLECT_STRING(char c) {
		if (c == '"') {
			state = WHITE;
			append(c);
			return true;
		} else {
			append(c);
			return false;
		}
	}

	public Token createToken() {
		String s = getCurrentToken();
		Token tok = new SparseToken(s);
		return tok;
	}
}
