package specialForms;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sparser.Entity;
import sparser.SparseInt;
import sparser.SparseList;
import tests.SparserTestCase;

public class QuoteTest extends SparserTestCase {


	@Test
	public void testQuote() throws Exception {
		Entity code = parseEntity("(quote (1 2 3))");
		SparseList list = (SparseList) code.execute(globalScope);
		assertEquals(SparseInt.valueOf(1), list.getFirstElement());
		SparseList rest = (SparseList) list.rest();
		assertEquals(SparseInt.valueOf(2), rest.getFirstElement());
		assertEquals(SparseInt.valueOf(3), ((SparseList) rest.rest()).getFirstElement());
	}
}
