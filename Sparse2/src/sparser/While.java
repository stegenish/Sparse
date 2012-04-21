package sparser;

public class While extends SpecialForm {

	public While() {
		super("while"); 
	}

	protected Entity callImplementation(ArgumentList args, Scope scope) {
		Entity condition = args.next();
		Code code = setupBody(args);
		Entity returnValue = loop(condition, code, scope);
		return returnValue;
	}

	private Code setupBody(ArgumentList args) {
		Code code = new Code();
		while(args.hasNext()) {
			code.appendEntity(args.next());
		}
		return code;
	}

	private Entity loop(Entity test, Code body, Scope scope) {
		Entity returnValue = SparseNull.theNull;
		while(test.execute(scope) == SparseBoolean.True) {
			returnValue = body.execute(scope);
		}
		return returnValue;
	}
}
