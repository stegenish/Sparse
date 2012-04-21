package sparser.builtins;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.SparseInt;
import sparser.SparseList;
import sparser.SparserTestCase;

public class FirstTest extends SparserTestCase {

	public FirstTest(String testName) {
		super(testName);
	}

	public void testSingleElementListShouldReturnOnlyItem() throws Exception {
		First first = new First();
		SparseList list = (SparseList) parseEntity("(quote (1 2 3))");
		ArgumentList args = ArgumentList.createArgumentList();
		args.addArg(list);
		Entity value = first.callWithScope(args, globalScope);
		assertEquals(SparseInt.valueOf(1), value);
	}

	public void testListInCode() throws Exception {
		Entity code = parseEntity("(first (quote (3 2 1)))");
		Entity value = code.execute(globalScope);
		assertEquals(SparseInt.valueOf(3), value);
	}
}
