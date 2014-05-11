package sparser;

import java.io.StringReader;

import junit.framework.TestCase;

public class SparseTokeniserTest extends TestCase {

	public void testWithSingleQuote() throws Exception {
		SparseTokeniser tokeniser = new SparseTokeniser(new StringReader(" ' asd (''xcv) "));
		while(tokeniser.hasMore()) {
			System.out.println(tokeniser.next());
		}
	}
}
