package sparser;

/*
 * Implement this interface to introduce a new rule for how a scope is created when 
 * calling a function.
 */
public interface ScopeSemantics {
	
	public Scope createNewScope(Scope scope);
}