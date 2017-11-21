package sparser;

public class UserDefinedFunction extends Function {
	
	private FunctionBody functionBody;
	private Scope lexicalScope;
	
	public UserDefinedFunction(String name, SparseList parameters, Code code, Scope scope) {
		super(name);
		functionBody = new FunctionBody(parameters, code);
		this.lexicalScope = scope;
	}

	@Override
	public Entity callImplementation(ArgumentList args, Scope scope) {
		return functionBody.callBody(args, lexicalScope);
	}
}
