package sparser;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Represents a list of arguments sent to a function.
 *
 * @author  Administrator
 */
public class ArgumentList
{
    private List<Entity> args;

    /**
     * For iterating over the arguments
     */
    public int next;
    /** Creates a new instance of ArgumentList */
    private ArgumentList()
    {
        args = new ArrayList<Entity>();
    }

    /**
     *  Use this function to create an ArgumentListInstance.
     */
    public static ArgumentList createArgumentList()
    {
    	ArgumentList argList = new ArgumentList();
        argList.next = 0;
        return argList;
    }

    /**
     * Adds an argument to the list.
     */
    public void addArg(Entity arg)
    {
        args.add(arg);
    }

    public boolean hasNext()
    {
        return next < args.size();
    }

	@SuppressWarnings("unchecked")
	public <T extends Entity> T next()
    {
        if(!hasNext())
        {
            throw new NoSuchElementException();
        }
        return (T) args.get(next++);
    }

	public void dump() {
		for(Entity arg : args) {
			System.out.print(arg.toString() + ", ");
		}
		System.out.println();
	}

	public SparseInt getIntArgument() {
		return nextCast(SparseInt.class);
	}

	public <T extends Entity> T nextCast(Class<?> clazz) {
		T arg = next();
		try {
			clazz.cast(arg);
		} catch (ClassCastException e) {
			throw new SparseException("argument " + next, e);
		}
		return arg;
	}

	public Symbol nextSymbol() {
		return nextCast(Symbol.class);
	}
}
