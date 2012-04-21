package specialForms;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Scope;
import sparser.SpecialForm;
import sparser.Symbol;

public class Bind extends SpecialForm {

	private Scope globalScope;

	public Bind(Scope globalScope) {
		super("bind");
		this.globalScope = globalScope;
	}

	public Entity callImplementation(ArgumentList args, Scope scope) {
		Entity value = args.next().execute(scope);
		Symbol symbol = (Symbol)args.next();
		globalScope.bind(symbol, value);
		return null;
	}
}
