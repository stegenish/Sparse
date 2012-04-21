package sparser;

public class SparserTest extends SparserTestCase {
	
	public SparserTest(String testName) {
		super(testName);
	}

	public void testReaderMacro() throws Exception {
		Code code = parseString("' 'a");
		Entity entity = code.getEntities().get(0);
		assertEquals("(quote (quote a))", entity.toString());
	}
	
	public void testSparserBindsExposeSparseFunctions() throws Exception {
		Symbol symbol = parser.getSymbol("concat");
		assertTrue(symbol.execute(globalScope) instanceof Callable);
	}
	
	public void testSparserExposedFunctionCanBeCalled() throws Exception {
		Code code = parseString("(concat '(a b) '(c d))");
		Entity concattedList = code.execute(globalScope);
		Entity expectedList = parser.parseString("(a b c d)").getEntities().get(0);
		assertEquals(expectedList, concattedList);
	}
	
	public void testFirstExposed() throws Exception {
		Code code = parseString("(first (list 1 2 3))");
		Entity firstElement = code.execute(globalScope);
		assertEquals(SparseInt.valueOf(1), firstElement);
	}
	
	public void testLessThanExposed() throws Exception {
		Code code = parseString("(< 1 2)");
		assertEquals(SparseBoolean.True, code.execute(globalScope));
	}

	public void testEqualExposed() throws Exception {
		Entity result = parseString("(equal 1 1)").execute(globalScope);
		assertEquals(SparseBoolean.True, result);
	}
	
	public void testEqualExposed2() throws Exception {
		Entity result = parseString("(equal 1 2)").execute(globalScope);
		assertEquals(SparseBoolean.False, result);
	}
	
	public void testEqualExposedForString() throws Exception {
		Entity result = parseString("(equal \"1asd\" \"1asd\")").execute(globalScope);
		assertEquals(SparseBoolean.True, result);
	}
	
	public void testRestExposed() throws Exception {
		Code code = parseString("(rest (list 1 2 3))");
		Entity theRest = code.execute(globalScope);
		Entity expectedValue = parser.parseString("(2 3)").getEntities().get(0);
		assertEquals(expectedValue, theRest);
	}
	
	public void testUnfinishedNestedListThrowsSyntaxException() {
		Exception thrownException = null;
		try {
			parseString("((a)");
		} catch (Exception e) {
			thrownException = e;
		}
		assertTrue(thrownException instanceof SyntaxErrorException);
	}
}
