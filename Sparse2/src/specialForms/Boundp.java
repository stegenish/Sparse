package specialForms;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Scope;
import sparser.SpecialForm;
import sparser.Symbol;

public class Boundp extends SpecialForm {

	public Boundp() {
		super("isbound");
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		Symbol symbol = args.nextSymbol();
		return scope.isBound(symbol);
	}

}
