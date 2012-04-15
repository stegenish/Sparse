package sparser.builtins;

import java.util.ArrayList;
import java.util.List;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;

public class TestBuiltin extends Function {

	public TestBuiltin() {
		super("TestBuiltin");
	}

	public ArgumentList lastCallArgs = null;
	public List<ArgumentList> callHistory = new ArrayList<ArgumentList>();

	protected Entity callImplementation(ArgumentList args, Scope scope) {
		this.lastCallArgs = args;
		callHistory.add(args);
		return this;
	}
}
