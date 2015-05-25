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

	
	
	// Trivial tests.
	// ------------------------------------------------------------
	
	// These tests are for the trivial case of a sphere enclosing a single vertex.
	// Centre:	 (0, 0, 0)
	// Radius^2: 0
	
	private final float[] verticesTrivial = new float[]{
			0, 0, 0
	};
	
	private BoundingSphere makeTrivial() {
		return new BoundingSphere(verticesTrivial);
	}
	
	@Test
	public void testTrivialSphereVertex() {
		// sphere around a trivial model containing one vertex has only itself.
		BoundingSphere sphere = new BoundingSphere(verticesTrivial);
		Vector3f v1 = new Vector3f(verticesTrivial[0], verticesTrivial[1], verticesTrivial[2]);
		assertTrue(sphere.contains(v1));
	}
	
	@Test
	public void testTrivialSphereNonVertices() {
		BoundingSphere sphere = new BoundingSphere(verticesTrivial);
		Vector3f v1 = new Vector3f(0.002f, 0.001f, -0.001f);
		assertFalse(sphere.contains(v1));
		Vector3f v2 = new Vector3f(0.015f, -0.15f, 0f);
		assertFalse(sphere.contains(v2));
		Vector3f v3 = new Vector3f(-0.001f, 0, 0);
		assertFalse(sphere.contains(v3));
	}
	
	
	
	// Small equidistant vertices tests.
	// ------------------------------------------------------------
	
	// These tests are for a sphere containing three equidistant vertices.
	// Centre:   (2,1,3)
	// Radius^2: 25
	
	private final float[] verticesSmall = new float[]{
		3, 3, 3,
		1, -1, 3,
		2, 1, 3
	};

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
