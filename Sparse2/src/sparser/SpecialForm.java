package sparser;

public abstract class SpecialForm extends Callable {

	public SpecialForm(String name) {
		super(name, new NonArgumentEvaluatingSemantics());
	}
}
