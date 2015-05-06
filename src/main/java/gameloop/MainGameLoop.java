package main.java.gameloop;

import org.lwjgl.opengl.Display;

import main.java.models.RawModel;
import main.java.models.TexturedModel;
import main.java.rendering.DisplayManager;
import main.java.rendering.Loader;
import main.java.rendering.Renderer;
import main.java.shaders.StaticShader;
import main.java.textures.ModelTexture;


public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();

		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();

		/* This is a test, use different verties later for different models  TODO:Read from file */
		float[] vertices = {
				-0.5f, 0.5f, 0f, //V0
				-0.5f, -0.5f, 0f, //V1
				0.5f, -0.5f, 0f, //V2
				0.5f, 0.5f, 0f, //V3
		};

		int[] indices = {
				0,1,3, //Top left triangle (V0,V1,V3)
				3,1,2 //Bottom right Triangle (V3,V1,V2)
		};
		
		float[] textureCoords = {
				0,0, //V0
				0,1, //V1
				1,1, //V2
				1,0 //V3
		};

		RawModel model = loader.loadToVAO(vertices,indices,textureCoords);
		ModelTexture texture = new ModelTexture(loader.loadTexture("texture"));
		TexturedModel textureModel = new TexturedModel(model, texture);
		
		while(!Display.isCloseRequested()) {
			renderer.prepare();
			shader.start();
			renderer.render(textureModel);
			shader.stop();
			DisplayManager.updateDisplay();
		}

		shader.cleanUp(); //clean up the shaders
		loader.cleanUp(); //clean up all the VAOs and VBOs
		DisplayManager.closeDisplay(); //close display when game loop ends
	}
}
