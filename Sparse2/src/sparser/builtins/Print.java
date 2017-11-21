package sparser.builtins;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;
import sparser.SparseNull;

public class Print extends Function {

	public Print() {
		super("print");
	}                  

	@Override
	public Entity callImplementation(ArgumentList args, Scope scope) {
		Entity next = SparseNull.theNull;
		while(args.hasNext()) {
			next = args.next();
			System.out.print(next.toString());
		}
		System.out.println();
		return next;
	}
}
