package sparser;

public class ArgumentEvaluatingSemantics implements ArgumentSemantics {
	
	public ArgumentList processArguments(ArgumentList args,	Scope scope) {
		ArgumentList evaluatedArguments = ArgumentList.createArgumentList();
		while (args.hasNext()) {
			evaluatedArguments.addArg(args.next().execute(scope));
		}
		return evaluatedArguments;
	}
}