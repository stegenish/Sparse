/*
 * SparseListTest.java
 * JUnit based test
 *
 * Created on 08 August 2004, 21:33
 */

package tests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import sparser.Entity;
import sparser.SparseInt;
import sparser.SparseList;
import sparser.Sparser;

/**
 *
 * @author Administrator
 */
public class SparseListTest extends SparserTestCase
{
    private SparseList list1;
    private SparseList list2;

    @Before
    public void setUp() throws Exception
    {
    	super.setUp();
        list1 = new SparseList();
        list2 = new SparseList();
    }

	@Test
    public void testGets()
    {
    	list1 = new SparseList();
        assertEquals(new SparseList(), list1.rest());
    }

	@Test
    public void testAddLast()
    {
    	list1.append(SparseInt.valueOf("1"));
    	list1.append(SparseInt.valueOf("2"));
    	list2.append(SparseInt.valueOf("3"));
    	list2.append(SparseInt.valueOf("4"));
    	list1.insertEnd(list2);
    	SparseInt num = (SparseInt)((SparseList) ((SparseList) ((SparseList) list1.rest()).rest()).rest()).getFirstElement();
    	assertEquals(SparseInt.valueOf("4"), num);
    }

	@Test
    public void testCreateString() throws Exception
    {
        String str = "(hello)";
        SparseList sl = parse(parser, str);
        assertEquals(str, sl.toString());

        str = "(hello)";
        sl = parse(parser, str);
        assertEquals(str, sl.toString());

        str = "(hello there)";
        sl = parse(parser, str);
        assertEquals(str, sl.toString());

        str = "(hello there (footnote) everyone)";
        sl = parse(parser, str);
        assertEquals(str, sl.toString());

        str = "(hello there (deep (footnote)) everyone)";
        sl = parse(parser, str);
        assertEquals(str, sl.toString());

        str = "(hello there \"a string\" (deep \"(footnote)\") everyone)";
        sl = parse(parser, str);
        assertEquals(str, sl.toString());
    }

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
	
	private SparseList parse(Sparser parser, String str) {
		try {
			return (SparseList)(parser.parseString(str).getEntities().get(0));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
