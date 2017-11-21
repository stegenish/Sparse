package sparser;

public class UserDefinedMacro extends Callable {

	private UserDefinedFunction function;

	public UserDefinedMacro(UserDefinedFunction macroFunction) {
		super(macroFunction.getName(), new NonArgumentEvaluatingSemantics());
		this.function = macroFunction;
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		Entity result = function.callImplementation(args, scope);
		return result.execute(scope);
	}
}
