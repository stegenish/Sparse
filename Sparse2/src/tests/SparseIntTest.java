package tests;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

import sparser.SparseInt;

public class SparseIntTest {

	@Test
	public void testValueOf() throws Exception {
		SparseInt sint = SparseInt.valueOf(2);
		assertEquals(new SparseInt("2"), sint);
	}

	@Test
	public void testValueOfString() throws Exception {
		SparseInt sint = SparseInt.valueOf("123");
		assertEquals(new SparseInt("123"), sint);
	}

	@Test
	public void testValueOfBigInteger() throws Exception {
		SparseInt sint = SparseInt.valueOf(new BigInteger("321"));
		assertEquals(new SparseInt("321"), sint);
	}

	@Test
	public void testToStringReturnsNumberAsString() throws Exception {
		assertEquals("123", SparseInt.valueOf(123).toString());
	}
}
