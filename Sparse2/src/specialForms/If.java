package specialForms;

import sparser.ArgumentList;
import sparser.ArgumentReturningProcessor;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;
import sparser.SparseBoolean;

public class If extends Function {

	public If() {
		super("if", new ArgumentReturningProcessor());
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		Entity test = args.next();
		Entity expr1 = args.next();
		Entity expr2 = args.next();
		if(test.execute(scope).equals(SparseBoolean.True)) {
			return expr1.execute(scope);
		} else {
			return expr2.execute(scope);	
		}
	}
}
