package sparser;

public class UserDefinedFunction extends Function {
	
	private FunctionBody functionBody;
	
	public UserDefinedFunction(String name, SparseList parameters, Code code, FunctionScopeSemantics scopeSemantics) {
		super(name);
		functionBody = new FunctionBody(code, parameters);
		this.scopeSemantics = scopeSemantics;
	}

	@Override
	public Entity callImplementation(ArgumentList args, Scope scope) {
		Scope localScope = scopeSemantics.createNewScope(scope);
		return functionBody.callBody(args, localScope);
	}

	public String getName() {
		return null;
	}
}
