package specialForms;

import sparser.ArgumentList;
import sparser.Code;
import sparser.Entity;
import sparser.Callable;
import sparser.NonArgumentEvaluatingSemantics;
import sparser.Scope;
import sparser.ShadowScopeSemantics;
import sparser.SparseList;
import sparser.SpecialForm;
import sparser.Symbol;
import sparser.UserDefinedSpecialForm;

public class DefSpecial extends SpecialForm {

	public DefSpecial() {
		super("defspecial");
	}

	public Entity callImplementation(ArgumentList args, Scope scope) {
		Symbol name = (Symbol) args.next();
		SparseList params = (SparseList) args.next();
		Code code = createCode(args);
		UserDefinedSpecialForm userDefinedFunction = new UserDefinedSpecialForm(name.toString(), params, code, new ShadowScopeSemantics());
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
