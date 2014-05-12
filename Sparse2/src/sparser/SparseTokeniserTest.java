package sparser;

import java.io.StringReader;

import junit.framework.TestCase;
import Tokeniser.Token;

public class SparseTokeniserTest extends TestCase {

	public void testWithSingleQuote() throws Exception {
		SparseTokeniser tokeniser = new SparseTokeniser(new StringReader(" ' asd (''xcv) "));
		Token next; 
		while((next = tokeniser.next()) != null) {
			System.out.println(next);
		}
	}
}
