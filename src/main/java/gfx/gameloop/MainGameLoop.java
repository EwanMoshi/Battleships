package main.java.gfx.gameloop;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import main.java.gfx.entities.Camera;
import main.java.gfx.entities.Entity;
import main.java.gfx.entities.Light;
import main.java.gfx.models.RawModel;
import main.java.gfx.models.TexturedModel;
import main.java.gfx.objConverter.ModelData;
import main.java.gfx.objConverter.OBJFileLoader;
import main.java.gfx.rendering.DisplayManager;
import main.java.gfx.rendering.EntityRenderer;
import main.java.gfx.rendering.Loader;
import main.java.gfx.rendering.MasterRenderer;
import main.java.gfx.rendering.OBJLoader;
import main.java.gfx.shaders.StaticShader;
import main.java.gfx.terrain.Terrain;
import main.java.gfx.textures.ModelTexture;
import main.java.gfx.toolbox.MouseSelector;
import main.java.gfx.water.WaterFrameBuffers;
import main.java.gfx.water.WaterRenderer;
import main.java.gfx.water.WaterShader;
import main.java.gfx.water.WaterTile;


public class MainGameLoop {

	
	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
		/* This is a test, use different verties later for different models  TODO:Read from file */
		ModelData data = OBJFileLoader.loadOBJ("ship");
		RawModel shipModel = loader.loadToVAO(data.getVertices(),  data.getTextureCoords(), data.getNormals(), data.getIndices());
		
		ModelData tileData = OBJFileLoader.loadOBJ("flatTile");
		RawModel tileModel = loader.loadToVAO(tileData.getVertices(),  tileData.getTextureCoords(), tileData.getNormals(), tileData.getIndices());
		
		ModelTexture texture = new ModelTexture(loader.loadTexture("Wood 1"));
		TexturedModel staticModel = new TexturedModel(shipModel, texture);
		ModelTexture specularTexture = staticModel.getTexture();
		specularTexture.setShineDamper(15);
		specularTexture.setReflectivity(0.5f);
		Entity entity = new Entity(staticModel, new Vector3f(20,0.4f,-40),0,0,0,1,data);
		//staticModel.getTexture().setHasTransparency(true); //TODO: maybe get rid of this as it turns off back culling
		
		/* List of all the entities in the game */
		List<Entity> entities = new ArrayList<>();
		entities.add(entity);
		
		Light light = new Light(new Vector3f(2000,2000,2000), new  Vector3f(1,1,1));
		
		Entity defaultLook = new Entity(staticModel, new Vector3f(0,20,-45),0,0,0,1,data);
		Camera camera = new Camera(defaultLook);
		
		MasterRenderer renderer = new MasterRenderer(loader);
		
		/*Grass Model*/
		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),  new ModelTexture(loader.loadTexture("grassTexture")));
		Entity grassEntity = new Entity(grass, new Vector3f(0,0,0),0,0,0,0.1f,OBJFileLoader.loadOBJ("grassModel"));
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
		ModelTexture t = new ModelTexture(loader.loadTexture("tile")); //these 2 must be
		TexturedModel gridTile = new TexturedModel(tileModel, t);      //outside for loop to increase performance!	
		gridTile.getTexture().setHasTransparency(true); 
		for(int i = -10; i < 10; i++) {
			for(int j = -10; j < 0; j++) { //100 covers all almost
				Entity gridTileEntity = new Entity(gridTile, 0, new Vector3f(i*10.5f+5,4,j*10.5f+5),0,0,0, 1);
				entities.add(gridTileEntity);
			}
		}
		//****************************************************************//
		
		WaterFrameBuffers fbos = new WaterFrameBuffers();

		//****************** Setup the Water Renderer ********************//
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader,waterShader, renderer.getProjectionMatrix(), fbos);
		List<WaterTile> waters = new ArrayList<>();
		waters.add(new WaterTile(-50, -50, 0)); //x and z position first two parameters and third is height
		waters.add(new WaterTile(50, -50, 0)); 

		
		MouseSelector selector = new MouseSelector(camera, renderer.getProjectionMatrix());
		
		while(!Display.isCloseRequested()) {
			//entity.increaseRotation(0, 0.5f, 0);
			camera.move();
			
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0); // enable clipping planes (distance of each vertex from the plane)
			
			//selector.update(); //nothing to select at the moment so commented this out

			fbos.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - waters.get(0).getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			//-waters.getheight would render things above the water but this causes problem with the outline of tiles being rendered onto water
			renderer.renderScene(entities, terrains, light, camera, new Vector4f(0, 1, 0 , -5)); //-5 clips anything above -5 height so tiles don't get rendered
			camera.getPosition().y += distance;
			camera.invertPitch();
			
			fbos.bindRefractionFrameBuffer();
			renderer.renderScene(entities, terrains, light, camera, new Vector4f(0, -1, 0 , waters.get(0).getHeight()));
			
			/*Render to screen*/
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			fbos.unbindCurrentFrameBuffer();
			renderer.renderScene(entities, terrains, light, camera, new Vector4f(0, -1, 0 ,15)); //clip plane is vector4f to tell OpenGL where to start clipping			
			
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
