package sparser;

public class SparseBoolean implements Entity {

	public static final SparseBoolean False = new SparseBoolean();
	public static final SparseBoolean True = new SparseBoolean();
	
	private SparseBoolean() {
		
	}
	
	public Entity execute(Scope scope) {
		return this;
	}

	public String createString() {
		return null;
	}
	
	public static SparseBoolean toSparseBoolean(boolean b) {
		return b ? True : False;
	}
}
