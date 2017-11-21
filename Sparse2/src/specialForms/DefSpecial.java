package specialForms;

import sparser.ArgumentList;
import sparser.Code;
import sparser.Entity;
import sparser.Scope;
import sparser.SparseList;
import sparser.SpecialForm;
import sparser.Symbol;
import sparser.UserDefinedSpecialForm;

public class DefSpecial extends SpecialForm {

	public DefSpecial() {
		super("defspecial");
	}

	public Entity callImplementation(ArgumentList args, Scope scope) {
		Symbol name = args.next();
		SparseList params = args.next();
		Code code = createCode(args);
		UserDefinedSpecialForm userDefinedSpecialForm = 
				new UserDefinedSpecialForm(name.toString(), params, code);
		scope.moduleBind(name, userDefinedSpecialForm);
		return userDefinedSpecialForm;
	}

	private Code createCode(ArgumentList args) {
		Code code = new Code();
		while(args.hasNext()) {
			code.appendEntity(args.next());
		}
		return code;
	}
}