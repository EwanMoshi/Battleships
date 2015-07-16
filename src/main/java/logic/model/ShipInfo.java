package main.java.logic.model;

/**
 * shipInfo encapsulates the statistics/data about a particular class of ships.
 * The actual ships on the board are Ship objects.
 * @author craig
 */
public class ShipInfo {

	// Fields.
	// ------------------------------------------------------------

	protected final int width;
	protected final int length;
	protected final String modelName;
	
	// Constructors.
	// ------------------------------------------------------------
	
	public ShipInfo (String modelName, int width, int length) {
		this.width = width;
		this.length = length;
		this.modelName = modelName;
	}
	
}
