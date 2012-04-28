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
public class SparseInt implements Entity
{
    private BigInteger intValue;

    public SparseInt(String s)
    {
        intValue = new BigInteger(s);
    }

    public SparseInt(BigInteger i)
    {
        intValue = i;
    }

	public BigInteger getValue()
    {
        return intValue;
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
		return this.intValue.compareTo(other.intValue);
	}
    
    public boolean equals(Object obj) {
    	if(obj instanceof SparseInt) {
    		SparseInt i = (SparseInt) obj;
    		return intValue.equals(i.getValue());
    	}
    	return false;
    }
    
    @Override
    public SparseBoolean equal(Object other) {
    	return toSparseBoolean(equals(other));
    }

    public int hashCode() {
    	return intValue.hashCode();
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

	@Override
	public String toString() {
		return intValue.toString();
	}
	
	@Override
	public String createString() {
		return toString();
	}
}
