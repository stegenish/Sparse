package specialForms;

import java.util.Iterator;

import sparser.ArgumentList;
import sparser.Code;
import sparser.Entity;
import sparser.Scope;
import sparser.SparseList;
import sparser.SparseNull;
import sparser.SpecialForm;
import sparser.Symbol;

public class Let extends SpecialForm {

	public Let() {
		super("let");
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		SparseList bindingList = (SparseList) args.next();
		Scope letScope = createLetScope(scope, bindingList);
		Code body = createLetBody(args);
		body.execute(letScope);
		return SparseNull.theNull;
	}

	private Scope createLetScope(Scope scope, SparseList bindingList) {
		Scope letScope = scope.createShadowScope();
		for(Entity binding : bindingList) {
			SparseList bindingAsList = (SparseList)binding;
			Iterator<Entity> iterator = bindingAsList.iterator();
			Symbol symbol = (Symbol) iterator.next();
			Entity value = iterator.next();
			letScope.bind(symbol, value.execute(scope));
		}
		return letScope;
	}

	private Code createLetBody(ArgumentList args) {
		Code body = new Code();
		while(args.hasNext()) {
			body.appendEntity(args.next());
		}
		return body;
	}
}
