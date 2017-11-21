/*
 * ArgumentListTest.java
 * JUnit based test
 *
 * Created on 14 August 2004, 09:34
 */

package tests;

import sparser.*;
import static org.junit.Assert.*;

import org.junit.Test;


/**
 *
 * @author Administrator
 */
public class ArgumentListTest
{

	@Test
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
}
