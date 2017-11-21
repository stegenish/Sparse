package tests;
import org.junit.Test;

public class SourceTest extends SparserSourceTestCase {

	@Test
	public void testBasic() throws Exception {
		run("basic.sp");
	}

	@Test
	public void testDefun() throws Exception {
		run("defunTest.sp");
	}
	
	@Test
	public void testDefSpecial() throws Exception {
		run("defspecialTest.sp");
	}

	@Test
	public void testScope() throws Exception {
		run("scopeTests.sp");
	}

	@Test
	public void testArithmetic() throws Exception {
		run("arithmetic.sp");
	}

	@Test
	public void testIf() throws Exception {
		run("iftest.sp");
	}
	
	@Test
	public void testImport() throws Exception {
		run("importTest.sp");
	}
	
	@Test
	public void testSet() throws Exception {
		run("setTest.sp");
	}
	
	@Test
	public void testClosure() throws Exception {
		run("closureTest.sp");
	}
	
	@Test
	public void testComment() throws Exception {
		run("commentTest.sp");
	}
}
