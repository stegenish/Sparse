package sparser;

import java.util.ArrayList;
import java.util.Iterator;

import static sparser.SparseBoolean.toSparseBoolean;

public class SparseList implements Entity, Iterable<Entity> {

	SparseListNode first = null;
	public SparseList(Entity entity) {
		first = new SparseListNode(entity);
	}

	private SparseList(SparseListNode node) {
		first = node;
	}

	public SparseList() {
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
	
	@ExposedSparseFunction(name="first")
	public Entity getFirstElement() {
		return first.getElement();
	}
	
	@ExposedSparseFunction(name="second")
	public Entity getSecondElement() {
		Entity element = SparseNull.theNull;
		if(first.next != null) {
			element = first.next.getElement();
		}
		return element;
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
			return first.compareLists(obj);
		}
		return false;
	}

	public String createString() {
		return first.createString();
	}

	@Override
	public SparseBoolean equal(Object other) {
		return toSparseBoolean(this.equals(other));
	}
}
