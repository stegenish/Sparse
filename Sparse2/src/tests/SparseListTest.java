/*
 * SparseListTest.java
 * JUnit based test
 *
 * Created on 08 August 2004, 21:33
 */

package tests;

import junit.framework.*;
import sparser.*;

/**
 *
 * @author Administrator
 */
public class SparseListTest extends SparserTestCase
{
    private SparseList list1;
    private SparseList list2;

    public SparseListTest(java.lang.String testName)
    {
        super(testName);
    }

    public static void main(String args[])
    {
        junit.textui.TestRunner.run(SparseListTest.class);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(SparseListTest.class);
        return suite;
    }

    protected void setUp() throws Exception
    {
    	super.setUp();
        list1 = new SparseList();
        list2 = new SparseList();
    }

    public void testGets()
    {
        assertNull(list1.getNext());
    }


    public void testAddLast()
    {
    	list1.append(SparseInt.valueOf("1"));
    	list1.append(SparseInt.valueOf("2"));
    	list2.append(SparseInt.valueOf("3"));
    	list2.append(SparseInt.valueOf("4"));
    	list1.insertEnd(list2);
    	SparseInt num = (SparseInt)list1.getNext().getNext().getNext().getFirstElement();
    	assertEquals(SparseInt.valueOf("4"), num);
    }

    public void testAddFront()
    {
    	list1.append(SparseInt.valueOf("1"));
    	list1.append(SparseInt.valueOf("2"));
    	list2.append(SparseInt.valueOf("3"));
    	list2.append(SparseInt.valueOf("4"));
    	list1.insertFront(list2);
    	SparseInt num = (SparseInt)list1.getNext().getFirstElement();
    	assertEquals(SparseInt.valueOf("4"), num);
    }

    public void testCreateString() throws Exception
    {
        String str = "(hello)";
        parser = new Sparser(globalScope);
        SparseList sl = parse(parser, str);
        assertEquals(str, sl.toString());


        str = "(hello)";
        parser = new Sparser(globalScope);
        sl = parse(parser, str);
        assertEquals(str, sl.toString());

        str = "(hello there)";
        parser = new Sparser(globalScope);
        sl = parse(parser, str);
        assertEquals(str, sl.toString());

        str = "(hello there (footnote) everyone)";
        parser = new Sparser(globalScope);
        sl = parse(parser, str);
        assertEquals(str, sl.toString());

        str = "(hello there (deep (footnote)) everyone)";
        parser = new Sparser(globalScope);
        sl = parse(parser, str);
        assertEquals(str, sl.toString());

        str = "(hello there \"a string\" (deep \"(footnote)\") everyone)";
        parser = new Sparser(globalScope);
        sl = parse(parser, str);
        assertEquals(str, sl.toString());
    }

	private SparseList parse(Sparser parser, String str) {
		return (SparseList)(parser.parseString(str).getEntities().get(0));
	}
}
