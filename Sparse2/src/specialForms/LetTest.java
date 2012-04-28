package specialForms;

import sparser.SparserTestCase;

public class LetTest extends SparserTestCase {

	public LetTest(String testName) {
		super(testName);
	}

	public void testFirstArgumentMustBeString() throws Exception {
		checkIncorrectArgument("(let a 1)", "argument 1");
	}
	
	public void testEachBindingMustBeList() throws Exception {
		checkIncorrectArgument("(let (a 1) 1)", "a not valid binding in let");
	}
	
	public void testEachBindingMustBeList2() throws Exception {
		checkIncorrectArgument("(let (\"asd\" 1) 1)", "\"asd\" not valid binding in let");
	}
}
