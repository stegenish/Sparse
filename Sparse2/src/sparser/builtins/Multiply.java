package sparser.builtins;

import java.math.BigInteger;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;
import sparser.SparseInt;

public class Multiply extends Function {

	public Multiply() {
		super("multiply");
	}

	public Entity callImplementation(ArgumentList args, Scope scope) {
		BigInteger result = new BigInteger("1");
		while(args.hasNext()) {
			Entity next = args.next();
			result = result.multiply(((SparseInt)next).getValue());
		}
		return SparseInt.valueOf(result);
	}
}
