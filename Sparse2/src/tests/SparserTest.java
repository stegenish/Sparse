/*
 * SparserTest.java
 * JUnit based test
 *
 * Created on 08 August 2004, 22:51
 */

package tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import sparser.Entity;
import sparser.SparseInt;
import sparser.SparseList;
import sparser.SparseString;
import sparser.SparserTestCase;
import sparser.Symbol;
import sparser.SyntaxErrorException;
/**
 *
 * @author Administrator
 */
public class SparserTest extends SparserTestCase
{
    private final static String simple1      = "hello";
    private final static String simple2      = "\"look, a string\"";
    private final static String simple4      = "(good bye)";
    private final static String simple5      = "(A longer list)";
    private final static String medium1      = "(a list (with a list))";
    private final static String number1      = "1234";
    private final static String number2      = "(1234)";
    /*
     * Constructor for SparseTokeniserTest.
     * @param arg0
     */
    public SparserTest(String arg0)
    {
    	super(arg0);
    }

    public static void main(String[] args)
    {
	junit.textui.TestRunner.run(SparserTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    public void testSimple1() throws Exception
    {
        List<Entity> lists = parseStr(simple1);
        assertTrue(lists.get(0) instanceof Symbol);

    }

    public void testSimple2() throws Exception
    {
        List<Entity> lists = parseStr(simple2);
        assertTrue(lists.get(0) instanceof SparseString);
    }

    public void testSimple3() throws Exception
    {
        List<Entity> lists = parseStr("(hello)");
        assertTrue(lists.get(0) instanceof SparseList);
        assertTrue(((SparseList)(lists.get(0))).getFirstElement() instanceof Symbol);
    }

    public void testSimple4() throws Exception
    {
        List<Entity> lists = parseStr(simple4);
        SparseList list = (SparseList) lists.get(0);
        assertTrue(list.getFirstElement() instanceof Symbol);
        Symbol s = (Symbol)list.getFirstElement();
        assertEquals("good", s.getName());
        SparseList l = (SparseList) list.rest();
        assertNotNull(l);
        assertTrue(((SparseList) l.rest()).isNil());
        assertTrue(l.getFirstElement() instanceof Symbol);
    }

    public void testSimple5() throws Exception
    {
        List<Entity> lists = parseStr(simple5);
        assertTrue(lists.get(0) instanceof SparseList);
        assertTrue(((SparseList)(lists.get(0))).getFirstElement() instanceof Symbol);
        SparseList l = (SparseList) ((SparseList)(lists.get(0))).rest();
        assertNotNull(l);
        assertNotNull(l.rest());
        assertTrue(l.getFirstElement() instanceof Symbol);
        l = (SparseList) l.rest();
        assertNotNull(l);
        assertTrue(((SparseList) l.rest()).isNil());
        assertTrue(l.getFirstElement() instanceof Symbol);
    }

    public void testMedium1() throws Exception
    {
        List<Entity> lists = parseStr(medium1);
        assertTrue(lists.get(0) instanceof SparseList);
        SparseList l = (SparseList)(lists.get(0));
        assertTrue(l.getFirstElement() instanceof Symbol);
        Symbol s = (Symbol)(l.getFirstElement());
        assertEquals("a", s.getName());

        l = (SparseList) l.rest();
        assertTrue(l.getFirstElement() instanceof Symbol);
        s = (Symbol)(l.getFirstElement());
        assertEquals("list", s.getName());

        l = (SparseList) l.rest();
        assertTrue(l.getFirstElement() instanceof SparseList);
        SparseList l2 = (SparseList)(l.getFirstElement());
        assertTrue(lists.get(0) instanceof SparseList);
        assertTrue(l2.getFirstElement() instanceof Symbol);
        s = (Symbol)(l2.getFirstElement());
        assertEquals("with", s.getName());

        l2 = (SparseList) l2.rest();
        assertTrue(l2.getFirstElement() instanceof Symbol);
        s = (Symbol)(l2.getFirstElement());
        assertEquals("a", s.getName());

        l2 = (SparseList) l2.rest();
        assertTrue(l2.getFirstElement() instanceof Symbol);
        s = (Symbol)(l2.getFirstElement());
        assertEquals("list", s.getName());

        assertTrue(((SparseList) l2.rest()).isNil());
        assertTrue(((SparseList) l.rest()).isNil());
    }

	private List<Entity> parseStr(String str) {
        List<Entity> lists = null;
        try {
			lists = parser.parseString(str).getEntities();
        }
        catch(SyntaxErrorException e) {
            fail();
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lists;
	}

    public void testNumber1() throws Exception
    {
        List<Entity> lists = parseStr(number1);
        assertTrue(lists.get(0) instanceof SparseInt);
        assertEquals(((SparseInt)lists.get(0)).getValue().intValue(), 1234);
    }

    public void testNumber2() throws Exception {
        List<Entity> lists = parseStr(number2);
        assertTrue(lists.get(0) instanceof SparseList);
        SparseList list = (SparseList)(lists.get(0));
        assertTrue(list.getFirstElement() instanceof SparseInt);
        assertEquals(((SparseInt)(list.getFirstElement().execute(globalScope))).getValue().intValue(), 1234);

    }
    
    public void testBackquoteReaderMacro_Symbol() throws Exception {
    	Entity result = executeString("`a");
    	assertTrue(result instanceof Symbol);
    }
    
    public void testBackquoteReaderMacro_Number() throws Exception {
    	Entity result = executeString("`3");
    	assertTrue(result instanceof SparseInt);
    }
    
    public void testBackquoteReaderMacro_List() throws Exception {
    	Entity result = executeString("`(a)");
    	assertTrue(result instanceof SparseList);
    }
    
    public void testBackquoteReaderMacro_List_CommaShouldEvaluate() throws Exception {
    	Entity result = executeString("(let ((a 4)) `(b ,a))");
    	assertEquals(parseForm("(b 4)"), result);
    }
    
    public void testBackquoteReaderMacro_List_CommaAtShouldSplice() throws Exception {
    	Entity result = executeString("`(1 2 3 ,@(list 4 5 6))");
    	assertEquals(parseForm("(1 2 3 4 5 6)"), result);
    }
    
    public void testBackquoteReaderMacro_List_ContainsLiteralList() throws Exception {
    	Entity result = executeString("`(1 2 3 (a b c))");
    	assertEquals(parseForm("(1 2 3 (a b c))"), result);
    }
    
    public void testBackquoteReaderMacro_List_SubAtList() throws Exception {
    	Entity result = executeString("`(1 2 3 (a b c ,@(list 11 22 33 44)))");
    	assertEquals(parseForm("(1 2 3 (a b c 11 22 33 44))"), result);
    }
    
    public void testBackquoteReaderMacro_List_SubSubAtList() throws Exception {
    	Entity result = executeString("`(1 2 3 (a b c (,@(list 11 22 33 44))))");
    	assertEquals(parseForm("(1 2 3 (a b c (11 22 33 44)))"), result);
    }
    
    public void testBackquoteReaderMacro_List_SubSubAtListWithSecondCommList() throws Exception {
    	Entity result = executeString("`(1 2 3 (a b c (,@(list 11 22 33 44) ,(list \"a string\" \"string2\"))))");
    	assertEquals(parseForm("(1 2 3 (a b c (11 22 33 44 (\"a string\" \"string2\"))))"), result);
    }
}
