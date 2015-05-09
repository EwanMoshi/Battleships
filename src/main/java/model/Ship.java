package main.java.model;

/**
 * A Ship is a collection of data about a particular piece on the game board.
 * @author craig
 */
public class Ship {

	// Fields.
	// ------------------------------------------------------------
	
	private final ShipBlock[] blocks;
	
	
	// Constructors.
	// ------------------------------------------------------------
	
	private Ship (ShipBlock[] blocks) {
		this.blocks = blocks;
	}
	
	public static Ship MakeShip (ShipBlock[] blocks) {
		return new Ship(blocks);
	}
	
	
	// Protected methods.
	// ------------------------------------------------------------

	/**
	 * Check if any part of the ship has been destroyed.
	 * @return boolean.
	 */
	protected boolean damaged () {
		for (ShipBlock bl : this.blocks) {
			if (bl.destroyed()) return true;
		}
		return false;
	}
	
	/**
	 * Check if the entirety of the ship has been destroyed.
	 * @return boolean.
	 */
	protected boolean destroyed () {
		for (ShipBlock bl : this.blocks) {
			if (!bl.destroyed()) return false;
		}
		return true;
	}
	
}
