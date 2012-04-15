package sparser.builtins;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;

public class Print extends Function {

	public Print() {
		super("print");
	}                  

	@Override
	public Entity callImplementation(ArgumentList args, Scope scope) {
		while(args.hasNext()) {
			System.out.println(args.next().toString());
		}
		return null;
	}

}
