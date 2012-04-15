package specialForms;

import java.util.ArrayList;
import java.util.List;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Scope;
import sparser.SpecialForm;

public class TestSpecialForm extends SpecialForm {

	public TestSpecialForm() {
		super("TestSpecialForm");
	}

	public ArgumentList lastCallArgs = null;
	public List<ArgumentList> callHistory = new ArrayList<ArgumentList>();

	public Entity callImplementation(ArgumentList args, Scope scope) {
		this.lastCallArgs = args;
		callHistory.add(args);
		return this;
	}
}
