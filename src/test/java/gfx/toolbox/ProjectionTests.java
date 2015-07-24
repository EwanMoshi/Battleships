package test.java.gfx.toolbox;

import static org.junit.Assert.*;

import main.java.gfx.toolbox.Maths;

import org.junit.Test;
import org.lwjgl.util.vector.Vector3f;

public class ProjectionTests {

	static final float TOLERANCE = 0.001f;

	/**
	 * Test projection values are correct.
	 */
	@Test
	public void testProjection() {
		Vector3f u = new Vector3f(4,3,2);
		Vector3f v = new Vector3f(1,6,-4);
		Vector3f projection = Maths.ProjectionOnto(u, v);
		assertEquals(projection.x, 14f/53, TOLERANCE);
		assertEquals(projection.y, 84f/53, TOLERANCE);
		assertEquals(projection.z, -56f/53, TOLERANCE);
	}
	
	/**
	 * A vector projected onto itself is itself.
	 */
	@Test
	public void testProjectionOntoSelf() {
		Vector3f u = new Vector3f(4,3,2);
		Vector3f projection = Maths.ProjectionOnto(u, u);
		assertEquals(projection.x, u.x, TOLERANCE);
		assertEquals(projection.y, u.y, TOLERANCE);
		assertEquals(projection.z, u.z, TOLERANCE);
	}
	
	/**
	 * Orthogonal vectors have a dot product of zero.
	 * Check to see if the orthogonal projection of u on v, dotted with v,
	 * returns 0.
	 */
	@Test
	public void testOrthogonalProjection() {
		Vector3f u = new Vector3f(4,3,2);
		Vector3f v = new Vector3f(1,6,-4);
		Vector3f orthog = Maths.OrthogonalProjection(u, v);
		float dotProduct = Vector3f.dot(v, orthog);
		assertEquals(dotProduct, 0, TOLERANCE);
	}
	
}
