package sparser;

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
		scope = globalScope.createFunctionScope();
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

	protected Code parseProgram(String form) {
		return parser.parseString(form);
	}

	protected Entity parseEntity(String code) {
		return parseProgram(code).getEntities().get(0);
	}
}
