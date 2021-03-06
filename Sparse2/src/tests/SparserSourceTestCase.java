package tests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import sparser.SparseSourceRunner;

public abstract class SparserSourceTestCase {

	protected void run(String string) throws FileNotFoundException {
		String filePath = "testSources/" + string;
		
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		SparseSourceRunner runner = new SparseSourceRunner(reader);
		try {
			runner.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
