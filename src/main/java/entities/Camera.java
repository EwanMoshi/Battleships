package main.java.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private Vector3f position = new Vector3f(0,0,0);
	private float pitch;
	private float yaw; 
	private float roll;
	
	private float distance = 50;
	private float angle = 0;
	
	private Entity entity;
	
	public Camera(Entity entity) {
		this.entity = entity;
	}
	

	
	/* Zoom in and out with scroll wheel */
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.01f;
		distance -= zoomLevel;
	}
	
	/*Move camera angle by holding right click */
	private void calculatePitch() {
		if(Mouse.isButtonDown(1)) { //right button is 1, left is 0
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
		}
	}
	
	/*Move camera around the world by holding left click */
	private void calculateAngle() {
		if(Mouse.isButtonDown(0)) { 
			float angleChange = Mouse.getDX() * 0.1f;
			angle -= angleChange;
		}
	}

	private float calculateHorizontalDist() {
		return (float) (distance * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDist() {
		return (float) (distance * Math.sin(Math.toRadians(pitch)));
	}
	
	/**
	 * Moving camera around using keyboard
	 */
	public void move() {
		calculateZoom();
		calculatePitch();
		calculateAngle();
		float horizontalDistance = calculateHorizontalDist();
		float verticalDistance = calculateVerticalDist();
		calculateCameraPos(horizontalDistance,verticalDistance);
		this.yaw = 180 - (entity.getRotY() + angle);
	}
	
	private void calculateCameraPos(float horizontalDist, float verticalDist) {
		float theta = entity.getRotY() + angle;
		float offsetX = (float) (horizontalDist * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizontalDist * Math.cos(Math.toRadians(theta)));

		position.x = entity.getPosition().x - offsetX;
		position.z = entity.getPosition().z - offsetZ;
		
		position.y = entity.getPosition().y + verticalDist;
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}


	
	
}
