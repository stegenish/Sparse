package sparser;

import java.util.ArrayList;
import java.util.Iterator;

import static sparser.SparseBoolean.toSparseBoolean;

public class SparseList implements Entity, Iterable<Entity> {

	SparseListNode first;

	public SparseList() {
	}

	public Entity execute(Scope scope) {
		return first.execute(scope);
	}

	@ExposedSparseFunction(name = "concat")
	public SparseList insertEnd(SparseList list) {
		if (list != null) {
			if (first == null) {
				first = list.first;
			} else {
				first.insertEnd(list.first);
			}
		}
		return this;
	}

	@ExposedSparseFunction(name = "append")
	public void append(Entity elem) {
		if (first == null) {
			first = new SparseListNode(elem);
			first.setElement(elem);
		} else {
			first.append(elem);
		}
	}

	@ExposedSparseFunction(name = "first")
	public Entity getFirstElement() {
		if (first == null) {
			return SparseNull.theNull;
		} else {
			return first.getElement();
		}
	}
	
	@ExposedSparseFunction(name = "rest")
	public Entity rest() {
		if (first == null) {
			return SparseNull.theNull;
		} else {
			SparseList sparseList = new SparseList();
			sparseList.first = first.getNext();
			return sparseList;
		}
	}

	public SparseList getLast() {
		SparseList sparseList = new SparseList();
		sparseList.first = first.getLast();
		return sparseList;
	}

	

	

	public int hashCode() {
		return first.hashCode();
	}

	public Iterator<Entity> iterator() {
		if (first == null) {
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
		if (first == null) {
			return "()";
		}
		return first.toString();
	}

	public boolean isEmpty() {
		return first == null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SparseList) {
			return first.compareLists(obj);
		}
		return false;
	}

	public String createString() {
		if (first != null) {
			return first.createString();
		} else {
			return "()";
		}
	}

	@Override
	public SparseBoolean equal(Object other) {
		return toSparseBoolean(this.equals(other));
	}
}
