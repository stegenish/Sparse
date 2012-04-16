package specialForms;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Scope;
import sparser.SpecialForm;

public class Eval extends SpecialForm {

	public Eval() {
		super("eval");
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		Entity entity = args.next();
		return entity.execute(scope);
	}
}
