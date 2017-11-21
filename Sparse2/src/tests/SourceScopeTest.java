package tests;

import org.junit.Test;

public class SourceScopeTest extends SparserSourceTestCase {

	@Test
	public void testScopeTests() throws Exception {
		run("scopeTests/scopeTest.sp");
	}

	@Test
	public void testSpecialScope() throws Exception {
		run("scopeTests/defspecialScopeTestImport.sp");
	}
}
