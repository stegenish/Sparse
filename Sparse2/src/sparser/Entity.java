package sparser;

public interface Entity
{
    public Entity execute(Scope scope);

	public abstract String createString();
}
