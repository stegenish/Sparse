package specialForms;

import sparser.ArgumentList;
import sparser.ArgumentReturningProcessor;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;
import sparser.Symbol;

public class Bind extends Function {

	private Scope globalScope;

	public Bind(Scope globalScope) {
		super("bind", new ArgumentReturningProcessor());
		this.globalScope = globalScope;
	}

	public Entity callImplementation(ArgumentList args, Scope scope) {
		Entity value = args.next().execute(scope);
		Symbol symbol = (Symbol)args.next();
		globalScope.bind(symbol, value);
		return null;
	}
}
