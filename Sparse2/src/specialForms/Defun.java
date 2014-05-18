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
		Callable userDefinedCallable = setupUserDefinedFunction(name, args, scope);
		scope.moduleBind(name, userDefinedCallable);
		return userDefinedCallable;
	}

	public UserDefinedFunction setupUserDefinedFunction(Symbol name, ArgumentList args, Scope scope) {
		SparseList params = args.nextList();
		Code code = createCode(args);
		UserDefinedFunction userDefinedCallable = createUserDefinedCallable(name, params, code, scope);
		return userDefinedCallable;
	}
	
	private Code createCode(ArgumentList args) {
		Code code = new Code();
		while(args.hasNext()) {
			code.appendEntity(args.next());
		}
		return code;
	}

	private UserDefinedFunction createUserDefinedCallable(Symbol name, SparseList params, Code code, Scope scope) {
		return new UserDefinedFunction(name.toString(), params, code, scope.createLexicalScope());
	}
}
