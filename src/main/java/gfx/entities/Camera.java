package main.java.gfx.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position = new Vector3f(0,5,0);
	private float pitch = 10;
	private float yaw ;
	private float roll;
	
	private float cameraSpeed;
	
	public Vector3f getPosition() {
		return position;
	}

	public void invertPitch() {
		this.pitch = -pitch;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}



	public float getPitch() {
		return pitch;
	}



	public float getYaw() {
		return yaw;
	}



	public void setYaw(float yaw) {
		this.yaw = yaw;
	}



	public void setPitch(float pitch) {
		this.pitch = pitch;
	}



	public float getRoll() {
		return roll;
	}



	public void setRoll(float roll) {
		this.roll = roll;
	}

	private float distance = 50;
	private float angle = 0;
	
	private Entity entity;
	
	
	public Camera(Entity entity) {
		this.cameraSpeed = 0.01f;
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
/*		yaw =  - (Display.getWidth() - Mouse.getX() / 2);
		pitch =  (Display.getHeight() / 2) - Mouse.getY();
		
		
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			position.y += cameraSpeed;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			position.y -= cameraSpeed;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {

			position.z += -(float)Math.cos(Math.toRadians(yaw)) * cameraSpeed;
			position.x += (float)Math.sin(Math.toRadians(yaw)) * cameraSpeed;
			
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z -= -(float)Math.cos(Math.toRadians(yaw)) * cameraSpeed;
			position.x -= (float)Math.sin(Math.toRadians(yaw)) * cameraSpeed;


		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			
			position.z += (float)Math.sin(Math.toRadians(yaw)) * cameraSpeed;
			position.x += (float)Math.cos(Math.toRadians(yaw)) * cameraSpeed;

		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			
			position.z -= (float)Math.sin(Math.toRadians(yaw)) * cameraSpeed;
			position.x -= (float)Math.cos(Math.toRadians(yaw)) * cameraSpeed;

		}*/
		
		
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
	

}
















