package main.java.gfx.entities;

import main.java.gfx.models.TexturedModel;
import main.java.gfx.objConverter.ModelData;
import main.java.gfx.toolbox.Maths;

import org.lwjgl.util.vector.Vector3f;

public class Entity {

	// Instance fields.
	// ----------------------------------------------------------------------

	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;

	private int textureIndex = 0; // identifying which texture to use from textureAtlas
								  // yep, it's texture.
	
	// Fields for bounding sphere surrounding Entity.
	private Vector3f centre;
	private float boundingRadius;
	
	// Constructors.
	// ----------------------------------------------------------------------
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, ModelData data) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;

		// get centre of the entity. its components will be the mean of the
		// x, y, z values of all the vertices.
		float[] vertices = data.getVertices();
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
		float radiusSquared = 0;
		for (int i = 0; i < vertices.length; i+= 3) {
			float v_x = vertices[i];
			float v_y = vertices[i+1];
			float v_z = vertices[i+2];
			Vector3f v = new Vector3f(v_x, v_y, v_z);
			Vector3f v_dist = new Vector3f();
			Vector3f.sub(v, centre, v_dist);
			float mag = v_dist.lengthSquared();
			if (mag > radiusSquared) radiusSquared = mag;
		}
		this.boundingRadius = (float) Math.sqrt(radiusSquared);
		
	}

	public Entity(TexturedModel model, int index, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.textureIndex = index;
	}

	private Entity(){}
	
	/**
	 * This one is for unit testing purposes. Makes a sphere with a centre and a radius.
	 * @param centre: centre of sphere
	 * @param boundingRadius: radius of sphere
	 * @return Entity
	 */
	public static Entity MockSphereEntity (Vector3f centre, float boundingRadius) {
		Entity e = new Entity();
		e.centre = centre;
		e.boundingRadius = boundingRadius;
		return e;
	}

	
	// Instance methods.
	// ----------------------------------------------------------------------

	/**
	 * Check to see if the bounding sphere around this entity contains the specified point.
	 * @param point: point to check.
	 * @return boolean
	 */
	public boolean boundingSphereContains (Vector3f point) {
		Vector3f distanceVector = Vector3f.sub(centre, point, null);
		float dist = Maths.Magnitude(distanceVector);
		return dist <= this.boundingRadius;
	}
	
	public float getTextureXOffset() {
		int column = textureIndex % model.getTexture().getNumberOfRows();
		return (float) column / (float)model.getTexture().getNumberOfRows();
	}
	
	public float getTextureYOffSet() {
		int row = textureIndex / model.getTexture().getNumberOfRows();
		return (float)row / (float)model.getTexture().getNumberOfRows();
	}

	
	// Getters + setters.
	// ----------------------------------------------------------------------
	
	public TexturedModel getModel() { return model; }
	public void setModel(TexturedModel model) { this.model = model; }
	public Vector3f getPosition() { return position; }
	public void setPosition(Vector3f position) { this.position = position; }
	public float getRotX() { return rotX; }
	public void setRotX(float rotX) { this.rotX = rotX; }
	public float getRotY() { return rotY; }
	public void setRotY(float rotY) { this.rotY = rotY; }
	public float getRotZ() { return rotZ; }
	public void setRotZ(float rotZ) { this.rotZ = rotZ; }
	public float getScale() { return scale; }
	public void setScale(float scale) { this.scale = scale; }
	public Vector3f getCentre() { return this.centre; }
	public float getBoundingRadius() { return this.boundingRadius; }

	/**
	 * Increase current position of the entity
	 * @param dx
	 * @param dy
	 * @param dz
	 */
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}
	
	/**
	 * Increase current rotation of the entity
	 * @param dx
	 * @param dy
	 * @param dz
	 */
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}
	
}
