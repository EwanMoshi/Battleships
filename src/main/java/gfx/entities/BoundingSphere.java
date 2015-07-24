package main.java.gfx.entities;

import org.lwjgl.util.vector.Vector3f;

/**
 * A BoundingSphere is the smallest possible sphere covering every point within an entity.
 * @author craig
 */
public class BoundingSphere {

	// Fields.
	// ------------------------------------------------------------
	
	private Vector3f centre;
	private float radiusSquared;
	
	
	
	// Constructors.
	// ------------------------------------------------------------
	
	/**
	 * Construct a new BoundingSphere.
	 * @param vertices: vertices of the enclosed entity.
	 */
	public BoundingSphere(float[] vertices) {
		// formula for a sphere is: x^2 + y^2 + x^z = r^2
		
		// get centre of the entity. its components will be the mean of the
		// x, y, z values of all the vertices.
		int x, y, z;
		x = y = z = 0;
		for (int i = 0; i < vertices.length; i += 3) {
			x += vertices[i];
			y += vertices[i+1];
			z += vertices[i+2];
		}
		int numVertices = vertices.length / 3;
		x /= numVertices;
		y /= numVertices;
		z /= numVertices;
		this.centre = new Vector3f(x,y,z);
		
		// distance from centre to farthest away vertex becomes the radius.
		this.radiusSquared = 0;
		for (int i = 0; i < vertices.length; i+= 3) {
			float v_x = vertices[i];
			float v_y = vertices[i+1];
			float v_z = vertices[i+2];
			Vector3f v = new Vector3f(v_x, v_y, v_z);
			Vector3f v_dist = new Vector3f();
			Vector3f.sub(v, centre, v_dist);
			float mag = v_dist.lengthSquared();
			if (mag > this.radiusSquared) this.radiusSquared = mag;
		}
		
	}

	
	
	// Instance methods.
	// ------------------------------------------------------------
	
	/**
	 * Check if a point is contained within the bounding sphere.
	 * @param point: point to check.
	 * @return true if point is inside the sphere.
	 */
	public boolean contains (Vector3f point) {
		// important thing to remember is that we're storing radius^2, not radius.
		Vector3f distanceVector = new Vector3f();
		Vector3f.sub(centre, point, distanceVector);
		float dist = Vector3f.dot(distanceVector, distanceVector);
		return dist <= this.radiusSquared;
	}
	
	/**
	 * Return the centre of the sphere.
	 * @return Vector3f
	 */
	public Vector3f getPosition () {
		return this.centre;
	}
	
	public final float radiusSquared () {
		return this.radiusSquared;
	}
	
}
