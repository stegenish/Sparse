package sparser;

public class ArgumentReturningProcessor implements ArgumentProcessor {
	
	@Override
	public ArgumentList processArguments(ArgumentList args,	Scope scope) {
		return args;
	}
}