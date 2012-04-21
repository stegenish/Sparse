/*
 * Created on Aug 6, 2004
 */
package sparser;

import Tokeniser.Token;
import Tokeniser.TokeniserStrategy;

/**
 * @author Thomas Stegen
 */
public class SparseTokeniserStrategy extends TokeniserStrategy {
	private static final int NOT_COLLECTING_TOKEN = 1;
	private static final int COLLECTING_TOKEN = 2;
	private static final int COLLECT_STRING = 5;

	/* Holds the current state of the tokeniser */
	private int state = NOT_COLLECTING_TOKEN;
	private boolean readerMacro;

	public SparseTokeniserStrategy() {
		setGoBack(0);
	}

	public boolean nextLetter(char c) {
		boolean retVal = false;
		setGoBack(0);

		switch (state) {
		case NOT_COLLECTING_TOKEN:
			retVal = handleNOT_COLLECTING_TOKEN(c);
			break;
		case COLLECTING_TOKEN:
			retVal = handleCOLLECT_TOKEN(c);
			break;
		case COLLECT_STRING:
			retVal = handleCOLLECT_STRING(c);
			break;
		}

		return retVal;
	}

	private boolean handleNOT_COLLECTING_TOKEN(char c) {
		if (Character.isWhitespace(c)) {
			state = NOT_COLLECTING_TOKEN;
			return false;
		} else if (c == '(' || c == ')') {
			state = NOT_COLLECTING_TOKEN;
			append(c);
			return true;
		} else if (c == '"') {
			state = COLLECT_STRING;
			append(c);
			return false;
		} else if(c == '\'') {
			state = NOT_COLLECTING_TOKEN;
			readerMacro = true;
			append(c);
			return true;
		} else {
			state = COLLECTING_TOKEN;
			append(c);
			return false;
		}
	}

	private boolean handleCOLLECT_TOKEN(char c) {
		if (Character.isWhitespace(c)) {
			state = NOT_COLLECTING_TOKEN;
			return true;
		} else if (c == '(' || c == ')') {
			state = NOT_COLLECTING_TOKEN;
			setGoBack(1);
			return true;
		} else if (c == '"') {
			state = COLLECT_STRING;
			append(c);
			return true;
		} else if(c == '\'') {
			state = NOT_COLLECTING_TOKEN;
			setGoBack(1);
			return true;
		} else {
			state = COLLECTING_TOKEN;
			append(c);
			return false;
		}
	}

	private boolean handleCOLLECT_STRING(char c) {
		if (c == '"') {
			state = NOT_COLLECTING_TOKEN;
			append(c);
			return true;
		} else {
			append(c);
			return false;
		}
	}

	public Token createToken() {
		String s = getCurrentToken();
		Token tok = new SparseToken(s, readerMacro);
		readerMacro = false;
		return tok;
	}
}
