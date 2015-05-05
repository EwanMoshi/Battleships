package main.java.gameloop;

import org.lwjgl.opengl.Display;

import main.java.rendering.DisplayManager;
import main.java.rendering.Loader;
import main.java.rendering.RawModel;
import main.java.rendering.Renderer;







public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();

		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		
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
		
		RawModel model = loader.loadToVAO(vertices,indices);
		
		while(!Display.isCloseRequested()) {
			renderer.prepare();
			renderer.render(model);
			DisplayManager.updateDisplay();
		}
		
		loader.cleanUp(); //clean up all the VAOs and VBOs
		DisplayManager.closeDisplay(); //close display when game loop ends
	}
}
