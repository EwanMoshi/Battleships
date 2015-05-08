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
import main.java.rendering.EntityRenderer;
import main.java.shaders.StaticShader;
import main.java.terrain.Terrain;
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
		
		Entity entity = new Entity(staticModel, new Vector3f(0,0.4f,0),0,0,0,1);
		//staticModel.getTexture().setHasTransparency(true); //TODO: maybe get rid of this as it turns off back culling

		Light light = new Light(new Vector3f(2000,2000,2000), new  Vector3f(1,1,1));
		
		Entity defaultLook = new Entity(staticModel, new Vector3f(0,0,0),0,0,0,1);
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		
		/*Grass Model*/
		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
		Entity grassEntity = new Entity(grass, new Vector3f(0,0,0),0,0,0,0.1f);
		//grass.getTexture().setHasTransparency(true);
		//grass.getTexture().setUseFakeLighting(true);
		
		/*Terrain Model*/
		Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("mud")));
		//terrain.getTexture().setHasTransparency(true); //set transparency true if the texture has transparency
		//terrain.getTexture().setUseFakeLighting(true); //set fake lighting true meaning all the normals face upwards
		Terrain terrain2 = new Terrain(-1,0,loader,new ModelTexture(loader.loadTexture("seabed")));

		while(!Display.isCloseRequested()) {
			//entity.increaseRotation(0, 0.5f, 0);
			camera.move();
					
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			
			renderer.processEntity(grassEntity);

			renderer.processEntity(entity);
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp(); //clean up the renderer
		loader.cleanUp(); //clean up all the VAOs and VBOs
		DisplayManager.closeDisplay(); //close display when game loop ends
	}
}
