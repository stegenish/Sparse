/*
 * SparseInt.java
 *
 * Created on 11 August 2004, 17:11
 */

package sparser;

import java.math.BigInteger;
import static sparser.SparseBoolean.toSparseBoolean;

/**
 * The integer type for Sparse.
 *
 * @author  Administrator
 */
public class SparseInt extends Symbol
{
    private BigInteger num;

    public SparseInt(String s)
    {
        super(s);
        num = new BigInteger(s);
    }

    public SparseInt(BigInteger i)
    {
        super(i.toString());
        num = i;
    }

	public BigInteger getValue()
    {
        return num;
    }

    public Entity execute(Scope scope)
    {
        return this;
    }

    @ExposedSparseFunction(name = "<")
    public SparseBoolean lessThan(SparseInt other) {
    	return toSparseBoolean(compareTo(other) < 0);
	}
    
    @ExposedSparseFunction(name = ">")
    public SparseBoolean greaterThan(SparseInt other) {
    	return toSparseBoolean(compareTo(other) > 0);
	}

    @ExposedSparseFunction(name = "=")
    public SparseBoolean equal(SparseInt other) {
    	return toSparseBoolean(compareTo(other) == 0);
	}
    
	private int compareTo(SparseInt other) {
		return this.num.compareTo(other.num);
	}
    
    public boolean equals(Object obj) {
    	if(obj instanceof SparseInt) {
    		SparseInt i = (SparseInt) obj;
    		return num.equals(i.getValue());
    	}
    	return false;
    }

    public int hashCode() {
    	return num.hashCode();
    }

	public static SparseInt valueOf(int i) {
		return new SparseInt("" + i);
	}

	public static SparseInt valueOf(String string) {
		return valueOf(Integer.valueOf(string));
	}

	public static SparseInt valueOf(BigInteger bigInteger) {
		return new SparseInt(bigInteger);
	}
}
