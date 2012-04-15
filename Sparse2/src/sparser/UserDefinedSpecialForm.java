package sparser;

public class UserDefinedSpecialForm extends SpecialForm {
	
	private FunctionBody functionBody;

	public UserDefinedSpecialForm(String name, SparseList parameters, Code code) {
		super(name);
		functionBody = new FunctionBody(code, parameters);
	}

	@Override
	public Entity callImplementation(ArgumentList args, Scope scope) {
		//Scope localScope = scopeSemantics.createNewScope(scope);
		return functionBody.callBody(args, scope);
	}

	public String getName() {
		return null;
	}
}
