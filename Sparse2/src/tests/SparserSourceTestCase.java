package tests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import sparser.SparseSourceRunner;
import junit.framework.TestCase;

public abstract class SparserSourceTestCase extends TestCase {

	protected void run(String string) throws FileNotFoundException {
		String filePath = "testSources/" + string;
		
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		SparseSourceRunner runner = new SparseSourceRunner(reader);
		runner.run();
	}

}
