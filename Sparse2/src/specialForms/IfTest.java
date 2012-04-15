package specialForms;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.SparseBoolean;
import sparser.SparseInt;
import sparser.SparserTestCase;

public class IfTest extends SparserTestCase {

	public IfTest(String testName) {
		super(testName);
	}

	public void testTestTrueExecutesSecondExpression() throws Exception {
		Entity test = SparseBoolean.True;
		Entity expression1 = SparseInt.valueOf(1);
		Entity expected = expression1.execute(scope);
		Entity expression2 = SparseInt.valueOf(2);
		checkIf(test, expression1, expression2, expected);
	}

	public void testTestFalseExecutesSecondExpression() throws Exception {
		Entity test = SparseBoolean.False;
		Entity expression1 = SparseInt.valueOf(1);
		Entity expression2 = SparseInt.valueOf(2);
		Entity expected = expression2.execute(scope);
		checkIf(test, expression1, expression2, expected);
	}
	
	public void testTestNotTrueOrFalseThrowsException() throws Exception {
		Entity test = SparseInt.valueOf(0);
		Entity expression1 = SparseInt.valueOf(1);
		Entity expression2 = SparseInt.valueOf(2);
		Entity expected = expression2.execute(scope);
		checkIf(test, expression1, expression2, expected);
	}
	
	private void checkIf(Entity test, Entity expression1, Entity expression2, Entity expected) {
		If ifExpression = new If();
		ArgumentList args = ArgumentList.createArgumentList();
		args.addArg(test);
		args.addArg(expression1);
		args.addArg(expression2);
		
		Entity result = ifExpression.callWithScope(args, scope);
		
		assertEquals(expected, result);
	}

}
