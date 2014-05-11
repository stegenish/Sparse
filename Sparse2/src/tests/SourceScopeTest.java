package tests;


public class SourceScopeTest extends SparserSourceTestCase {

	public void testScopeTests() throws Exception {
		run("scopeTests/scopeTest.sp");
	}
	
	public void testSpecialScope() throws Exception {
		run("scopeTests/defspecialScopeTestImport.sp");
	}
}
