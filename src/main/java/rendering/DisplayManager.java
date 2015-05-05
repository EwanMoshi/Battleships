package main.java.rendering;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;


/**
 * This class manages the display
 *
 */

public class DisplayManager {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	private static final int FPS_CAP = 120;


	/**
	 * This method opens the display at the start of the game
	 */
	public static void createDisplay() {

		/*arguments is the version of OpenGL (3.2)*/
		ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

		try {
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("BattleShips"); //set the title of the display
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		/*Set the display region to the whole display region*/
		GL11.glViewport(0,0, WIDTH, HEIGHT);
	}

	/**
	 * This method updates the display every single frame
	 */
	public static void updateDisplay() {
		Display.sync(FPS_CAP); //set fps to FPS_CAP
		Display.update();
	}

	/**
	 * This method closes all the displays at the end of the game
	 */
	public static void closeDisplay() {
		Display.destroy();
	}
}
