package specialForms;

import java.util.Iterator;

import sparser.ArgumentList;
import sparser.Code;
import sparser.Entity;
import sparser.Scope;
import sparser.SparseException;
import sparser.SparseList;
import sparser.SpecialForm;
import sparser.Symbol;

public class Let extends SpecialForm {

	public Let() {
		super("let");
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		SparseList bindingList = args.nextList();
		Scope letScope = createLetScope(scope, bindingList);
		Code body = createLetBody(args);
		return body.execute(letScope);
	}

	private Scope createLetScope(Scope scope, SparseList bindingList) {
		Scope letScope = scope.createShadowScope();
		for(Entity entity : bindingList) {
			if(entity instanceof SparseList) {
				SparseList binding = (SparseList) entity;
				bindToScope(scope, letScope, binding);
			} else {
				throw new SparseException(entity.createString() + " not valid binding in let");
			}
		}

		return letScope;
	}

	private void bindToScope(Scope scope, Scope letScope, SparseList binding) {
		Iterator<Entity> iterator = binding.iterator();
		Symbol symbol = (Symbol) iterator.next();
		Entity value = iterator.next();
		letScope.bind(symbol, value.execute(scope));
	}

	private Code createLetBody(ArgumentList args) {
		Code body = new Code();
		while(args.hasNext()) {
			body.appendEntity(args.next());
		}
		return body;
	}
}
