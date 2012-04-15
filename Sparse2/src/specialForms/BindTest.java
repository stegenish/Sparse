package specialForms;

import sparser.ArgumentList;
import sparser.Callable;
import sparser.SparseInt;
import sparser.SparserTestCase;
import sparser.Symbol;

public class BindTest extends SparserTestCase {

	public BindTest(String testName) {
		super(testName);
	}

	public void testBind() throws Exception {
		Callable bind = new Bind(globalScope);
		ArgumentList args = ArgumentList.createArgumentList();
		args.addArg(SparseInt.valueOf("3"));
		Symbol symbol = new Symbol("sym");
		args.addArg(symbol);
		bind.callWithScope(args, scope);
		assertEquals(SparseInt.valueOf("3"), symbol.execute(scope));
	}
}
