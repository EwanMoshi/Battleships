package main.java.entities;

import main.java.models.TexturedModel;

import org.lwjgl.util.vector.Vector3f;

public class PlayerView extends Entity {

	
	
	public PlayerView(TexturedModel model, Vector3f position, float rotX,
			float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}

	
	
}

