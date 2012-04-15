package specialForms;


import sparser.ArgumentList;
import sparser.Code;
import sparser.Entity;
import sparser.Callable;
import sparser.SparseList;
import sparser.SparserTestCase;
import sparser.Symbol;
import sparser.UserDefinedFunction;

public class DefunTest extends SparserTestCase {

	public DefunTest(String testName) {
		super(testName);
	}

	public void testEmptyFunction() throws Exception {
		Defun defun = new Defun();
		ArgumentList args = ArgumentList.createArgumentList();
		args.addArg(new Symbol("func"));
		args.addArg(new SparseList());
		Entity call = defun.callWithScope(args, scope);
		assertTrue(call instanceof UserDefinedFunction);
	}

	public void testSimpleFunction() throws Exception {
		String program = "(defun funcName () (test 1 2 3 4 5)(test 3 a))";
		Callable func = parseFunction(program);
        Symbol symbol = getSymbol("funcName");
        assertEquals(symbol.execute(scope), func);
        ArgumentList passedArgs = ArgumentList.createArgumentList();
		func.callWithScope(passedArgs, scope);
		ArgumentList args = testSpecialForm.lastCallArgs;
		assertEquals(sparseInt("3"), args.next());
		assertEquals(getSymbol("a"), args.next());
		assertEquals(2, testSpecialForm.callHistory.size());
	}

	public void testFunctionWithParameters() throws Exception {
		String program = "(defun adder (a b) (testFun (add a b)))";
		Callable func = parseFunction(program);
        ArgumentList passedArgs = ArgumentList.createArgumentList();
        passedArgs.addArg(sparseInt("4"));
        passedArgs.addArg(sparseInt("5"));
		func.callWithScope(passedArgs, scope);
		assertEquals(sparseInt("9"), testFunction.lastCallArgs.next());
	}

	private Callable parseFunction(String program) {
		Code code = parser.parseString(program);
        Callable func = (Callable) code.execute(globalScope);
		return func;
	}
}
