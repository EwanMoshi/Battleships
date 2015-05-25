package main.java.gfx.terrain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector3f;

import main.java.gfx.models.RawModel;
import main.java.gfx.rendering.Loader;
import main.java.gfx.textures.ModelTexture;

public class Terrain {
	
	public static final String HEIGHT_MAP_LOCATION = "res" + File.separatorChar;

	
	private static final float SIZE = 105;
	
	private static final float MAX_HEIGHT = 40;
	private static final float MAX_PIXEL_COLOUR = 256 * 256 * 256; //3 colour channels between 0-256
	private float x;
	private float z;
	private RawModel model;  //model of the terrain
	private ModelTexture texture; //texture for the terrain
	
	public Terrain(int gridX, int gridZ, Loader loader, ModelTexture texture, String heightMap){
		this.texture = texture;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.model = generateTerrain(loader, heightMap);
	}
	
	
	
	public float getX() {
		return x;
	}



	public float getZ() {
		return z;
	}



	public RawModel getModel() {
		return model;
	}



	public ModelTexture getTexture() {
		return texture;
	}

	
	private Vector3f calculateNormal(int x, int z, BufferedImage image) {
		float heightL = getHeight(x -1, z, image);
		float heightR = getHeight(x + 1, z, image);
		float heightD = getHeight(x, z - 1, image);
		float heightU = getHeight(x, z + 1, image);
		Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
		normal.normalise();
		return normal;
	}
	
	/**
	 * Generate a flat terrain
	 * @param loader
	 * @return
	 */
	private RawModel generateTerrain(Loader loader, String heightMap){
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(HEIGHT_MAP_LOCATION + heightMap + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int VERTEX_COUNT = image.getHeight(); //numbers of vertices along each terrain
		
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT*1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				vertices[vertexPointer*3] = -(float)j/((float)VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer*3+1] = getHeight(j, i, image); //calculate height for this vetex
				vertices[vertexPointer*3+2] = -(float)i/((float)VERTEX_COUNT - 1) * SIZE;
				Vector3f normal = calculateNormal(j, i, image); //calculate the normal 
				normals[vertexPointer*3] = normal.x;
				normals[vertexPointer*3+1] = normal.y;
				normals[vertexPointer*3+2] = normal.z;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}

	private float getHeight(int x, int z, BufferedImage image) {
		if(x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()) {
			return 0;
		}
		float height = image.getRGB(x, z); //return value between -Max_PIXEL_COLOUR and 0
		height += MAX_PIXEL_COLOUR/2f; //this gives a range between MAX_PIXEL_COLOUR/2 and MAX/2
		height /= MAX_PIXEL_COLOUR/2f; //this gives a range between -1 and 1
		height *= MAX_HEIGHT; //Convert height to a value between MAX_HEIGHT and -MAX_HEIGHT
		return height;
	}
	
}


