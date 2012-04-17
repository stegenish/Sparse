package sparser.builtins;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;
import sparser.SparseList;

public class Concat extends Function {

	public Concat() {
		super("concat");
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		SparseList list1 = (SparseList) args.next();
		SparseList list2 = (SparseList) args.next();
		list1.addLast(list2);
		return list1;
	}

}
