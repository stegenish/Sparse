package sparser;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SymbolTest extends TestCase {

	public void testWhenSymbolHasNoValueThrowsException() throws Exception {
		SparseException expectedException = null;
		Symbol symbol = new Symbol("ab");
		try {
			symbol.execute(new Scope());
			fail("Exception not thrown when evaluating an unbound symbol");
		} catch (SparseException se) {
			expectedException = se;
		}
		Assert.assertEquals("Symbol not bound ab", expectedException.getMessage());
	}
}
