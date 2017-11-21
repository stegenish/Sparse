package tests;

import org.junit.Test;

public class DefMacroTest extends SparserSourceTestCase {
	
	@Test
	public void testDefmacro() throws Exception {
		run("defmacroTest.sp");
	}
	
	@Test
	public void testDefmacro2() throws Exception {
		run("defmacroTest2.sp");
	}
}
