package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import sparser.ArgumentList;
import sparser.Callable;
import sparser.Code;
import sparser.SparseInt;
import sparser.SparseList;
import sparser.UserDefinedFunction;

public class UserDefinedFunctionTest extends SparserTestCase {

	@Test
	public void testSingleForm() throws Exception {
		Code code = parser.parseString("(bind 4 zzz)");
		Callable func = createUserDefinedFunction(code);
		func.callWithScope(ArgumentList.createArgumentList(), globalScope.createLexicalScope());
		assertEquals(SparseInt.valueOf("4"), getSymbolBinding("zzz"));
	}

	private Callable createUserDefinedFunction(Code code) {
		return new UserDefinedFunction("",null, code, globalScope.createLexicalScope());
	}
	
    @Test
	public void testMultipleForms() throws Exception {
		Code code = parser.parseString("(bind 4 zzz)(bind 5 yyy)(bind 6 xxx)");
		Callable func = createUserDefinedFunction(code);
		func.callWithScope(ArgumentList.createArgumentList(), globalScope.createLexicalScope());
		assertEquals(SparseInt.valueOf("4"), getSymbolBinding("zzz"));
		assertEquals(SparseInt.valueOf("5"), getSymbolBinding("yyy"));
		assertEquals(SparseInt.valueOf("6"), getSymbolBinding("xxx"));
	}

    @Test
	public void testWithParameters() throws Exception {
		Code code = parser.parseString("(bind 12 l1)(bind (add p1 p2 l1) qqq)");
		SparseList parameters = (SparseList) parser.parseString("(p1 p2)").getEntities().get(0);
		Callable func = new UserDefinedFunction("",parameters, code, globalScope.createLexicalScope());
		ArgumentList args = ArgumentList.createArgumentList();
		args.addArg(SparseInt.valueOf("4"));
		args.addArg(SparseInt.valueOf("6"));
		func.callWithScope(args, globalScope);
		assertEquals(SparseInt.valueOf("22"), getSymbolBinding("qqq"));
	}
}
