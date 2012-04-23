package sparser;

import static sparser.SparseBoolean.toSparseBoolean;

public class SparseNull implements Entity {

	public static SparseNull theNull = new SparseNull();

	private SparseNull() {
	}

	public Entity execute(Scope scope) {
		return this;
	}
	
	@Override
	public String toString() {
		return "null";
	}

	public String createString() {
		return "null";
	}

	@Override
	public SparseBoolean equal(Object other) {
		return toSparseBoolean(other == theNull);
	}

}
