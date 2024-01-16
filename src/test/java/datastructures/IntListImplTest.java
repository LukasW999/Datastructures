package datastructures;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.pp.datastructures.list.IntList;
import de.pp.datastructures.list.IntListImpl;

public class IntListImplTest {

	IntList list;

	@Before
	public void setUp() {
		list = new IntListImpl();
	}

	@Test
	public void testAddAndGet() {
		list.add(5);
		assertEquals(5, list.get(0));
	}

	@Test
	public void testGetWithNegative() {
		assertEquals(-1, list.get(-5));
	}

	@Test
	public void testGetWithToHigh() {
		list.addAll(0, 1);
		assertEquals(-2, list.get(2));
	}

	@Test
	public void testAddAll() {
		list.addAll(0, 1, 2);
		assertEquals(2, list.get(2));
	}

	@Test
	public void testAddSpezific() {
		list.addAll(0, 1, 3);
		list.add(2, 2);
		assertEquals(2, list.get(2));
	}

	@Test
	public void testAddSpezificLowerThanZero() {
		list.addAll(1, 2, 3);
		list.add(-5, 0);
		assertEquals(0, list.get(0));
	}

	@Test
	public void testSize() {
		list.addAll(0, 1, 2);
		assertEquals(3, list.size());
	}

	@Test
	public void testContainsTrue() {
		list.addAll(0, 1, 2);
		assertEquals(true, list.contains(1));
	}

	@Test
	public void testContainsFalse() {
		list.addAll(0, 1, 2);
		assertEquals(false, list.contains(3));
	}

	@Test
	public void testDelete() {
		list.addAll(0, 1, 2, 3, 4);
		list.delete(3);
		assertEquals(4, list.get(3));
	}

	@Test
	public void testDeleteFirst() {
		list.addAll(0, 1, 2);
		list.delete(0);
		assertEquals(1, list.get(0));
	}

	@Test
	public void testDeleteOutOfRange() {
		list.addAll(0, 1, 2);
		list.delete(4);
		assertEquals(2, list.get(2));
	}

	@Test
	public void testDeleteLast() {
		list.addAll(0, 1, 2);
		list.delete(1);
		assertEquals(2, list.size());
	}
}
