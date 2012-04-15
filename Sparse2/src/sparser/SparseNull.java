package sparser;

public class SparseNull implements Entity {

	public static SparseNull theNull = new SparseNull();

	private SparseNull() {
	}

	public Entity execute(Scope scope) {
		return this;
	}

}
