package sparser;

import static sparser.SparseBoolean.toSparseBoolean;

/**
 *
 * @author  Administrator
 */
public abstract class Callable implements Entity
{
	private String name;
	private ArgumentSemantics argumentProcessor;
	protected ScopeSemantics scopeSemantics;

	public Callable(String name, ArgumentSemantics argumentProcessor, ScopeSemantics scopeSemantics) {
		this.name = name;
		this.argumentProcessor = argumentProcessor;
		this.scopeSemantics = scopeSemantics;
	}

    public Entity execute(Scope scope) {
        return this;
    }

    public Entity callWithScope(ArgumentList args, Scope scope) {
    	try {
    		return call(args, scope);
    	} catch(SparseException e) {
    		e.addTrace(name);
    		throw e;
    	} catch(Exception e) {
    		SparseException se = new SparseException(e);
    		se.addTrace(name);
    		throw se;
    	}
    }

	private Entity call(ArgumentList args, Scope scope) {
		ArgumentList processedArguments = argumentProcessor.processArguments(args, scope);
		Scope newScope = scopeSemantics.createNewScope(scope);
		return callImplementation(processedArguments, newScope);
	}

    protected abstract Entity callImplementation(ArgumentList args, Scope scope);

	public String createString() {
		return null;
	}
	
	@Override
	public SparseBoolean equal(Object other) {
		return toSparseBoolean(this == other);
	}
}
