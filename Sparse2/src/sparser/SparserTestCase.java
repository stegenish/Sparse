package sparser;

import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;
import sparser.builtins.TestBuiltin;
import specialForms.TestSpecialForm;

public abstract class SparserTestCase extends TestCase {
	protected Sparser parser;
	protected TestBuiltin testFunction;
	protected TestSpecialForm testSpecialForm;
	protected Scope scope;
	protected Scope globalScope;

	public SparserTestCase(String testName) {
		super(testName);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		globalScope = new Scope();
		testSpecialForm = new TestSpecialForm();
		parser = new Sparser(globalScope);
		parser.bindSymbol("test", testSpecialForm, globalScope);

		testFunction = new TestBuiltin();
		parser.bindSymbol("testFun", testFunction, globalScope);
		scope = globalScope.createLexicalScope();
	}

	protected SparseInt sparseInt(String string) {
		return new SparseInt(string);
	}

	protected Sparser parser() {
		return parser;
	}

	protected Entity getSymbolBinding(String symName) {
		return getSymbol(symName).execute(globalScope);
	}

	protected Symbol getSymbol(String symName) {
		return parser.getSymbol(symName);
	}

	protected Code parseProgram(String form) throws FileNotFoundException, IOException {
		return parser.parseString(form);
	}

	protected Entity parseEntity(String code) throws FileNotFoundException, IOException {
		return parseProgram(code).getEntities().get(0);
	}

	protected Code parseString(String code) throws FileNotFoundException, IOException {
		return parser.parseString(code);
	}
	
	protected Entity parseForm(String code) throws FileNotFoundException, IOException {
		return parser.parseString(code).getEntities().get(0);
	}

	public Entity executeString(String str) throws FileNotFoundException, IOException {
		Code code = parseString(str);
		Entity firstElement = code.execute(globalScope);
		return firstElement;
	}

	public void checkIncorrectArgument(String code, String expected) throws FileNotFoundException, IOException {
		Exception caught = null;
		try {
			executeString(code);
		} catch (SparseException e) {
			assertTrue(e.getMessage(), e.getInfo().contains(expected));
			caught = e;
		}
		assertNotNull(caught);
	}
}
