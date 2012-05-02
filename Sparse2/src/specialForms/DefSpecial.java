package specialForms;

import sparser.Callable;
import sparser.Code;
import sparser.NonArgumentEvaluatingSemantics;
import sparser.SameScopeSemantics;
import sparser.Scope;
import sparser.SparseList;
import sparser.Symbol;
import sparser.UserDefinedSpecialForm;

public class DefSpecial extends DefineUserDefinedCallable {

	public DefSpecial() {
		super("defspecial", new NonArgumentEvaluatingSemantics(), new SameScopeSemantics());
	}

	protected Callable createUserDefinedCallable(Symbol name,	SparseList params, Code code, Scope scope) {
		return new UserDefinedSpecialForm(name.toString(), params, code);
	}
}
