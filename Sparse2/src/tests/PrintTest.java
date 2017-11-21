package tests;

import org.junit.Test;

import sparser.Code;

public class PrintTest extends SparserTestCase {

	@Test
	public void testPrint() throws Exception {
		Code code = parser.parseString("(print \"Hello Sparse World!\")");
		code.execute(scope);
	}
}
