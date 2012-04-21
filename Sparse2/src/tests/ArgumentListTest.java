/*
 * ArgumentListTest.java
 * JUnit based test
 *
 * Created on 14 August 2004, 09:34
 */

package tests;

import sparser.*;

import junit.framework.*;

/**
 *
 * @author Administrator
 */
public class ArgumentListTest extends SparserTestCase
{

    public ArgumentListTest(java.lang.String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite(ArgumentListTest.class);
        return suite;
    }

    public static void main(String args[])
    {
        junit.textui.TestRunner.run(ArgumentListTest.class);
    }

    public void test1()
    {
        ArgumentList args = ArgumentList.createArgumentList();

		args.addArg(new Symbol("1"));
        args.addArg(new Symbol("2"));
        args.addArg(new Symbol("3"));
        args.addArg(new Symbol("4"));
        int i = 1;

        while(args.hasNext())
        {
            assertEquals("" + i ,args.next().toString());
            i++;
        }
    }

//    public void testBoundInGlobalScope() throws Exception {
//    	ArgumentList args = ArgumentList.createArgumentList();
//    	Scope scope = new Scope();
//    	Symbol s = new Symbol("sym");
//    	s.bind(SparseInt.valueOf(3));
//    	args.
//	}
}
