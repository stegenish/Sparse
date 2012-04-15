package specialForms;

import sparser.Entity;
import sparser.SparseInt;
import sparser.SparseList;
import sparser.SparserTestCase;

public class QuoteTest extends SparserTestCase {

	public QuoteTest(String testName) {
		super(testName);
	}

	public void testQuote() throws Exception {
		Entity code = parseEntity("(quote (1 2 3))");
		SparseList list = (SparseList) code.execute(globalScope);
		assertEquals(SparseInt.valueOf(1), list.getFirstElement());
		SparseList rest = list.getNext();
		assertEquals(SparseInt.valueOf(2), rest.getFirstElement());
		assertEquals(SparseInt.valueOf(3), rest.getNext().getFirstElement());
	}
}
