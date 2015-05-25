package main.java.toolbox;

import main.java.entities.Camera;
import main.java.terrain.Terrain;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class MouseSelector {

	private Vector3f currentRay;
	
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private Camera camera;

		
	public MouseSelector(Camera camera, Matrix4f projection) {
		this.camera = camera;
		this.projectionMatrix = projection;
		this.viewMatrix = Maths.createViewMatrix(camera);
	}
	
	public Vector3f getCurrentRay() {
		return currentRay;
	}
	
	
	private Vector3f calculateMouseRay() {
		float mouseX = Mouse.getX();
		float mouseY = Mouse.getY();
		Vector2f normalisedCoordinates = getNormalizedDeviceCoordinates(mouseX, mouseY);
		Vector4f clipCoordinates = new Vector4f(normalisedCoordinates.x, normalisedCoordinates.y, -1f, 1f);
		Vector4f eyeCoordinates = toEyeCoordinates(clipCoordinates);
		Vector3f worldMouseRay = toWorldCoordinates(eyeCoordinates);
		return worldMouseRay;
	}
	
	/**
	 * Update every frame
	 */
	public void update() {
		viewMatrix = Maths.createViewMatrix(camera);
		currentRay = calculateMouseRay();
	}
	
	/**
	 * Convert mouse coordinates into OpenGL coordinates
	 * @param mouseX
	 * @param mouseY
	 * @return
	 */
	private Vector2f getNormalizedDeviceCoordinates(float mouseX, float mouseY) {
		float x = (2f * mouseX) / Display.getWidth() -1;
		float y = (2f * mouseY) / Display.getHeight() - 1f;
		return new Vector2f(x,y);
 	}
	
	private Vector4f toEyeCoordinates(Vector4f clipCoordinates) {
		Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
		Vector4f eyeCoordinates = Matrix4f.transform(invertedProjection, clipCoordinates, null);
		return new Vector4f(eyeCoordinates.x, eyeCoordinates.y, -1f, 0f);
		
	}
	
	/**
	 * Convert from eye coordinates, to world coordinates
	 * @param eyeCoordinates - 4f eye coordinates
	 * @return mouseRay - 4d world coordinates
	 */
	private Vector3f toWorldCoordinates(Vector4f eyeCoordinates) {
		Matrix4f invertedViewMatrix = Matrix4f.invert(viewMatrix, null);
		Vector4f rayWorld = Matrix4f.transform(invertedViewMatrix, eyeCoordinates, null);
		Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalise(); //turn to unit vector
		return mouseRay;
	}
	

	
	
	
	
	
	
	
	private Vector3f getPointOnRay(Vector3f ray, float distance) {
		Vector3f camPos = camera.getPosition();
		Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
		Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
		return Vector3f.add(start, scaledRay, null);
	}
	
	private Vector3f binarySearch(int count, float start, float finish, Vector3f ray) {
		float half = start + ((finish - start) / 2f);
		if (count >= RECURSION_COUNT) {
			Vector3f endPoint = getPointOnRay(ray, half);
			Terrain terrain = getTerrain(endPoint.getX(), endPoint.getZ());
			if (terrain != null) {
				return endPoint;
			} else {
				return null;
			}
		}
		if (intersectionInRange(start, half, ray)) {
			return binarySearch(count + 1, start, half, ray);
		} else {
			return binarySearch(count + 1, half, finish, ray);
		}
	}

	private boolean intersectionInRange(float start, float finish, Vector3f ray) {
		Vector3f startPoint = getPointOnRay(ray, start);
		Vector3f endPoint = getPointOnRay(ray, finish);
		if (!isUnderGround(startPoint) && isUnderGround(endPoint)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isUnderGround(Vector3f testPoint) {
		Terrain terrain = getTerrain(testPoint.getX(), testPoint.getZ());
		float height = 0;
		if (terrain != null) {
			height = terrain.getHeightOfTerrain(testPoint.getX(), testPoint.getZ());
		}
		if (testPoint.y < height) {
			return true;
		} else {
			return false;
		}
	}

	private Terrain getTerrain(float worldX, float worldZ) {
		return terrain;
	}
	
	
	
	
	
	
	
}
