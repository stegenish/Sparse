package sparser;

public class SparseBoolean implements Entity {

	public static final Entity False = new SparseBoolean();
	public static final Entity True = new SparseBoolean();
	
	private SparseBoolean() {
		
	}
	
	public Entity execute(Scope scope) {
		return this;
	}

	public String createString() {
		return null;
	}
}
