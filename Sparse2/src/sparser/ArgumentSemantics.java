package sparser;

/*
 * Implement this interface to create a new rule for how arguments are passed to a function
 */
public interface ArgumentSemantics {

	public abstract ArgumentList processArguments(ArgumentList args, Scope scope);

}
