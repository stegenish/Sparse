package sparser;

import java.util.ArrayList;
import java.util.Iterator;

public class SparseList implements Entity, Iterable<Entity> {

	SparseListNode first = null;
	public SparseList(Entity entity) {
		first = new SparseListNode(entity);
	}

	private SparseList(SparseListNode node) {
		first = node;
	}

	public SparseList() {
		//default constructor
	}
	
	public Entity execute(Scope scope) {
		return first.execute(scope);
	}
	
	public SparseList insertFront(SparseList list) {
		if(first == null) {
			first = list.first;
		} else {
			first.insertFront(list.first);
			first = list.first;
		}
		return this;
	}
	
	@ExposedSparseFunction(name="concat")
	public SparseList insertEnd(SparseList list) {
		if(first == null) {
			first = list.first;
		} else {
			first.insertEnd(list.first);
		}
		return this;
	}

	@ExposedSparseFunction(name="append")
	public void append(Entity elem) {
		if(first == null) {
			first = new SparseListNode(elem);
		} else {
			first.append(elem);
		}
	}
	
	public StringBuffer createString(SparseListNode node, int level) {
		return first.createString(node, level);
	}
	
	@ExposedSparseFunction(name="first")
	public Entity getFirstElement() {
		return first.getElement();
	}

	public SparseList getLast() {
		return new SparseList(first.getLast());
	}

	@ExposedSparseFunction(name="rest")
	public SparseList getNext() {
		if(first == null) {
			return null;
		} else {
			return new SparseList(first.getNext());
		}
	}

	public int hashCode() {
		return first.hashCode();
	}

	public Iterator<Entity> iterator() {
		if(first == null) {
			return new ArrayList<Entity>().iterator();
		}
		return first.iterator();
	}

	public void setElement(Entity elem) {
		first.setElement(elem);
	}

	public void setNext(SparseListNode newNext) {
		first.setNext(newNext);
	}

	public String toString() {
		if(first == null) {
			return "()";
		}
		return first.toString();
	}

	public boolean isEmpty() {
		return first == null;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SparseList) {
			return compareLists(obj);
		}
		return false;
	}

	private boolean compareLists(Object obj) {
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
	        if(getElement() == null)
	        {
	            return "()";
	        }
	        else
	        {
	            return createString(this, 0).toString();
	        }
	    }

	    public StringBuffer createString(SparseListNode node,  int level)
	    {
	        StringBuffer str = new StringBuffer();
	        SparseListNode tmp = node;
	        str.append('(');
	        do
	        {
	            if(tmp.getElement() instanceof SparseList)
	            {
	                str.append(createString(((SparseList)tmp.getElement()).first, level));
	                str.setLength(str.length() - 1);
	                str.append(") ");
	            }
	            else if(tmp.getElement() instanceof SparseString)
	            {
	                str.append("\"" + ((SparseString)tmp.getElement()).getString() + "\" ");
	            }
	            else if(tmp.getElement() instanceof Symbol)
	            {
	                str.append(((Symbol)tmp.getElement()).getName() + " ");
	            }
	            tmp = tmp.getNext();
	        }while(tmp != null);
	        str.setLength(str.length() - 1);
	        str.append(")");
	        return str;
	    }

	    class SparseListIterator implements Iterator<Entity> {

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

	    public Iterator<Entity> iterator() {
			return this.new SparseListIterator();
		}

	}

}
