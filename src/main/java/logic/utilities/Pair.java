package main.java.logic.utilities;

/**
 * An immutable pair of numbers.
 * Unlike the Point class, these are ints, so you don't have to do conversions.
 * @author craig
 */
public class Pair {

	public final int x;
	public final int y;
	
	public Pair (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
}
