package specialForms;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import sparser.ArgumentList;
import sparser.Callable;
import sparser.Entity;
import sparser.SparseList;
import sparser.Symbol;
import sparser.UserDefinedFunction;
import tests.SparserTestCase;

public class DefunTest extends SparserTestCase {


	@Test
	public void testEmptyFunction() throws Exception {
		Defun defun = new Defun();
		ArgumentList args = ArgumentList.createArgumentList();
		args.addArg(new Symbol("func"));
		args.addArg(new SparseList());
		Entity call = defun.callWithScope(args, scope);
		assertTrue(call instanceof UserDefinedFunction);
	}

	@Test
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

	@Test
	public void testFunctionWithParameters() throws Exception {
		String program = "(defun adder (a b) (testFun (add a b)))";
		Callable func = parseFunction(program);
        ArgumentList passedArgs = ArgumentList.createArgumentList();
        passedArgs.addArg(sparseInt("4"));
        passedArgs.addArg(sparseInt("5"));
		func.callWithScope(passedArgs, scope);
		assertEquals(sparseInt("9"), testFunction.lastCallArgs.next());
	}

	@Test
	public void testSecondArgumentMustBeSymbol() throws Exception {
		checkIncorrectArgument("(defun \"asd\" () null)", "argument 1");
	}

	@Test
	public void testSecondArgumentCannotBeNumber() throws Exception {
		checkIncorrectArgument("(defun 1 () null)", "argument 1");
	}

	@Test
	public void testThirdArgumentMustBeList() throws Exception {
		checkIncorrectArgument("(defun name asd null)", "argument 2");
	}

	private Callable parseFunction(String program) {
		try {
			return (Callable) executeString(program);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
