package main.java.logic.model;

public class ShipPiece {

	private final ShipInfo shipInfo;
	private final boolean[] health;

	public ShipPiece (ShipInfo shipinfo) {
		this.shipInfo = shipinfo;
		this.health = new boolean[shipinfo.width*shipinfo.length];
		for (int i = 0; i < shipinfo.width; i++) {
			for (int j = 0; j < shipinfo.length; j++) {
				health[i*j + j] = true;
			}
		}
	}

	/**
	 * Check if any part of this ShipPiece is damaged.
	 * @return boolean
	 */
	protected boolean isDamaged () {
		for (int i = 0; i < shipInfo.width; i++) {
			for (int j = 0; j < shipInfo.length; j++) {
				if (!health[i*j]) return true;
			}
		}
		return false;
	}

	/**
	 * Damage a specific part of this ShipPiece. Return false if that piece was already damaged.
	 * @param rowWidth: how far across from the ship's origin to attack.
	 * @param colLength: how far down the ship's origin to attack.
	 * @throws ArrayIndexOutOfBoundsException: if you pass in bad coordinates.
	 * @return false if the part you damaged was already damaged.
	 */
	protected boolean damage (int row, int col) {
		if (!health[row*col + col]) return false;
		health[row*col + col] = false;
		return true;
	}

	public String toString() {
		return shipInfo.toString();
	}

}
