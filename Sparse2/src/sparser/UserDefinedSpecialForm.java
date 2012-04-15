package sparser;

public class UserDefinedSpecialForm extends SpecialForm {
	
	private FunctionBody functionBody;

	public UserDefinedSpecialForm(String name, SparseList parameters, Code code, ShadowScopeSemantics scopeSemantics) {
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
