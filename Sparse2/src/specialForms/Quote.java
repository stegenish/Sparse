package specialForms;

import sparser.ArgumentList;
import sparser.ArgumentReturningProcessor;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;

public class Quote extends Function {

	public Quote() {
		super("quote", new ArgumentReturningProcessor());
	}

	protected Entity callImplementation(ArgumentList args, Scope scope) {
		return args.next();
	}
}
