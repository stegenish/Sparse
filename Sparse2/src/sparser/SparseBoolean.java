package sparser;

public class SparseBoolean implements Entity {

	public static final SparseBoolean False = new SparseBoolean("false");
	public static final SparseBoolean True = new SparseBoolean("true");
	
	private String description;
	
	private SparseBoolean(String description) {
		this.description = description;
	}
	
	public Entity execute(Scope scope) {
		return this;
	}

	@Override
	public String toString() {
		return description;
	}
	
	public String createString() {
		return toString();
	}
	
	public static SparseBoolean toSparseBoolean(boolean b) {
		return b ? True : False;
	}

	@Override
	public SparseBoolean equal(Object other) {
		return toSparseBoolean(this == other);
	}
	
	@ExposedSparseFunction(name = "not")
	public SparseBoolean not() {
		return this == True ? SparseBoolean.False : SparseBoolean.True;
	}
}
