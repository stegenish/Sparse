/*
 * FuncCallTest.java
 * JUnit based test
 *
 * Created on 14 August 2004, 09:17
 */

package tests;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;
import sparser.Code;
import sparser.Entity;
import sparser.SparseInt;
import sparser.SparseList;

/**
 *
 * @author Administrator
 */
public class FuncCallTest extends SparserTestCase
{
    @Test
    public void test2() throws Exception
    {
    	checkValue("6", "(add 1 2 3)");
    }

    @Test
    public void test1() throws Exception
    {
    	checkValue("4", "(add 1 (add 1 2))");
    }

    @Test
    public void test3() throws Exception
    {
    	checkValue("18", "(add 1 2 3 (add 1 2 3) (add 1 2 3))");
    }

    @Test
    public void test4() throws Exception
    {
    	checkValue("20", "(add 1 2 3 (add 1 2  (add 1 2 3) 3)2)");
    }

    @Test
    public void test5() {
    	checkValue("30", "(add 1 2 3 (multiply 1 2  (add 1 2 3 ) 2))");
	}

    @Test
    public void testBind() throws Exception {
		SparseList list = parseSingleForm("(bind 12 symmy)");
		list.execute(globalScope);
		assertEquals(SparseInt.valueOf("12"), getSymbolBinding("symmy"));
	}

    @Test
    public void testCallWithBind() throws Exception {
		Code program = parseProgram("(bind 4 a) (bind 2 c) (bind (add c a) b)");
		program.execute(globalScope);
		assertEquals(SparseInt.valueOf("6"), getSymbolBinding("b"));
	}

	private void checkValue(String exoectedValue, String form) {
		SparseList list = parseSingleForm(form);
		assertEquals(new BigInteger(exoectedValue), ((SparseInt)(list.execute(globalScope))).getValue());
	}

	private SparseList parseSingleForm(String form) {
		List<Entity> lists = null;
		try {
			lists = parseProgram(form).getEntities();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        SparseList list = (SparseList)lists.get(0);
		return list;
	}
}