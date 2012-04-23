package sparser;

import java.util.Iterator;

public class SparseListIterator implements Iterator<Entity> {

	private SparseList current;

	public SparseListIterator(SparseList node) {
		current = node;
	}
	
	public boolean hasNext() {
		return current != null && !current.isNil();
	}

	public Entity next() {
		Entity entity = current.getFirstElement();
		current = (SparseList) current.rest();
		return entity;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}