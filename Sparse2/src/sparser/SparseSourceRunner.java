package sparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SparseSourceRunner {

	private String source;

	public SparseSourceRunner(BufferedReader reader) {
		String fileBuffer = loadFile(reader);
		this.source = fileBuffer;
	}

	public static String loadFile(String fileName) {
		try {
			return loadFile(new BufferedReader(new FileReader(fileName)));
		} catch (FileNotFoundException e) {
			throw new SparseException(e);
		}
	}
	
	public static String loadFile(BufferedReader reader) {
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
		String fileBuffer = buffer.toString();
		return fileBuffer;
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
			e.printStackTrace();
			throw e;
		}
	}

}
