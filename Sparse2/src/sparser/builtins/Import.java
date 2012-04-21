package sparser.builtins;

import sparser.ArgumentList;
import sparser.Code;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;
import sparser.SparseSourceRunner;
import sparser.SparseString;
import sparser.Sparser;

public class Import extends Function implements Entity {

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
		return code.execute(scope);
	}

}
