package sparser;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

public class SparseSourceExecutorTest extends SparserTestCase {

	public SparseSourceExecutorTest(String testName) {
		super(testName);
		// TODO Auto-generated constructor stub
	}

	public void test() throws Exception {
		Reader source = new StringReader("(bind 3 a)(bind 4 b)(bind 6 c)");
		BufferedReader reader = new BufferedReader(source);
		SparseSourceRunner executor = new SparseSourceRunner(reader);
		executor.run(null);
	}

}
