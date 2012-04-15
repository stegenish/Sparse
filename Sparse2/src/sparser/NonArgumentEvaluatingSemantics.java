package sparser;

public class NonArgumentEvaluatingSemantics implements ArgumentSemantics {
	
	@Override
	public ArgumentList processArguments(ArgumentList args,	Scope scope) {
		return args;
	}
}