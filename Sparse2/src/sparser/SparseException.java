package sparser;

import java.util.LinkedList;
import java.util.List;

public class SparseException extends RuntimeException {

	List<String> stackTrace = new LinkedList<String>();

	public SparseException(Exception e) {
		super(e);
	}

	public SparseException(String string) {
		super(string);
	}

	public SparseException(String string, Exception e) {
		super(string, e);
	}

	public void addTrace(String trace) {
		stackTrace.add(trace);
	}

	public String getTrace() {
		StringBuilder buffer = new StringBuilder();
		for(String line: stackTrace) {
			buffer.append("  ").append(line).append('\n');
		}
		if(getCause() != null) {
			buffer.append("Caused by: ").append(getCause().toString()).append('\n');
		}
		return buffer.toString();
	}
}
