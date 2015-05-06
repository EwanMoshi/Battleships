package main.java.rendering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import main.java.models.RawModel;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Parser for loading in OBJ files
 * OBJ Files are good for static models that don't move
 *
 */
public class OBJLoader {

	public static RawModel loadObjModel(String filename, Loader loader) {
		FileReader fr = null; //must set to null or else line 29 gives error
		try {
			 fr = new FileReader(new File("res"+ File.separatorChar + filename+".obj"));
		} catch (FileNotFoundException e) {
			System.err.println("Failed to load the obj model");
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(fr);
		String line;
		List<Vector3f> vertices = new ArrayList<Vector3f>(); //list of vertices
		List<Vector2f> textures = new ArrayList<Vector2f>(); //list of textures
		List<Vector3f> normals = new ArrayList<Vector3f>(); //list of all the normals
		List<Integer> indices = new ArrayList<Integer>();
		float[] verticesArray = null;
		float[] texturesArray = null;
		float[] normalsArray = null;
		int[] indicesArray = null;
		
		try {
			while(true) {
				line = reader.readLine(); //read a line from the obj file
				String[] currentLine = line.split(" "); //split the line with spaces
				
				/*Check to see what the line starts with to determine what we are reading*/
				if(line.startsWith("v ")) { //if we're reading a vector store it as vertex
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
				}
				else if(line.startsWith("vt ")) { //if line is a texture, add to the textures list
					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
					textures.add(texture);
				}
				else if(line.startsWith("vn ")) { //if the line is a normal vector, add to list of normals
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
				}
				else if(line.startsWith("f ")) {
					texturesArray = new float[vertices.size()*2];
					normalsArray = new float[vertices.size()*3];
					break;
				}
			}
			
			while(line != null) {
				if(!line.startsWith("f ")) {
					line = reader.readLine();
					continue;
				}
				
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");

				/* Process the current triangle */
				processVertex(vertex1,indices,textures,normals,texturesArray,normalsArray);
				processVertex(vertex2,indices,textures,normals,texturesArray,normalsArray);
				processVertex(vertex3,indices,textures,normals,texturesArray,normalsArray);

				line=reader.readLine();
			}
			reader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		verticesArray = new float[vertices.size() * 3];
		indicesArray = new int[indices.size()];
		
		int vertexPointer = 0;
		for(Vector3f v : vertices) {
			verticesArray[vertexPointer++] = v.x;
			verticesArray[vertexPointer++] = v.y;
			verticesArray[vertexPointer++] = v.z;
		}
		
		for(int i = 0; i < indices.size(); i++) {
			indicesArray[i] = indices.get(i);
		}
		return loader.loadToVAO(verticesArray, indicesArray, texturesArray, normalsArray);
	}
	
	
	/**
	 * Organizes the vertex because the obj file randomizes the data for each vertex
	 * @param vertexData
	 * @param indices
	 * @param textures
	 * @param normals
	 * @param textureArray
	 * @param normalArray
	 */
	private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, float[] normalArray) {
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1; //obj file starts at 1 and array starts at 0 so minus 1
		indices.add(currentVertexPointer);
		
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
		textureArray[currentVertexPointer * 2] = currentTex.x;
		textureArray[currentVertexPointer * 2+1] = 1 - currentTex.y;
		
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) -1);
		normalArray[currentVertexPointer * 3] = currentNorm.x;
		normalArray[currentVertexPointer * 3 + 1] = currentNorm.y;
		normalArray[currentVertexPointer * 3 + 2] = currentNorm.z;
		
	}
	
	
}