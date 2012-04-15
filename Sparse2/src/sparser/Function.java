package sparser;

public abstract class Function extends Callable {

	public Function(String name) {
		super(name, new ArgumentEvaluatingSemantics(), new FunctionScopeSemantics());
	}
}
