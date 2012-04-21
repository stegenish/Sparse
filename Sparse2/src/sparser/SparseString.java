/*
 * SparseString.java
 *
 * Created on 08 August 2004, 22:44
 */

package sparser;

import static sparser.SparseBoolean.toSparseBoolean;

public class SparseString implements Entity
{
    private String string;

    public SparseString(String str) {
        string = str;
    }

    public String getString() {
        return string;
    }

    public Entity execute(Scope scope) {
        return this;
    }

    @Override
    public String toString() {
    	return getString();
    }

    @Override
    public boolean equals(Object arg) {
    	if(arg instanceof SparseString) {
    		SparseString str2 = (SparseString) arg;
    		return string.equals(str2.getString());
    	}
    	return false;
    }

    @Override
    public int hashCode() {
    	return string.hashCode();
    }

	@Override
	public String createString() {
		return "\"" + getString() + "\"";
	}

	@Override
	public SparseBoolean equal(Object other) {
		return toSparseBoolean(equals(other));
	}
}
