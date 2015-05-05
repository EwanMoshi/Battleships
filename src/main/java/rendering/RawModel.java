package main.java.rendering;

public class RawModel {

	private int vaoID;
	private int vertexCount;
	
	public RawModel(int vaoID, int VertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = VertexCount;
	}
	
	
	public int getVaoID() {
		return this.vaoID;
	}
	
	public int getVertexCount() {
		return this.vertexCount;
	}
	
}
