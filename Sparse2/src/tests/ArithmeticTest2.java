package tests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import junit.framework.TestCase;
import sparser.SparseSourceRunner;

public class ArithmeticTest2 extends TestCase {

	public void testArithmetic() throws Exception {
		run("arithmetic2.sp");
	}
	
	private void run(String string) throws FileNotFoundException {
		String filePath = "testSources/" + string;
		
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		SparseSourceRunner runner = new SparseSourceRunner(reader);
		runner.run();
	}
}
