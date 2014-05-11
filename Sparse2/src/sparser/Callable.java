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

	public Callable(String name, ArgumentSemantics argumentProcessor) {
		this.name = name;
		this.argumentProcessor = argumentProcessor;;
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
		Entity returnValue = callImplementation(processedArguments, scope);
		return returnValue;
	}

    protected abstract Entity callImplementation(ArgumentList args, Scope scope);

	public String createString() {
		return null;
	}
	
	@Override
	public SparseBoolean equal(Object other) {
		return toSparseBoolean(this == other);
	}

	public String getName() {
		return name;
	}
}
