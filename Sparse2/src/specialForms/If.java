package specialForms;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Scope;
import sparser.SparseBoolean;
import sparser.SpecialForm;

public class If extends SpecialForm {

	public If() {
		super("if");
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
