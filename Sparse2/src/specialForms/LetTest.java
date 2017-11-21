package specialForms;

import org.junit.Test;

import tests.SparserTestCase;

public class LetTest extends SparserTestCase {

	@Test
	public void testFirstArgumentMustBeString() throws Exception {
		checkIncorrectArgument("(let a 1)", "argument 1");
	}

	@Test
	public void testEachBindingMustBeList() throws Exception {
		checkIncorrectArgument("(let (a 1) 1)", "a not valid binding in let");
	}

	@Test
	public void testEachBindingMustBeList2() throws Exception {
		checkIncorrectArgument("(let (\"asd\" 1) 1)", "\"asd\" not valid binding in let");
	}
}
