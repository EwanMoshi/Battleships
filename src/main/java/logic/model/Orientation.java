package main.java.logic.model;

public enum Orientation {

	RIGHT, DOWN, LEFT, UP;

	public static Orientation rotateLeft (Orientation o) {
		int ord = o.ordinal();
		Orientation[] vals = Orientation.values();
		if (o == RIGHT) return UP; // wrap around
		int index = Math.abs(ord-1)%vals.length;
		return vals[index];
	}

	public static Orientation rotateRight (Orientation o) {
		int ord = o.ordinal();
		Orientation[] vals = Orientation.values();
		int i = (ord+1)%vals.length;
		return vals[i];
	}

}
