package sparser.builtins;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;

public class Quit extends Function {

	public Quit() {
		super("quit");
	}

	@Override
	protected Entity callImplementation(ArgumentList args, Scope scope) {
		System.exit(0);
		return null;
	}

}
