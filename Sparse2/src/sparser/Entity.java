package sparser;

public interface Entity
{
    public Entity execute(Scope scope);

	public String createString();
	
	@ExposedSparseFunction(name = "equal")
	public SparseBoolean equal(Object other);
}
