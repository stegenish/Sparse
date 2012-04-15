package sparser;

public class FunctionBody {

	private Code code;
	private SparseList parameters;

	public FunctionBody(Code code, SparseList parameters) {
		this.code = code;
		this.parameters = parameters;
	}

	public Entity callBody(ArgumentList args, Scope localScope) {
		if(parameters != null) {
			for(Entity e : parameters) {
				Symbol sym = (Symbol)e;
				localScope.bind(sym, args.next());
			}
		}
		return code.execute(localScope);
	}
}
