package specialForms;

import sparser.ArgumentList;
import sparser.ArgumentReturningProcessor;
import sparser.Code;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;
import sparser.SparseList;
import sparser.Symbol;
import sparser.UserDefinedSpecialForm;

public class DefSpecial extends Function {

	public DefSpecial() {
		super("defspecial", new ArgumentReturningProcessor());
	}

	public Entity callImplementation(ArgumentList args, Scope scope) {
		Symbol name = (Symbol) args.next();
		SparseList params = (SparseList) args.next();
		Code code = createCode(args);
		UserDefinedSpecialForm userDefinedFunction = new UserDefinedSpecialForm(name.toString(), params, code);
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
