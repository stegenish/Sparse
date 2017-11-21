package sparser.builtins;

import java.io.FileNotFoundException;
import java.io.IOException;

import sparser.ArgumentList;
import sparser.Code;
import sparser.Entity;
import sparser.Scope;
import sparser.SparseException;
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
		
		Code code;
		try {
			code = sparser.parseString(source);
		} catch (FileNotFoundException e) {
			throw new SparseException(e);
		} catch (IOException e) {
			throw new SparseException(e);
		}
		return code.execute(new Scope(null, scope.GetGlobalScope(), scope));
	}

}
