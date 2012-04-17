package sparser;

public class FunctionBody {

	private SparseList parameters;
	private Code code;

	public FunctionBody(SparseList parameters, Code code) {
		this.parameters = parameters;
		this.code = code;
	}

	public Entity callBody(ArgumentList args, Scope localScope) {
		if (parameters != null) {
			for (Entity parameter : parameters) {
				if (parameter instanceof Symbol) {
					bindSymbolParameter(args, localScope, parameter);
				} else {
					SparseList param = (SparseList) parameter;
					param.getFirstElement();
					Symbol symbol = (Symbol) param.getNext().getFirstElement();
					SparseList restList = new SparseList();
					while(args.hasNext()) {
						restList.append(args.next());
					}
					localScope.bind(symbol, restList);
				}
			}
		}
		return code.execute(localScope);
	}

	private void bindSymbolParameter(ArgumentList args, Scope localScope,
			Entity parameter) {
		Symbol sym = (Symbol) parameter;
		localScope.bind(sym, args.next());
	}
}
