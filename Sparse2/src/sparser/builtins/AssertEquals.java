package sparser.builtins;

import sparser.ArgumentEvaluatingProcessor;
import sparser.ArgumentList;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;
import sparser.SparseString;

public class AssertEquals extends Function {

	public AssertEquals() {
		super("assert", new ArgumentEvaluatingProcessor());
	}

	public Entity callImplementation(ArgumentList args, Scope scope) {
		Entity expected = args.next();
		Entity actual = args.next();
		Entity desc = new SparseString("");
		if(args.hasNext()) {
			desc = args.next();
		}
		if(expected.equals(actual)) {
			return expected;
		}
		throw new AssertException("Expected " + expected.toString() + ", was " +actual.toString() + ", msg=" + desc);
	}
}
