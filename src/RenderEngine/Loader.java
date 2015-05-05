package RenderEngine;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Loader {

	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	
	/**
	 * Take positional data about the model, store in a VAO and return information as RawModel
	 * @param positions
	 * @return
	 */
	public RawModel loadToVAO(float[] positions) {
		int vaoID = createVAO();
		storeDataInAttributeList(0,positions);
		unbindVAO();
		return new RawModel(vaoID,positions.length/3); //divide by 3 because each vertex has 3 floats
	}
	
	/**
	 * Creates an empty VAO that returns the id of the VAO
	 * @return
	 */
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays(); //creates an empty VAO and return the id of that VAO
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	/**
	 * Stores the data into an attribute list of the VAO
	 * 
	 * @param attributeNumber - The number of the attribute list that we want to store in
	 * @param data - the data to store
	 */
	private void storeDataInAttributeList(int attributeNumber, float[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER,buffer,GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber,3,GL11.GL_FLOAT,false,0,0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); //unbind the current vbo
	}
	
	/**
	 * Unbind the VAO when finished
	 */
	private void unbindVAO() {
		GL30.glBindVertexArray(0); //unbind the currently bound VAO
	}

	/**
	 * Converts the float array of data into a FloatBuffer
	 * @param data - the float array
	 * @return 
	 */
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data); //place the data into the buffer
		buffer.flip(); //prepare the buffer to be read from
		return buffer;
	}
	
	/**
	 * Loop through all the VAOs and VBOs and delete all the buffers
	 */
	public void cleanUp() {
		for(int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
	}
	
}
