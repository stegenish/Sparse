package sparser;

import static sparser.SparseBoolean.toSparseBoolean;

import java.util.Iterator;

public class SparseList implements Entity, Iterable<Entity> {
	
	private Entity element = SparseNull.theNull;
    private SparseList next;
	
	private SparseList next() {
		return next;
	}
	
	private Entity element() {
		return element;
	}
	
	private void next(SparseList node) {
		next = node;
	}
	
	private void element(Entity elem) {
		element = elem;
	}
	
	public SparseList() {
	}

	@ExposedSparseFunction(name = "concat")
	public SparseList concat(Entity list) {
		if (list != SparseNull.theNull) {
			return insertEnd((SparseList) list);
		}
		return this;
	}
	
	public SparseList insertEnd(SparseList list) {
		if(next() == null) {
			next(list);
		} else {
			next().insertEnd(list);
		}
		return this;
	}

	@ExposedSparseFunction(name = "append")
	public void append(Entity elem) {
		if(element() == SparseNull.theNull) {
    		element(elem);
    	} else if(next() == null) {
    		next(new SparseList());
    		next().append(elem);
    	} else {
    		next().append(elem);
    	}
	}

	@ExposedSparseFunction(name = "first")
	public Entity getFirstElement() {
		return element();
	}
	
	@ExposedSparseFunction(name = "rest")
	public Entity rest() {
		Entity sparseList;
		SparseList restNodes = next();
		if(restNodes != null) {
			sparseList = restNodes;
		} else {
			sparseList = new SparseList();
		}
		return sparseList;
	}

	public Iterator<Entity> iterator() {
		return new SparseListIterator(this);
	}

	public String toString() {
		return createString();
	}

	@ExposedSparseFunction(name = "empty")
	public SparseBoolean isEmpty() {
		return toSparseBoolean(isNil());
	}
	
	public boolean isNil() {
		return element() == SparseNull.theNull && next() == null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SparseList) {
			Iterator<Entity> otherElements = ((SparseList)obj).iterator();
			Iterator<Entity> thisElements = iterator();
			while(otherElements.hasNext() && thisElements.hasNext()) {
				Entity otherElement = otherElements.next();
				Entity thisElement = thisElements.next();
				if(!otherElement.equals(thisElement)) {
					return false;
				}
			}
			return otherElements.hasNext() == thisElements.hasNext();
		}
		return false;
	}

	public String createString() {
		StringBuffer str = new StringBuffer();
		str.append('(');
		for(Entity node : this) {
			str.append(node.createString()).append(" ");
		}
		if(str.length() > 1) {
			str.setLength(str.length() - 1);
		}
		str.append(")");
		return str.toString();
	}

	@Override
	public SparseBoolean equal(Object other) {
		return toSparseBoolean(this.equals(other));
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	public Entity execute(Scope scope) {
		Callable fun = getFunction(scope);
		ArgumentList args = createArgumentList();
		Entity returnValue = fun.callWithScope(args, scope);
		return returnValue;
	}

	private ArgumentList createArgumentList() {
		ArgumentList args = ArgumentList.createArgumentList();
		SparseList listNode = next();
		while(listNode != null) {
			args.addArg(listNode.element());
		    listNode = listNode.next();
		}
		return args;
	}

	private Callable getFunction(Scope scope) {
		Callable fun;
		try {
		    fun = (Callable)(element().execute(scope));
		    if(fun == null) {
		    	throw new FunctionCallException("No function bound to " + element().toString());
		    }
		}
		catch(ClassCastException e) {
		    throw new FunctionCallException("No function bound to " + element().toString());
		}
		return fun;
	}
}
