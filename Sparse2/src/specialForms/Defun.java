package specialForms;

import sparser.Callable;
import sparser.Code;
import sparser.NonArgumentEvaluatingSemantics;
import sparser.SameScopeSemantics;
import sparser.SparseList;
import sparser.Symbol;
import sparser.UserDefinedFunction;

public class Defun extends DefineUserDefinedCallable {

	public Defun() {
		super("defun", new NonArgumentEvaluatingSemantics(), new SameScopeSemantics());
	}

	protected Callable createUserDefinedCallable(Symbol name, SparseList params, Code code) {
		return new UserDefinedFunction(name.toString(), params, code);
	}
}
