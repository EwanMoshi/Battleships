package main.java.logic.model;

/**
 * Represents a single block of a ship.
 * @author craigaaro
 */
public class ShipBlock {

	// Fields.
	// ------------------------------------------------------------
	private final ShipInfo ship;
	private boolean destroyed;

	
	// Constructors.
	// ------------------------------------------------------------
	
	public ShipBlock (ShipInfo ship) {
		this.ship = null;
		this.destroyed = false;
	}

	
	// Protected methods.
	// ------------------------------------------------------------
	
	/**
	 * Check if this piece of the ship has been destroyed.
	 * @return boolean.
	 */
	protected boolean destroyed () {
		return this.destroyed;
	}
	
	/**
	 * Destroy this Ship Block.
	 */
	protected void destroy () {
		this.destroyed = true;
	}
	
	protected ShipInfo getShip () {
		return this.ship;
	}
	
}
