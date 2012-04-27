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

	public Entity next()
    {
        if(!hasNext())
        {
            throw new NoSuchElementException();
        }
        return args.get(next++);
    }

	public void dump() {
		for(Entity arg : args) {
			System.out.print(arg.toString() + ", ");
		}
		System.out.println();
	}

	public SparseInt getIntArgument() {
		try {
			return (SparseInt) next();
		} catch (ClassCastException e) {
			throw new SparseException("argument " + next, e);
		}
	}
}
