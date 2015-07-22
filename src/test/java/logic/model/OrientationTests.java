package test.java.logic.model;

import main.java.logic.model.Orientation;
import static org.junit.Assert.*;
import org.junit.Test;

public class OrientationTests {

	@Test
	public void testRotateLeft() {
		Orientation o = Orientation.RIGHT;
		Orientation o1 = Orientation.rotateLeft(o);
		assertEquals(o1, Orientation.UP);
		Orientation o2 = Orientation.rotateLeft(o1);
		assertEquals(o2, Orientation.LEFT);
		Orientation o3 = Orientation.rotateLeft(o2);
		assertEquals(o3, Orientation.DOWN);
		Orientation o4 = Orientation.rotateLeft(o3);
		assertEquals(o4, Orientation.RIGHT);
	}

	@Test
	public void testRotateRight() {
		Orientation o = Orientation.LEFT;
		Orientation o1 = Orientation.rotateRight(o);
		assertEquals(o1, Orientation.UP);
		Orientation o2 = Orientation.rotateRight(o1);
		assertEquals(o2, Orientation.RIGHT);
		Orientation o3 = Orientation.rotateRight(o2);
		assertEquals(o3, Orientation.DOWN);
	}

	@Test
	public void testRotateInverses() {
		Orientation o = Orientation.LEFT;
		Orientation o2 = Orientation.rotateRight(Orientation.rotateLeft(o));
		assertEquals(o, o2);
		Orientation o3 = Orientation.rotateLeft(Orientation.rotateRight(o));
		assertEquals(o, o3);
	}

}
