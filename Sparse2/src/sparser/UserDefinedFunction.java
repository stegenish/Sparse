package sparser;

public class UserDefinedFunction extends Function {
	
	private FunctionBody functionBody;
	
	public UserDefinedFunction(String name, SparseList parameters, Code code) {
		super(name);
		functionBody = new FunctionBody(parameters, code);
	}

	@Override
	public Entity callImplementation(ArgumentList args, Scope scope) {
		return functionBody.callBody(args, scope);
	}
}
