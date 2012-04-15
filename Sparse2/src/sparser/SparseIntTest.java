package sparser;

import java.math.BigInteger;

import junit.framework.TestCase;

public class SparseIntTest extends TestCase {

	public void testValueOf() throws Exception {
		SparseInt sint = SparseInt.valueOf(2);
		assertEquals(new SparseInt("2"), sint);
	}

	public void testValueOfString() throws Exception {
		SparseInt sint = SparseInt.valueOf("123");
		assertEquals(new SparseInt("123"), sint);
	}

	public void testValueOfBigInteger() throws Exception {
		SparseInt sint = SparseInt.valueOf(new BigInteger("321"));
		assertEquals(new SparseInt("321"), sint);
	}
}
