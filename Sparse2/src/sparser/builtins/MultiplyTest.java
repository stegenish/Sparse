package sparser.builtins;

import java.math.BigInteger;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Function;
import sparser.SparseInt;
import sparser.SparserTestCase;

public class MultiplyTest extends SparserTestCase {

	public MultiplyTest(String testName) {
		super(testName);
	}

	public void testOneNumberOne() {
		Function fun = new Multiply();
		ArgumentList args = ArgumentList.createArgumentList();
		args.addArg(SparseInt.valueOf(1));
		Entity call = fun.callWithScope(args, scope);
		assertEquals(new BigInteger("1"), ((SparseInt)call).getValue());
	}

	public void testOneNumberOther() {
		Function fun = new Multiply();
		ArgumentList args = ArgumentList.createArgumentList();
		args.addArg(SparseInt.valueOf(2));
		Entity call = fun.callWithScope(args, scope);
		assertEquals(new BigInteger("2"), ((SparseInt)call).getValue());
	}

	public void testTwoNumbers() {
		Function fun = new Multiply();
		ArgumentList args = ArgumentList.createArgumentList();
		args.addArg(SparseInt.valueOf(2));
		args.addArg(SparseInt.valueOf(3));
		args.addArg(SparseInt.valueOf(4));
		Entity call = fun.callWithScope(args, scope);
		assertEquals(new BigInteger("24"), ((SparseInt)call).getValue());
	}
}
