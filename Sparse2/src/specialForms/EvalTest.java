package specialForms;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Function;
import sparser.SparseInt;
import sparser.SparserTestCase;

public class EvalTest extends SparserTestCase {
	
	public EvalTest(String testName) {
		super(testName);
	}

	public void testEvalReturnExecuteValueOfArgument() throws Exception {
		Function eval = new Eval();
		ArgumentList args = ArgumentList.createArgumentList();
		Entity entity = parser.parseString("(quote (add 1 2))").execute(globalScope);
		args.addArg(entity);
		assertEquals(SparseInt.valueOf(3), eval.callWithScope(args, globalScope));
	}
}
