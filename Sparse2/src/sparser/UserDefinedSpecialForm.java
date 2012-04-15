package sparser;

public class UserDefinedSpecialForm extends Function {
	private FunctionBody functionBody;

	public UserDefinedSpecialForm(String name, SparseList parameters, Code code) {
		super(name, new ArgumentReturningProcessor());		
		functionBody = new FunctionBody(code, parameters);
	}

	@Override
	public Entity callImplementation(ArgumentList args, Scope scope) {
		Scope localScope = scope.createShadowScope();
		return functionBody.callBody(args, localScope);
	}

	public String getName() {
		return null;
	}
}
