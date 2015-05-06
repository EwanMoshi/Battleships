package main.java.rendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.entities.Camera;
import main.java.entities.Entity;
import main.java.entities.Light;
import main.java.models.TexturedModel;
import main.java.shaders.StaticShader;

public class MasterRenderer {

	private StaticShader shader = new StaticShader();
	private Renderer renderer = new Renderer(shader);
	
	/*Map each model to a list containing that entity (how many times it needs to be displayed) */
	private Map<TexturedModel, List<Entity>> entities = new HashMap<>();
	
	public void render(Light sun, Camera camera) {
		renderer.prepare();
		shader.start();
		shader.loadLight(sun);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		entities.clear();
		
	}
	
	public void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null) {
			batch.add(entity);
		}
		else {
			List<Entity> newBatch = new ArrayList<>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
		
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
}
