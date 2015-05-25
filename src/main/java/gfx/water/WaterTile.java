package main.java.gfx.water;

public class WaterTile {
	
	public static final float TILE_SIZE = 50; //size of the tile for water (50 is nice, maybe try that later if we choose to make water tiles clickable*/
	
	private float height;
	private float x,z;
	
	public WaterTile(float centerX, float centerZ, float height){
		this.x = centerX;
		this.z = centerZ;
		this.height = height;
	}

	public float getHeight() {
		return height;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}



}
