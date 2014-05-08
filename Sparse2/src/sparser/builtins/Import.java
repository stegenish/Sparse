package sparser.builtins;

import sparser.ArgumentList;
import sparser.Code;
import sparser.Entity;
import sparser.Scope;
import sparser.SparseSourceRunner;
import sparser.SparseString;
import sparser.Sparser;
import sparser.SpecialForm;

public class Import extends SpecialForm implements Entity {

	private Sparser sparser;

	public Import() {
		super("import");
	}

	public Import(Sparser sparser) {
		this();
		this.sparser = sparser;
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		SparseString fileName = (SparseString) args.next();
		String source = SparseSourceRunner.loadFile(fileName.toString());
		Code code = sparser.parseString(source);
		return code.execute(new Scope(null, scope.GetGlobalScope(), scope));
	}

}
