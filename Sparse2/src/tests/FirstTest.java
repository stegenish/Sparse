package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.SparseInt;
import sparser.SparseList;
import sparser.builtins.First;

public class FirstTest extends SparserTestCase {

	@Test
	public void testSingleElementListShouldReturnOnlyItem() throws Exception {
		First first = new First();
		SparseList list = (SparseList) parseEntity("(quote (1 2 3))");
		ArgumentList args = ArgumentList.createArgumentList();
		args.addArg(list);
		Entity value = first.callWithScope(args, globalScope);
		assertEquals(SparseInt.valueOf(1), value);
	}
	
	@Test
	public void testListInCode() throws Exception {
		Entity value = executeString("(first (quote (3 2 1)))");
		assertEquals(SparseInt.valueOf(3), value);
	}
	
	@Test
	public void testArgumentMustBeList() throws Exception {
		checkIncorrectArgument("(first \"asd\")", "argument 1");
	}
}
