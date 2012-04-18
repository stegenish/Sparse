package tests;


public class SourceTest extends SparserSourceTestCase {

	public void testBasic() throws Exception {
		run("basic.sp");
	}

	public void testDefun() throws Exception {
		run("defunTest.sp");
	}
	
	public void testDefSpecial() throws Exception {
		run("defspecialTest.sp");
	}

	public void testScope() throws Exception {
		run("scopeTests.sp");
	}

	public void testArithmetic() throws Exception {
		run("arithmetic.sp");
	}

	public void testIf() throws Exception {
		run("iftest.sp");
	}
}
