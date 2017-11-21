package specialForms;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sparser.ArgumentList;
import sparser.Callable;
import sparser.SparseInt;
import sparser.Symbol;
import tests.SparserTestCase;

public class BindTest extends SparserTestCase {

	@Test
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
