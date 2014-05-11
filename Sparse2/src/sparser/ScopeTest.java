package sparser;


public class ScopeTest extends SparserTestCase {

	public ScopeTest(String testName) {
		super(testName);
	}

	public void testGlobalScope() throws Exception {
		Symbol s = createSymbol("sym");
		Entity binding = scope.getBinding(s);
		assertEquals(SparseInt.valueOf(2), binding);
	}

	public void testLocalScope() throws Exception {
		Symbol s = bindSymbolToScope(scope, "sym", new SparseString("a string"));
		Entity binding = scope.getBinding(s);
		assertEquals(new SparseString("a string"), binding);
	}

	public void testLocalScopeWith2Bindings() throws Exception {
		Symbol s = bindSymbolToScope(scope, "sym", new SparseString("a string"));
		Symbol s2 = bindSymbolToScope(scope, "sym2", SparseInt.valueOf(3));
		Entity binding = scope.getBinding(s);
		assertEquals(new SparseString("a string"), binding);
		binding = scope.getBinding(s2);
		assertEquals(SparseInt.valueOf(3), binding);
	}
	
	
	public void testSetThrowsExceptionWhenNotBound() {
		try {
			scope.set(new Symbol("notSet"), SparseNull.theNull);	
		} catch (SparseException e) {
			return;
		}
		
		fail("set should throw exception when symbol is not bound");
	}
	
	private Symbol bindSymbolToScope(Scope scope, String name, Entity value) {
		Symbol s = createSymbol(name);
		scope.bind(s, value);
		return s;
	}

	private Symbol createSymbol(String symName) {
		Symbol s = new Symbol(symName);
		scope.bind(s, SparseInt.valueOf("2"));
		return s;
	}
}
