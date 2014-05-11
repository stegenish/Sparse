package specialForms;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Scope;
import sparser.SparseException;
import sparser.SparseNull;
import sparser.SpecialForm;
import sparser.builtins.AssertException;

public class AssertThrowsException extends SpecialForm {

	public AssertThrowsException() {
		super("assert-throws-exception");
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		Entity asd = args.next();
		SparseException caughtException = null;
		
		try {
			asd.execute(scope);
		} catch (SparseException e) {
			caughtException = e;
		}
		
		if (caughtException == null) {
			throw new AssertException(String.format("Should have thrown exception: %s", asd.toString()));
		}
		
		return SparseNull.theNull;
	}

}
