package sparser;

public class SameScopeSemantics implements ScopeSemantics {

	@Override
	public Scope createNewScope(Scope scope) {
		return scope;
	}
}
