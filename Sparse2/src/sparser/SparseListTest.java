package sparser;

import java.util.Iterator;

import junit.framework.TestCase;

public class SparseListTest extends TestCase {

	public void testIterator() throws Exception {
		SparseList list = new SparseList(SparseInt.valueOf(1));
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
		SparseInt value = SparseInt.valueOf(2);
		SparseList list = new SparseList(value);
		Entity firstElement = list.getFirstElement();
		assertSame(value, firstElement);
	}

	public void testgetFirstElementFormultipleElementList() throws Exception {
		SparseInt value = SparseInt.valueOf(2);
		SparseList list = new SparseList(value);
		list.append(SparseInt.valueOf(2));
		list.append(SparseInt.valueOf(3));
		Entity firstElement = list.getFirstElement();
		assertSame(value, firstElement);
	}
}
