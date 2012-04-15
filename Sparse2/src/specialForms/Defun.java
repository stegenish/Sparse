package specialForms;

import sparser.ArgumentList;
import sparser.Callable;
import sparser.Code;
import sparser.Entity;
import sparser.NonArgumentEvaluatingSemantics;
import sparser.SameScopeSemantics;
import sparser.Scope;
import sparser.SparseList;
import sparser.Symbol;
import sparser.UserDefinedFunction;

public class Defun extends Callable {

	public Defun() {
		super("defun", new NonArgumentEvaluatingSemantics(), new SameScopeSemantics());
	}

	public Entity callImplementation(ArgumentList args, Scope scope) {
		Symbol name = (Symbol) args.next();
		SparseList params = (SparseList) args.next();
		Code code = createCode(args);
		Callable userDefinedFunction = new UserDefinedFunction(name.toString(), params, code);
		scope.bind(name, userDefinedFunction);
		return userDefinedFunction;
	}

	private Code createCode(ArgumentList args) {
		Code code = new Code();
		while(args.hasNext()) {
			code.appendEntity(args.next());
		}
		return code;
	}
}
