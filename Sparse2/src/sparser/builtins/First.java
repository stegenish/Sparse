package sparser.builtins;

import sparser.ArgumentEvaluatingProcessor;
import sparser.ArgumentList;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;
import sparser.SparseList;

public class First extends Function {

	public First() {
		super("first", new ArgumentEvaluatingProcessor());
	}

	protected Entity callImplementation(ArgumentList args, Scope scope) {
		SparseList list = (SparseList) args.next();
		return list.getFirstElement();
	}

}
