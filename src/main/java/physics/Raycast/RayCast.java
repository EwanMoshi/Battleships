package main.java.physics.Raycast;

import java.util.List;

import main.java.gfx.entities.Entity;
import main.java.gfx.toolbox.Maths;

import org.lwjgl.util.vector.Vector3f;

public class RayCast {

	/**
	 * Check if the current ray is colliding with any real objects
	 * @param ray - the ray we are checking
	 * @return Entity that ray collides with, or null if no collision
	 */
	public static Entity isColliding(Vector3f ray, List<Entity> entities) {
		for (Entity entity : entities) {
			
			// Check if bounding sphere is behind the ray.
			Vector3f centre = entity.getCentre(); // Sometimes this is null but it shouldn't be per the Entity constructor....
			float dotProduct = Vector3f.dot(ray, centre);
			if (dotProduct <= 0) continue;
			
			// Sphere in front of the ray. Compute projection.
			Vector3f orthogProjection = Maths.OrthogonalProjection(centre, ray);
			float magnitude = Maths.Magnitude(orthogProjection);
			float radius = entity.getBoundingRadius();
			if (magnitude <= radius) return entity;
		}
		return null;
	}
	
}
