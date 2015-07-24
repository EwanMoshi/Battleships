package test.java.gfx.entities;

import static org.junit.Assert.*;

import org.junit.Test;
import org.lwjgl.util.vector.Vector3f;

import main.java.gfx.entities.Entity;

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

	private Entity makeTrivial() {
		return Entity.MockSphereEntity(new Vector3f(0,0,0), 0);
	}

	@Test
	public void testTrivialSphereVertex() {
		// sphere around a trivial model containing one vertex has only itself.
		Entity sphere = makeTrivial();
		Vector3f centre = sphere.getCentre();
		assertTrue(sphere.boundingSphereContains(centre));
	}

	@Test
	public void testTrivialSphereNonVertices() {
		Entity sphere = makeTrivial();
		Vector3f v1 = new Vector3f(0.002f, 0.001f, -0.001f);
		assertFalse(sphere.boundingSphereContains(v1));
		Vector3f v2 = new Vector3f(0.015f, -0.15f, 0f);
		assertFalse(sphere.boundingSphereContains(v2));
		Vector3f v3 = new Vector3f(-0.001f, 0, 0);
		assertFalse(sphere.boundingSphereContains(v3));
	}



	// Small equidistant vertices tests.
	// ------------------------------------------------------------

	/* These tests are for a sphere containing three equidistant vertices.
	   Centre:   (2,1,3)
	   Radius^2: 25
	
	*/

	   private final float[] verticesSmall = new float[]{
		3, 3, 3,
		1, -1, 3,
		2, 1, 3
	   };
	   
	private Entity makeSmall() {
		float x = 0;
		float y = 0;
		float z = 0;
		for (int i = 0; i < verticesSmall.length; i += 3) {
			x += verticesSmall[i];
			y += verticesSmall[i+1];
			z += verticesSmall[i+2];
		}
		float xmean = x / 3;
		float ymean = y / 3;
		float zmean = z / 3;
		Vector3f centre = new Vector3f(xmean, ymean, zmean);
		
		// distance from centre to farthest away vertex becomes the radius.
		float radiusSquared = 0;
		for (int i = 0; i < verticesSmall.length; i+= 3) {
			float v_x = verticesSmall[i];
			float v_y = verticesSmall[i+1];
			float v_z = verticesSmall[i+2];
			Vector3f v = new Vector3f(v_x, v_y, v_z);
			Vector3f v_dist = new Vector3f();
			Vector3f.sub(v, centre, v_dist);
			float mag = v_dist.lengthSquared();
			if (mag > radiusSquared) radiusSquared = mag;
		}
		float radius = (float) Math.sqrt(radiusSquared);
		return Entity.MockSphereEntity(centre, radius);
	}
	
	/**
	 * Each point in the vertices used to create a bounding sphere should be
	 * contained within the bounding sphere.
	 */
	@Test
	public void testVerticesContainedInSphere() {
		Entity sphere = makeSmall();
		for (int i = 0; i < verticesSmall.length; i += 3) {
			float x = verticesSmall[i];
			float y = verticesSmall[i+1];
			float z = verticesSmall[i+2];
			Vector3f vector = new Vector3f(x, y, z);
			assertTrue(sphere.boundingSphereContains(vector));
		}
	}

	/**
	 * Test a whole bunch of vertices that aren't vertices used to create the sphere,
	 * but are nonetheless contained within the sphere.
	 */
	@Test
	public void testNonVerticesContainedInSphere() {
		Entity sphere = makeSmall();
		Vector3f v1 = new Vector3f(4,1,3);
		assertTrue(sphere.boundingSphereContains(v1));
		Vector3f v2 = new Vector3f(1,2,3);
		assertTrue(sphere.boundingSphereContains(v2));
		Vector3f v3 = new Vector3f(2.5f,2.2f,2.f);
		assertTrue(sphere.boundingSphereContains(v3));
		Vector3f v4 = new Vector3f(2.2f, -0.2f, 3f);
		assertTrue(sphere.boundingSphereContains(v4));
	}

	@Test
	public void testPointsNotInSphere() {
		Entity sphere = makeSmall();

		// a very far away point
		Vector3f v1 = new Vector3f(10, 1, 3);
		assertFalse(sphere.boundingSphereContains(v1));

		// a point just beyond one the vertices in the sphere
		Vector3f v2 = new Vector3f(3, 3, 3.5f);
		assertFalse(sphere.boundingSphereContains(v2));
	}

}
