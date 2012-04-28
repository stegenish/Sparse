package specialForms;

import sparser.ArgumentList;
import sparser.ArgumentSemantics;
import sparser.Callable;
import sparser.Code;
import sparser.Entity;
import sparser.Scope;
import sparser.ScopeSemantics;
import sparser.SparseList;
import sparser.Symbol;

public abstract class DefineUserDefinedCallable extends Callable {

	public DefineUserDefinedCallable(String name, ArgumentSemantics argumentProcessor, ScopeSemantics scopeSemantics) {
		super(name, argumentProcessor, scopeSemantics);
	}
	
	protected abstract Callable createUserDefinedCallable(Symbol name, SparseList params, Code code);
	
	public Entity callImplementation(ArgumentList args, Scope scope) {
		Symbol name = args.nextSymbol();
		SparseList params = args.nextList();
		Code code = createCode(args);
		Callable userDefinedCallable = createUserDefinedCallable(name, params, code);
		scope.bind(name, userDefinedCallable);
		return userDefinedCallable;
	}
	
	private Code createCode(ArgumentList args) {
		Code code = new Code();
		while(args.hasNext()) {
			code.appendEntity(args.next());
		}
		return code;
	}
}
