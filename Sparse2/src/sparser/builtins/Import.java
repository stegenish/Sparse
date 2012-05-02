package sparser.builtins;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Scope;
import sparser.SparseSourceRunner;
import sparser.SparseString;
import sparser.SpecialForm;

public class Import extends SpecialForm implements Entity {

	public Import() {
		super("import");
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		SparseString fileName = (SparseString) args.next();
		SparseSourceRunner sparseSourceRunner = new SparseSourceRunner(fileName.toString());
		ExportedBindings exportedBindings = new ExportedBindings();
		Entity runResult = sparseSourceRunner.run(exportedBindings);
		exportedBindings.export(scope);
		return runResult;
	}

}
