package specialForms;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Scope;
import sparser.SpecialForm;
import sparser.Symbol;
import sparser.UserDefinedFunction;
import sparser.UserDefinedMacro;



public class Defmacro extends SpecialForm {

	private Defun functionDefiner = new Defun();
	
	public Defmacro() {
		super("defmacro");
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		Symbol name = args.nextSymbol();
		UserDefinedFunction function = functionDefiner.setupUserDefinedFunction(name, args, scope);
		UserDefinedMacro macro = new UserDefinedMacro(function);
		scope.moduleBind(name, macro);
		return macro;
	}
}
