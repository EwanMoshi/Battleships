package main.java.gameloop;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import main.java.entities.Camera;
import main.java.entities.Entity;
import main.java.models.RawModel;
import main.java.models.TexturedModel;
import main.java.rendering.DisplayManager;
import main.java.rendering.Loader;
import main.java.rendering.OBJLoader;
import main.java.rendering.Renderer;
import main.java.shaders.StaticShader;
import main.java.textures.ModelTexture;


public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();

		Loader loader = new Loader();
		StaticShader shader = new StaticShader();

		Renderer renderer = new Renderer(shader);

		/* This is a test, use different verties later for different models  TODO:Read from file */
		RawModel model = OBJLoader.loadObjModel("ship", loader);
		
		
		ModelTexture texture = new ModelTexture(loader.loadTexture("Wood 1"));
		TexturedModel staticModel = new TexturedModel(model, texture);
		
		Entity entity = new Entity(staticModel, new Vector3f(0,-15,-50),0,0,0,1);
		
		Camera camera = new Camera();
		
		while(!Display.isCloseRequested()) {
			entity.increaseRotation(0, 0.5f, 0);
			camera.move();
			renderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);
			renderer.render(entity,shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}

		shader.cleanUp(); //clean up the shaders
		loader.cleanUp(); //clean up all the VAOs and VBOs
		DisplayManager.closeDisplay(); //close display when game loop ends
	}
}
