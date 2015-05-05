package GameLoop;

import org.lwjgl.opengl.Display;






import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import RenderEngine.RawModel;
import RenderEngine.Renderer;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();

		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		
		/* This is a test, use different verties later for different models  TODO:Read from file */
		float[] vertices = { -0.5f, 0.5f, 0f, -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f, 0.5f, 0.5f, 0f, -0.5f, 0.5f, 0f};
		
		RawModel model = loader.loadToVAO(vertices);
		
		while(!Display.isCloseRequested()) {
			renderer.prepare();
			renderer.render(model);
			DisplayManager.updateDisplay();
		}
		
		loader.cleanUp(); //clean up all the VAOs and VBOs
		DisplayManager.closeDisplay(); //close display when game loop ends
	}
}
