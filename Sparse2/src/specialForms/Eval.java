package specialForms;

import sparser.ArgumentEvaluatingSemantics;
import sparser.ArgumentList;
import sparser.Callable;
import sparser.Entity;
import sparser.SameScopeSemantics;
import sparser.Scope;

public class Eval extends Callable {

	public Eval() {
		super("eval", new ArgumentEvaluatingSemantics(), new SameScopeSemantics());
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		Entity entity = args.next();
		return entity.execute(scope);
	}
}
