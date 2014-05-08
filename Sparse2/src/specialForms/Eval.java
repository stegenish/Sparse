package specialForms;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;

public class Eval extends Function {

	public Eval() {
		super("eval");
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		Entity entity = args.next();
		return entity.execute(scope);
	}
}
