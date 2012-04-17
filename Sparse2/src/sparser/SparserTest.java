package sparser;

public class SparserTest extends SparserTestCase {
	
	public SparserTest(String testName) {
		super(testName);
	}

	public void testReaderMacro() throws Exception {
		Code code = parser.parseString("''a");
		Entity entity = code.getEntities().get(0);
		assertEquals("(quote (quote a))", entity.toString());
	}
}
