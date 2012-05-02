package specialForms;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Scope;
import sparser.SpecialForm;
import sparser.Symbol;
import sparser.builtins.ExportedBindings;

public class Export extends SpecialForm implements Entity {

	private ExportedBindings exports;

	public Export(ExportedBindings exports) {
		super("export");
		this.exports = exports;
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		Symbol symbol = args.nextSymbol();
		Entity value = scope.getBinding(symbol);
		exports.addExport(symbol, value);
		return value;
	}

}
