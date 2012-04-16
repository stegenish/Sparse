package sparser;

public class UserDefinedSpecialForm extends SpecialForm {
	
	private FunctionBody functionBody;

	public UserDefinedSpecialForm(String name, SparseList parameters, Code code) {
		super(name);
		functionBody = new FunctionBody(parameters, code);
	}

	@Override
	public Entity callImplementation(ArgumentList args, Scope scope) {
		return functionBody.callBody(args, scope);
	}
}
