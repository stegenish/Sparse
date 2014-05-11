package specialForms;

import sparser.ArgumentList;
import sparser.Callable;
import sparser.Code;
import sparser.Entity;
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
		Symbol name = args.nextSymbol();
		SparseList params = args.nextList();
		Code code = createCode(args);
		Callable userDefinedCallable = createUserDefinedCallable(name, params, code, scope);
		scope.moduleBind(name, userDefinedCallable);
		return userDefinedCallable;
	}
	
	private Code createCode(ArgumentList args) {
		Code code = new Code();
		while(args.hasNext()) {
			code.appendEntity(args.next());
		}
		return code;
	}

	protected Callable createUserDefinedCallable(Symbol name, SparseList params, Code code, Scope scope) {
		return new UserDefinedFunction(name.toString(), params, code, scope.createLexicalScope());
	}
}
