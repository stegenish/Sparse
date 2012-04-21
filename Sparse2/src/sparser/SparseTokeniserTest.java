package sparser;

import junit.framework.TestCase;

public class SparseTokeniserTest extends TestCase {

	public void testWithSingleQuote() throws Exception {
		SparseTokeniser tokeniser = new SparseTokeniser(" ' asd (''xcv)");
		while(tokeniser.hasMore()) {
			System.out.println(tokeniser.next());
		}
	}
}
