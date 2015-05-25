package main.java.gfx.water;

import java.io.File;

import org.lwjgl.util.vector.Matrix4f;

import main.java.gfx.entities.Camera;
import main.java.gfx.shaders.ShaderProgram;
import main.java.gfx.toolbox.Maths;

public class WaterShader extends ShaderProgram {

	public static final String SHADERS = "src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "gfx" + File.separatorChar + "water" + File.separatorChar;

	private static final String VERTEX_FILE = SHADERS + "waterVertexShader";
	private static final String FRAGMENT_FILE = SHADERS + "waterFragmentShader";
	
	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;

	public WaterShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = getUniformLocation("projectionMatrix");
		location_viewMatrix = getUniformLocation("viewMatrix");
		location_modelMatrix = getUniformLocation("modelMatrix");
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		loadMatrix(location_viewMatrix, viewMatrix);
	}

	public void loadModelMatrix(Matrix4f modelMatrix){
		loadMatrix(location_modelMatrix, modelMatrix);
	}

}