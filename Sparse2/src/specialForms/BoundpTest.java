package specialForms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import sparser.SparseBoolean;
import sparser.Symbol;
import tests.SparserTestCase;

public class BoundpTest extends SparserTestCase {
	
	@Test
	public void testIsBoundIsBoundInParser() throws Exception {
		Symbol symbol = parser.getSymbol("isbound");
		assertTrue(globalScope.getBinding(symbol) != null);
	}
	
	@Test
	public void testIsBoundReturnsFalseOnUnboundSymbol() throws Exception {
		assertEquals(SparseBoolean.False, executeString("(isbound safaw34f4)"));
	}
	
	@Test
	public void testIsBoundReturnsTrueOnBoundSymbol() throws Exception {
		assertEquals(SparseBoolean.True, executeString("(bind 1 b) (isbound b)"));
	}
}
