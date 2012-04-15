package specialForms;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Scope;
import sparser.SpecialForm;

public class Quote extends SpecialForm {

	public Quote() {
		super("quote");
	}

	protected Entity callImplementation(ArgumentList args, Scope scope) {
		return args.next();
	}
}
