package main.java;

import org.lwjgl.util.vector.Vector3f;

public class Test {

	public static void main (String[] args) {
		Vector3f v1 = new Vector3f(1, 1, 0);
		Vector3f v2 = new Vector3f(1, 0, 1);
		Vector3f v3 = new Vector3f();
		Vector3f.sub(v1, v2, v3);
		System.out.println(v3);
	}
	
}
