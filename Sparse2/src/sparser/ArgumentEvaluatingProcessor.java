package sparser;

public class ArgumentEvaluatingProcessor implements ArgumentProcessor {
	
	public ArgumentList processArguments(ArgumentList args,	Scope scope) {
		ArgumentList evaluatedArguments = ArgumentList.createArgumentList();
		while (args.hasNext()) {
			evaluatedArguments.addArg(args.next().execute(scope));
		}
		return evaluatedArguments;
	}
}