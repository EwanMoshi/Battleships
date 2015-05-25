package  main.java.gfx.textures;

public class ModelTexture {
	
	private int textureID;
	
	private float shineDamper = 1;
	private float reflectivity = 0;
	
	private boolean hasTransparency = false;
	private boolean useFakeLighting = false; //this is useful for things like grass to set all the normals to face upwards
	
	private int numberOfRows = 1; //this is used for a Texture Atlases (this game will only use for tiles so just 1 row)
	
	public ModelTexture(int texture){
		this.textureID = texture;
	}
	
	
	
	public boolean isUseFakeLighting() {
		return useFakeLighting;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}



	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}



	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
	}

	
	public boolean isHasTransparency() {
		return hasTransparency;
	}

	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}

	public int getID(){
		return textureID;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	
	

}
