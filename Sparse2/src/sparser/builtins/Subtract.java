/*
 * Add.java
 *
 * Created on 11 August 2004, 19:06
 */

package sparser.builtins;

import java.math.BigInteger;

import sparser.ArgumentList;
import sparser.Entity;
import sparser.Function;
import sparser.Scope;
import sparser.SparseInt;

/**
 *
 * @author  Administrator
 */
public class Subtract extends Function
{
	public Subtract() {
    	super("add");
    }

    public Entity callImplementation(ArgumentList args, Scope scope)
    {
        BigInteger result = args.nextInt().getValue();
        while(args.hasNext())
        {
            SparseInt sparseValue = args.nextInt();
			BigInteger intValue = sparseValue.getValue();
			result = result.subtract(intValue);
        }
        return SparseInt.valueOf(result);
    }
}
