package  main.java.gfx.toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import main.java.gfx.entities.Camera;

public class Maths {

	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry,float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale,scale,scale), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix,viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}

	/**
	 * Compute the vector orthogonal to the projection vector of the first on the second.
	 * This is the x vector in the diagram below:
	 *
	 *     /\
	 *     /x
	 *    / x
	 *   /  x
	 *  /   x
	 *  ----------->
	 *
	 * @param first: vector doing the projecting.
	 * @param second: vector being projected onto.
	 * @return the vector
	 * @author craig
	 */
	public static Vector3f OrthogonalProjection (Vector3f first, Vector3f second) {
		Vector3f projection = ProjectionOnto(first, second);
		return Vector3f.sub(first, projection, null);
	}
	
	/**
	 * Compute a vector which is in the direction of the second vector with length equal to the
	 * projection of the first vector onto the second.
	 * @param first: vector doing the projecting.
	 * @param second: vector being projected onto
	 * @return the vector 
	 * @author craig
	 */
	public static Vector3f ProjectionOnto (Vector3f first, Vector3f second) {
		float lengthOfProjection = Vector3f.dot(first, second) / Magnitude(second);
		Vector3f normalised = second.normalise(null);
		return (Vector3f) normalised.scale(lengthOfProjection); // I'm not sure about this casting.... Aaron
	}

	/**
	 * Return the magnitude of a vector = sqrt(x^2 + y^2 + z^2)
	 * @param vector: vector whose magnitude you're taking
	 * @return double
	 * @author craig
	 */
	public static float Magnitude (Vector3f vector) {
		return (float) Math.sqrt(Vector3f.dot(vector, vector));
	}

	
}
