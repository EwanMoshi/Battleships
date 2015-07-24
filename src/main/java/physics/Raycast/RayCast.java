package main.java.physics.Raycast;

import java.util.List;

import main.java.gfx.entities.BoundingSphere;
import main.java.gfx.entities.Entity;
import main.java.gfx.toolbox.Maths;

import org.lwjgl.util.vector.Vector3f;

public class RayCast {

	/**
	 * This class does calculations with the 'currentRay' (from main.java.gfx.toolbox) on game world.
	 * 
	 * (A method that may be useful is inside BoundingSphere.java called contains(Vector3f Point)) 
	 */
	
		
	
	/**
	 * Check if the current ray is colliding with any real objects
	 * @param ray - the ray we are checking
	 * @return Entity that ray collides with, or null if no collision
	 */
	public static Entity isColliding(Vector3f ray, List<Entity> entities) {
		for (Entity entity : entities) {
			if (colliding(ray, entity.boundingSphere())) return entity;
		}
		return null;
	}
	
	private static boolean colliding (Vector3f ray, BoundingSphere sphere) {

		// Check if sphere is behind the ray.
		Vector3f centre = sphere.getPosition();
		float dotProduct = Vector3f.dot(ray, centre);
		if (dotProduct <= 0) return false;
		
		// Sphere in front of the ray. Compute projection.
		Vector3f orthogProjection = Maths.OrthogonalProjection(centre, ray);
		float magnitude = Maths.Magnitude(orthogProjection);
		float radius = (float) Math.sqrt(sphere.radiusSquared());
		return magnitude <= radius;

	}
	
}
