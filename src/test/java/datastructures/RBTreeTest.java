package datastructures;

import static org.junit.Assert.*;

import org.junit.Test;

import de.pp.datastructures.rbtree.RBTree;

public class RBTreeTest {

	RBTree test = new RBTree();

	@Test
	public void testContainsTrueAndAddRoot() {
		test.add(5);
		assertTrue(test.contains(5));
	}

	@Test
	public void testContainsFalse() {
		test.add(4);
		assertEquals(false, test.contains(3));
	}

	@Test
	public void testFind() {
		test.add(5);
		test.add(8);
		test.add(3);
		assertEquals(3, test.find(3).getValue());
	}

	@Test
	public void testAdd() {
		test.add(1);
		test.add(5);
		assertEquals(true, test.contains(5));
	}

	@Test
	public void testAddRBExtraRootBlack() {
		test.add(5);
		test.add(3);
		test.add(6);
		assertTrue(test.find(5).isBlack());
		assertFalse(test.find(3).isBlack());
		assertFalse(test.find(6).isBlack());

	}

	@Test
	public void testAddRBExtraYourUncleisRedAndGrandfatherIsRoot() {
		test.add(5);
		test.add(3);
		test.add(7);
		test.add(1);
		assertTrue(test.find(3).isBlack());
		assertTrue(test.find(7).isBlack());
		assertTrue(test.find(5).isBlack());
		assertFalse(test.find(1).isBlack());
	}
	@Test
	public void testAddRBExtraYourUncleIsRed() {
		test.add(15);
		test.add(8);
		test.add(9);
		test.add(10);
		test.add(3);
		test.add(1);
		test.add(5);
		test.add(30);
		test.add(35); 
		test.add(25);
		assertTrue(test.find(15).isBlack());
		assertTrue(test.find(8).isBlack());
		assertTrue(test.find(9).isBlack());
		assertFalse(test.find(10).isBlack());
		assertFalse(test.find(1).isBlack());

	}
	@Test
	public void testAddRbExtraYourUncleIsBlackAndYoureFarAway() {
		test.add(15);
		test.add(8);
		test.add(7);
		test.add(9);
		test.add(6);
		assertEquals(test.find(7), test.find(8).getParent());
		assertEquals(test.find(8), test.find(7).getRight());
		assertTrue("3. Fall Parent ist nicht schwarz", test.find(7).isBlack());
		assertFalse("3. Fall: Grandfather ist nicht rot", test.find(8).isBlack());
	}
}
