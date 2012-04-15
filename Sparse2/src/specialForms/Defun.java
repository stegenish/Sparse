package specialForms;

import sparser.ArgumentList;
import sparser.Code;
import sparser.Entity;
import sparser.Callable;
import sparser.FunctionScopeSemantics;
import sparser.Scope;
import sparser.SparseList;
import sparser.SpecialForm;
import sparser.Symbol;
import sparser.UserDefinedFunction;

public class Defun extends SpecialForm {

	public Defun() {
		super("defun");
	}

	public Entity callImplementation(ArgumentList args, Scope scope) {
		Symbol name = (Symbol) args.next();
		SparseList params = (SparseList) args.next();
		Code code = createCode(args);
		Callable userDefinedFunction = new UserDefinedFunction(name.toString(), params, code, new FunctionScopeSemantics());
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
