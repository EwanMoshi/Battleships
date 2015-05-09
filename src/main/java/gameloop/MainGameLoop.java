package main.java.gameloop;

import java.util.ArrayList;
import java.util.List;

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
import main.java.water.WaterFrameBuffers;
import main.java.water.WaterRenderer;
import main.java.water.WaterShader;
import main.java.water.WaterTile;


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
		/* List of all the entities in the game */
		List<Entity> entities = new ArrayList<>();
		//entities.add(entity);
		
		Light light = new Light(new Vector3f(2000,2000,2000), new  Vector3f(1,1,1));
		
		Entity defaultLook = new Entity(staticModel, new Vector3f(0,20,-45),0,0,0,1);
		Camera camera = new Camera(defaultLook);
		
		MasterRenderer renderer = new MasterRenderer();
		
		/*Grass Model*/
		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
		Entity grassEntity = new Entity(grass, new Vector3f(0,0,0),0,0,0,0.1f);
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		//entities.add(grassEntity);
		
		//********************** Terrain Model ****************************/
		Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("mud")), "heightMap");
		//terrain.getTexture().setHasTransparency(true); //set transparency true if the texture has transparency
		//terrain.getTexture().setUseFakeLighting(true); //set fake lighting true meaning all the normals face upwards
		Terrain terrain2 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("seabed")), "heightMap");
		List<Terrain> terrains = new ArrayList<>();
		terrains.add(terrain);
		terrains.add(terrain2);
		
		//********************** GRID FOR THE BOARD ***********************/
		for(int i = 0; i < 1; i++) {
			for(int j = -1; j > -100; j--) { //100 covers all almost
				TexturedModel gridTile = new TexturedModel(OBJLoader.loadObjModel("flatTile", loader), new ModelTexture(loader.loadTexture("tile")));
				Entity gridTileEntity = new Entity(gridTile, new Vector3f(i,4,j),0,0,0, 0.5f);
				//gridTile.getTexture().setHasTransparency(true); 
				entities.add(gridTileEntity);
			}
		}
		//****************************************************************//
		
		//****************** Setup the Water Renderer ********************//
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader,waterShader, renderer.getProjectionMatrix());
		List<WaterTile> waters = new ArrayList<>();
		//waters.add(new WaterTile(-230, -240,-6)); //x and z position first two parameters and third is height
		//waters.add(new WaterTile(-750, -240, -6)); 

		WaterFrameBuffers fbos = new WaterFrameBuffers();
		
		while(!Display.isCloseRequested()) {
			//entity.increaseRotation(0, 0.5f, 0);
			camera.move();
								
			fbos.bindReflectionFrameBuffer();
			//renderer.renderScene(entities, terrains, light, camera);
			fbos.unbindCurrentFrameBuffer();
			
			renderer.renderScene(entities, terrains, light, camera);			
			waterRenderer.render(waters, camera);
			
			DisplayManager.updateDisplay();
		}
		
		/* Cleaning up after game is closed */
		fbos.cleanUp();
		renderer.cleanUp(); //clean up the renderer
		loader.cleanUp(); //clean up all the VAOs and VBOs
		DisplayManager.closeDisplay(); //close display when game loop ends
	}

}
