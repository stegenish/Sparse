package tests;

import org.junit.Test;

public class WhileSourceTest extends SparserSourceTestCase {

	@Test
	public void testBasic() throws Exception {
		run("whileTest.sp");
	}
}
