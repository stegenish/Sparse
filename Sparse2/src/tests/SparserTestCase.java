package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;

import sparser.Code;
import sparser.Entity;
import sparser.Environment;
import sparser.Scope;
import sparser.SparseException;
import sparser.SparseInt;
import sparser.Sparser;
import sparser.Symbol;
import sparser.builtins.TestBuiltin;
import specialForms.TestSpecialForm;

public abstract class SparserTestCase {
	protected Sparser parser;
	protected TestBuiltin testFunction;
	protected TestSpecialForm testSpecialForm;
	protected Scope scope;
	protected Scope globalScope;

	@Before
	public void setUp() throws Exception {
		testSpecialForm = new TestSpecialForm();
		
		Environment environment = new Environment();
		globalScope = environment.scope;
		parser = environment.sparser;
		environment.bindSymbol("test", testSpecialForm, environment.symbols, globalScope);

		testFunction = new TestBuiltin();
		environment.bindSymbol("testFun", testFunction, environment.symbols, globalScope);
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
