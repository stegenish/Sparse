package sparser;

/**
 *
 * @author  Administrator
 */
public abstract class Function implements Entity
{
	private String name;
	private ArgumentProcessor argumentProcessor;

	public Function(String name, ArgumentProcessor argumentProcessor) {
		this.name = name;
		this.argumentProcessor = argumentProcessor;
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
		return callImplementation(processedArguments, scope);
	}

    protected abstract Entity callImplementation(ArgumentList args, Scope scope);
}
