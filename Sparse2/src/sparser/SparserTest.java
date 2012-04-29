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
		Entity concattedList = executeString("(concat '(a b) '(c d))");
		Entity expectedList = parser.parseString("(a b c d)").getEntities().get(0);
		assertEquals(expectedList, concattedList);
	}
	
	public void testFirstExposed() throws Exception {
		Entity firstElement = executeString("(first (list 1 2 3))");
		assertEquals(SparseInt.valueOf(1), firstElement);
	}

	public void testLessThanExposed() throws Exception {
		assertEquals(SparseBoolean.True, executeString("(< 1 2)"));
	}

	public void testEqualExposed() throws Exception {
		Entity result = executeString("(equal 1 1)");
		assertEquals(SparseBoolean.True, result);
	}
	
	public void testEqualExposed2() throws Exception {
		Entity result = executeString("(equal 1 2)");
		assertEquals(SparseBoolean.False, result);
	}
	
	public void testEqualExposedForString() throws Exception {
		Entity result = executeString("(equal \"1asd\" \"1asd\")");
		assertEquals(SparseBoolean.True, result);
	}
	
	public void testRestExposed() throws Exception {
		Entity theRest = executeString("(rest (list 1 2 3))");
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
	
	public void testStackTraceContainsIndexOfIncorrectParameter() throws Exception {
		checkIncorrectArgument("(add (list 1) 1)", "argument 1");
	}

	public void testStackTraceContainsIndexOfIncorrectParameter2() throws Exception {
		checkIncorrectArgument("(add 1 (list 1))", "argument 2");
	}
	
	public void testStackTraceContainsNameOfFunction() throws Exception {
		checkIncorrectArgument("(add 1 (list 1))", "add");
	}
	
	public void testStackTraceContainsSourceOfError() throws Exception {
		checkIncorrectArgument("(add 1 (list 1))", "source:");
	}
	
	public void testStackTraceContainsSourceOfErrorAdditionalInformation() throws Exception {
		checkIncorrectArgument("(add 1 (list 1))", "Cannot cast");
	}
}
