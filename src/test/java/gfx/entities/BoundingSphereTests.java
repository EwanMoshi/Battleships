package test.java.gfx.entities;

import static org.junit.Assert.*;

import org.junit.Test;
import org.lwjgl.util.vector.Vector3f;

import main.java.gfx.entities.BoundingSphere;

/**
 * Tests for BoundingSpheres.
 * @author craig
 */
public class BoundingSphereTests {

	private final float[] verticesSmall = new float[]{
		3, 3, 3,
		1, -1, 3,
		2, 1, 3
	};

	/**
	 * Make a BoundingSphere. Has the following properties:
	 * 		- centre   = (2,1,3)
	 * 		- radius^2 = 25
	 * All the vertices in this sphere are equidistant.
	 * @return
	 */
	private BoundingSphere makeSmall() {
		return new BoundingSphere(verticesSmall);
	}
	
	/**
	 * Each point in the vertices used to create a bounding sphere should be
	 * contained within the bounding sphere.
	 */
	@Test
	public void testVerticesContainedInSphere() {
		BoundingSphere sphere = makeSmall();
		for (int i = 0; i < verticesSmall.length; i += 3) {
			float x = verticesSmall[i];
			float y = verticesSmall[i+1];
			float z = verticesSmall[i+2];
			Vector3f vector = new Vector3f(x, y, z);
			assertTrue(sphere.contains(vector));
		}
	}
	
	/**
	 * Test a whole bunch of vertices that aren't vertices used to create the sphere,
	 * but are nonetheless contained within the sphere.
	 */
	@Test
	public void testNonVerticesContainedInSphere() {
		BoundingSphere sphere = makeSmall();
		Vector3f v1 = new Vector3f(4,1,3);
		assertTrue(sphere.contains(v1));
		Vector3f v2 = new Vector3f(1,2,3);
		assertTrue(sphere.contains(v2));
		Vector3f v3 = new Vector3f(2.5f,2.2f,2.f);
		assertTrue(sphere.contains(v3));
		Vector3f v4 = new Vector3f(2.2f, -0.2f, 3f);
		assertTrue(sphere.contains(v4));
	}
	
	@Test
	public void testPointsNotInSphere() {
		BoundingSphere sphere = makeSmall();
		
		// a very far away point
		Vector3f v1 = new Vector3f(10, 1, 3);
		assertFalse(sphere.contains(v1));
		
		// a point just beyond one the vertices in the sphere
		Vector3f v2 = new Vector3f(3, 3, 3.5f);
		assertFalse(sphere.contains(v2));
	}
	
}
