package sparser;

public class While extends SpecialForm {

	public While() {
		super("while"); 
	}

	protected Entity callImplementation(ArgumentList args, Scope scope) {
		Entity test = args.next();
		Code code = new Code();
		while(args.hasNext()) {
			code.appendEntity(args.next());
		}
		Entity returnValue = SparseNull.theNull;
		while(test.execute(scope) == SparseBoolean.True) {
			returnValue = code.execute(scope);
		}
		return returnValue;
	}

}
