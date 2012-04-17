package sparser.builtins;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;
import sparser.SparseList;

public class List extends Function {

	public List() {
		super("list");
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		SparseList sparseList = new SparseList();
		while(args.hasNext()) {
			sparseList.append(args.next());
		}
		return sparseList;
	}

}
