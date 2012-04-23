package sparser;

import java.util.Iterator;

import junit.framework.TestCase;

public class SparseListTest extends TestCase {

	public void testIterator() throws Exception {
		SparseList list = new SparseList();
		list.append(SparseInt.valueOf(1));
		list.append(SparseInt.valueOf(2));
		list.append(SparseInt.valueOf(3));
		Iterator<Entity> iterator = list.iterator();
		int expected = 1;
		while(iterator.hasNext()) {
			SparseInt value = (SparseInt) iterator.next();
			assertEquals(expected, value.getValue().intValue());
			expected++;
		}
	}

	public void testgetFirstElementForSingleElementList() throws Exception {
		SparseList list = new SparseList();
		list.append(SparseInt.valueOf(2));
		Entity firstElement = list.getFirstElement();
		assertEquals(SparseInt.valueOf(2), firstElement);
	}

	public void testgetFirstElementFormultipleElementList() throws Exception {
		SparseList list = new SparseList();
		list.append(SparseInt.valueOf(2));
		list.append(SparseInt.valueOf(2));
		list.append(SparseInt.valueOf(3));
		Entity firstElement = list.getFirstElement();
		assertEquals(SparseInt.valueOf(2), firstElement);
	}
}
