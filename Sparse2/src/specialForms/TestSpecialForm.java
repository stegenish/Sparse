package specialForms;

import java.util.ArrayList;
import java.util.List;

import sparser.ArgumentList;
import sparser.ArgumentReturningProcessor;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;

public class TestSpecialForm extends Function {

	public TestSpecialForm() {
		super("TestSpecialForm", new ArgumentReturningProcessor());
	}

	public ArgumentList lastCallArgs = null;
	public List<ArgumentList> callHistory = new ArrayList<ArgumentList>();

	public Entity callImplementation(ArgumentList args, Scope scope) {
		this.lastCallArgs = args;
		callHistory.add(args);
		return this;
	}
}
