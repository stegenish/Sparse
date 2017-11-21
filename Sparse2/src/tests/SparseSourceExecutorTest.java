package tests;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;

import sparser.SparseSourceRunner;

public class SparseSourceExecutorTest extends SparserTestCase {


    @Test
	public void test() throws Exception {
		Reader source = new StringReader("(bind 3 a)(bind 4 b)(bind 6 c)");
		BufferedReader reader = new BufferedReader(source);
		SparseSourceRunner executor = new SparseSourceRunner(reader);
		executor.run();
	}

}
