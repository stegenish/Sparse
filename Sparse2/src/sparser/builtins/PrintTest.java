package sparser.builtins;

import sparser.Code;
import sparser.SparserTestCase;

public class PrintTest extends SparserTestCase {

	public PrintTest(String testName) {
		super(testName);
	}

	public void testPrint() throws Exception {
		Code code = parser.parseString("(print \"Hello Sparse World!\")");
		code.execute(scope);
	}
}
