package sparser.repl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import sparser.Entity;
import sparser.Scope;
import sparser.Sparser;

public class SparseRepl {

	public static class ReplReader extends Reader {

		private Reader console = new InputStreamReader(System.in);
		
		@Override
		public void close() throws IOException {
			
		}

		@Override
		public int read(char[] buffer, int offset, int length) throws IOException {
			System.out.print("Sparse> ");
			return console.read(buffer, offset, length);
		}
		
		/*public boolean ready() {
			return false;
		}*/
	}
	
	public static void main(String[] args) throws IOException {
		Scope replScope = new Scope();
		Sparser sparser = new Sparser(replScope);
		sparser.setReader(new BufferedReader(new ReplReader()));
		while(true) {
			Entity result = sparser.parseNextForm(true);
			System.out.println(result.execute(replScope));
		}
	}
}
