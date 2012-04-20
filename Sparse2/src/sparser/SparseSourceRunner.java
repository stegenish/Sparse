package sparser;

import java.io.BufferedReader;
import java.io.IOException;

public class SparseSourceRunner {

	private String source;

	public SparseSourceRunner(BufferedReader reader) {
		StringBuffer buffer = new StringBuffer(1024);
		try {
			String line = reader.readLine();
			while(line != null) {
				buffer.append(line).append('\n');
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.source = buffer.toString();
	}

	public void run() {
		Scope scope = new Scope();
		Sparser parser = new Sparser(scope);
		Code code = parser.parseString(source);
		try {
			code.execute(scope);
		} catch (SparseException e) {
			System.err.println("Exception thrown and not caught:");
			System.err.println(e.getMessage());
			System.err.print(e.getTrace());
			throw e;
		}
	}

}
