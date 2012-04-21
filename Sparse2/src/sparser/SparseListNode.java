package sparser;

import java.util.Iterator;

import static sparser.SparseBoolean.toSparseBoolean;

public class SparseListNode implements Entity, Iterable<Entity>
{
    protected Entity element;

    protected SparseListNode next;
    
    public SparseListNode(Entity elem)
    {
        next = null;
        element = elem;
    }

    public SparseListNode getNext()
    {
        return next;
    }

    public void setNext(SparseListNode newNext)
    {
        next = newNext;
    }

    public SparseListNode getLast()
    {
        SparseListNode tmp = this;
        while(tmp.getNext() != null)
        {
            tmp = tmp.getNext();
        }
        return tmp;
    }

    public void insertEnd(SparseListNode list)
    {
        getLast().setNext(list);
    }

    /**
     * Adds list to the front of this list. If list holds more than
     * one element, the last element of list will be made to point to
     * the first element in this list.
     * Note that list1.addFront(list2) is the same as list2.addLast(list1).
     * Running time is the same in both cases.
     */
    public void insertFront(SparseListNode list)
    {
        list.getLast().setNext(this);
    }

    public Entity getElement()
    {
        return element;
    }

    public void setElement(Entity elem)
    {
        element = elem;
    }

    public void append(Entity elem) {
    	if(next != null) {
    		next.append(elem);
    	} else {
    		next = new SparseListNode(elem);
    	}
    }

    public Entity execute(Scope scope) {
        Callable fun = getFunction(scope);
        ArgumentList args = createArgumentList();
        return fun.callWithScope(args, scope);
    }

	private ArgumentList createArgumentList() {
		ArgumentList args = ArgumentList.createArgumentList();
		SparseListNode listNode = getNext();
		while(listNode != null) {
			args.addArg(listNode.getElement());
		    listNode = listNode.getNext();
		}
		return args;
	}
	
	private Callable getFunction(Scope scope) {
		Callable fun;
        try {
            fun = (Callable)(getElement().execute(scope));
            if(fun == null) {
            	throw new FunctionCallException("No function bound to " + getElement().toString());
            }
        }
        catch(ClassCastException e) {
            throw new FunctionCallException("No function bound to " + getElement().toString());
        }
		return fun;
	}

    public String toString()
    {
    	return createString();
    }

    public String createString()
    {
        StringBuffer str = new StringBuffer();
        str.append('(');
        for(Entity node : this) {
        	str.append(node.createString()).append(" ");
        }
        str.setLength(str.length() - 1);
        str.append(")");
        return str.toString();
    }

    public Iterator<Entity> iterator() {
		return this.new SparseListIterator();
	}
    
    protected class SparseListIterator implements Iterator<Entity> {

    	private SparseListNode current = SparseListNode.this;

    	public boolean hasNext() {
			return current != null;
		}

		public Entity next() {
			Entity entity = current.element;
			current = current.next;
			return entity;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
    }

	@Override
	public SparseBoolean equal(Object other) {
		return toSparseBoolean(compareLists(other));
	}
	
	public boolean compareLists(Object obj) {
		Iterator<Entity> otherElements = ((SparseList) obj).iterator();
		Iterator<Entity> thisElements = this.iterator();
		while(otherElements.hasNext() && thisElements.hasNext()) {
			Entity otherElement = otherElements.next();
			Entity thisElement = thisElements.next();
			if(!otherElement.equals(thisElement)) {
				return false;
			}
		}
		return otherElements.hasNext() == thisElements.hasNext();
	}
}