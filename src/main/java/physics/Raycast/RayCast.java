package main.java.physics.Raycast;

import main.java.gfx.entities.BoundingSphere;

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
	public static boolean isColliding(Vector3f ray, float[] vertices) {
		return false;
	}
	
	private static boolean colliding (Vector3f ray, BoundingSphere sphere) {
		
		
		return true;
	}
	
}
