package specialForms;

import sparser.SparseBoolean;
import sparser.SparserTestCase;
import sparser.Symbol;

public class BoundpTest extends SparserTestCase {

	public BoundpTest(String testName) {
		super(testName);
	}

	public void testIsBoundIsBoundInParser() throws Exception {
		Symbol symbol = parser.getSymbol("isbound");
		assertTrue(globalScope.getBinding(symbol) != null);
	}
	
	public void testIsBoundReturnsFalseOnUnboundSymbol() throws Exception {
		assertEquals(SparseBoolean.False, executeString("(isbound safaw34f4)"));
	}
	
	public void testIsBoundReturnsTrueOnBoundSymbol() throws Exception {
		assertEquals(SparseBoolean.True, executeString("(bind 1 b) (isbound b)"));
	}
}
