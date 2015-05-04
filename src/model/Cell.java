package model;

/**
 * Represents a single tile on the board. It may or may not have a piece of ship on it.
 * @author craigaaro
 */
public class Cell {

	private final int x;
	private final int y;
	private Ship ship;
	private boolean sunken;

	public Cell (int x_, int y_) {
		this.x = x_;
		this.y = y_;
		this.ship = null;
		this.sunken = false;
	}

	/**
	 * Set the ship that is at this grid.
	 * @param s: the ship.
	 * @throws IllegalStateException: if there's already a ship here.
	 */
	public void setShip (Ship s) {
		if (this.ship != null) throw new IllegalStateException("Already put a ship on here.");
		this.ship = s;
	}

	public boolean hasShip () {
		return this.ship != null;
	}

	/**
	 * Return true if this grid has been destroyed.
	 * @return boolean
	 */
	public boolean sunken () {
		return this.sunken;
	}

	/**
	 * Sink this cell.
	 */
	public void sink () {
		this.sunken = true;
	}

}
