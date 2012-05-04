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
					bindRestListParameter(args, localScope, parameter);
				}
			}
		}
		return code.execute(localScope);
	}

	private void bindRestListParameter(ArgumentList args, Scope localScope, Entity parameter) {
		SparseList param = (SparseList) parameter;
		param.getFirstElement();
		Symbol symbol = (Symbol) ((SparseList)param.rest()).getFirstElement();
		SparseList restList = getRestList(args);
		localScope.bind(symbol, restList);
	}

	private SparseList getRestList(ArgumentList args) {
		SparseList restList = new SparseList();
		while(args.hasNext()) {
			restList.append(args.next());
		}
		return restList;
	}

	private void bindSymbolParameter(ArgumentList args, Scope localScope, Entity parameter) {
		Symbol sym = (Symbol) parameter;
		localScope.bind(sym, args.next());
	}
}
