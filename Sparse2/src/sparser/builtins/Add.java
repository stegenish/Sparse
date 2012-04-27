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
public class Add extends Function
{
    public Add() {
    	super("add");
    }

    public Entity callImplementation(ArgumentList args, Scope scope)
    {
        BigInteger result = new BigInteger("0");
        while(args.hasNext())
        {
            SparseInt sparseValue = args.getIntArgument();
			BigInteger intValue = sparseValue.getValue();
			result = result.add(intValue);
        }
        return SparseInt.valueOf(result);
    }
}
