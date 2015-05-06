package main.java.gameloop;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import main.java.entities.Camera;
import main.java.entities.Entity;
import main.java.entities.Light;
import main.java.models.RawModel;
import main.java.models.TexturedModel;
import main.java.rendering.DisplayManager;
import main.java.rendering.Loader;
import main.java.rendering.MasterRenderer;
import main.java.rendering.OBJLoader;
import main.java.rendering.Renderer;
import main.java.shaders.StaticShader;
import main.java.textures.ModelTexture;


public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();

		Loader loader = new Loader();


		/* This is a test, use different verties later for different models  TODO:Read from file */
		RawModel model = OBJLoader.loadObjModel("ship", loader);
		
		
		ModelTexture texture = new ModelTexture(loader.loadTexture("Wood 1"));
		TexturedModel staticModel = new TexturedModel(model, texture);
		ModelTexture specularTexture = staticModel.getTexture();
		specularTexture.setShineDamper(15);
		specularTexture.setReflectivity(0.5f);
		
		Entity entity = new Entity(staticModel, new Vector3f(0,-5,-30),0,0,0,1);
		Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
		
		Camera camera = new Camera(entity);
		MasterRenderer renderer = new MasterRenderer();
		
		
		while(!Display.isCloseRequested()) {
			//entity.increaseRotation(0, 0.5f, 0);
			camera.move();
					
			renderer.processEntity(entity);
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp(); //clean up the renderer
		loader.cleanUp(); //clean up all the VAOs and VBOs
		DisplayManager.closeDisplay(); //close display when game loop ends
	}
}
