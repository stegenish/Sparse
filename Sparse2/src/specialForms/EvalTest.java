package specialForms;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sparser.ArgumentList;
import sparser.Callable;
import sparser.Entity;
import sparser.SparseInt;
import tests.SparserTestCase;

public class EvalTest extends SparserTestCase {

	@Test
	public void testEvalReturnExecuteValueOfArgument() throws Exception {
		Callable eval = new Eval();
		ArgumentList args = ArgumentList.createArgumentList();
		Entity entity = parser.parseString("(quote (add 1 2))").execute(globalScope);
		args.addArg(entity);
		assertEquals(SparseInt.valueOf(3), eval.callWithScope(args, globalScope));
	}
}
