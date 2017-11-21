package sparser;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Test;

public class SparseListTest {
    
	@Test
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
    
	@Test
	public void testgetFirstElementForSingleElementList() throws Exception {
		SparseList list = new SparseList();
		list.append(SparseInt.valueOf(2));
		Entity firstElement = list.getFirstElement();
		assertEquals(SparseInt.valueOf(2), firstElement);
	}
    
	@Test
	public void testgetFirstElementFormultipleElementList() throws Exception {
		SparseList list = new SparseList();
		list.append(SparseInt.valueOf(2));
		list.append(SparseInt.valueOf(2));
		list.append(SparseInt.valueOf(3));
		Entity firstElement = list.getFirstElement();
		assertEquals(SparseInt.valueOf(2), firstElement);
	}
    
	@Test
	public void testEmptyListPrintsProperly() throws Exception {
		assertEquals("()", new SparseList().createString());
		
	}
}
