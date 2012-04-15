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

	public void testCreateFunctionScope() throws Exception {
		Scope subScope = globalScope.createFunctionScope();
		Symbol symbol = getSymbol("add");
		Entity binding = subScope.getBinding(symbol);
		assertSame(globalScope.getBinding(symbol), binding);
	}

	public void testCreateSubFunctionScope() throws Exception {
		Scope subScope = globalScope.createFunctionScope();
		Symbol symbol = getSymbol("testSymbol");
		SparseString value = new SparseString("hello hello");
		subScope.bind(symbol, value);
		Scope subSubScope = subScope.createFunctionScope();
		assertNull(subSubScope.getBinding(symbol));
	}
	
	public void testCreateShadowScope() throws Exception {
		Scope subScope = globalScope.createFunctionScope();
		Symbol symbol = getSymbol("testSymbol");
		SparseString value = new SparseString("hello hello");
		subScope.bind(symbol, value);
		Scope subSubScope = subScope.createShadowScope();
		assertEquals(value, subSubScope.getBinding(symbol));
	}
	
	public void testCreateShadowedShadowScope() throws Exception {
		Scope shadowScope = globalScope.createFunctionScope();
		Symbol symbol = getSymbol("testSymbol");
		SparseString value = new SparseString("hello hello");
		shadowScope.bind(symbol, value);
		Scope shadowedShadowScope = shadowScope.createShadowScope().createShadowScope();
		SparseString shadowedShadowValue = new SparseString("another string");
		shadowedShadowScope.bind(symbol, shadowedShadowValue);
		assertEquals(shadowedShadowValue, shadowedShadowScope.getBinding(symbol));
	}

	public void testCreateFunctionShadowScopeHidesAllShadowScopes() throws Exception {
		Scope shadowScope = globalScope.createFunctionScope();
		Symbol symbol = getSymbol("testSymbol");
		SparseString value = new SparseString("hello hello");
		shadowScope.bind(symbol, value);
		Scope shadowedShadowScope = shadowScope.createShadowScope().createFunctionScope();
		assertEquals(null, shadowedShadowScope.getBinding(symbol));
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
