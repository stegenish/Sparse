package sparser;

public class UserDefinedFunction extends Function {
	private FunctionBody functionBody;

	public UserDefinedFunction(String name, SparseList parameters, Code code) {
		super(name, new ArgumentEvaluatingProcessor());
		functionBody = new FunctionBody(code, parameters);
	}

	@Override
	public Entity callImplementation(ArgumentList args, Scope scope) {
		Scope localScope = createNewScope(scope);
		return functionBody.callBody(args, localScope);
	}

	private static Scope createNewScope(Scope scope) {
		return scope.createFunctionScope();
	}

	public String getName() {
		return null;
	}
}
