package specialForms;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Scope;
import sparser.SpecialForm;
import sparser.Symbol;

public class Set extends SpecialForm {

	public Set() {
		super("set");
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		Symbol symbol = (Symbol)args.next();
		Entity value = args.next().execute(scope);
		return scope.set(symbol, value);
	}

}
