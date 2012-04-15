package specialForms;

import sparser.ArgumentList;
import sparser.ArgumentReturningProcessor;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;

public class Eval extends Function {

	public Eval() {
		super("eval", new ArgumentReturningProcessor());
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		return args.next().execute(scope);
	}
}
