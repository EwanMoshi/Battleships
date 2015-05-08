package main.java.entities;

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
	
	public Camera(){
		this.cameraSpeed = 0.1f;
	}
	
	public void move(){
		yaw =  - (Display.getWidth() - Mouse.getX() / 2);
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

		}
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
