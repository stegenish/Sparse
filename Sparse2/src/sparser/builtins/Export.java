package sparser.builtins;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Scope;
import sparser.SpecialForm;
import sparser.Symbol;

public class Export extends SpecialForm{

	public Export() {
		super("export");
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		Symbol symbol = args.next();
		return scope.export(symbol);
	}

}
