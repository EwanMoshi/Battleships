package main.java.shaders;

import java.io.File;


public class StaticShader extends ShaderProgram {


	public static final String SHADERS = "src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "shaders" + File.separatorChar;

	private static final String VERTEX_FILE = SHADERS + "vertexShader";
	private static final String FRAGMENT_FILE = SHADERS + "fragmentShader";




	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
