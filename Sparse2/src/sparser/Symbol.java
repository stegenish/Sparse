/*
 * Symbol.java
 *
 * Created on 07 August 2004, 10:29
 */

package sparser;

import static sparser.SparseBoolean.toSparseBoolean;

/**
 *
 * @author  Administrator
 */
public class Symbol implements Entity
{
    private String name;

    public Symbol(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public Entity execute(Scope scope)
    {
        Entity value = scope.getBinding(this);
        if(value == null) {
        	throw new SparseException("Symbol not bound: " + name);
        }
		return value;
    }

    public String toString()
    {
        return getName();
    }

	public String createString() {
		return getName();
	}

	@Override
	public SparseBoolean equal(Object other) {
		return toSparseBoolean(this == other);
	}
}
