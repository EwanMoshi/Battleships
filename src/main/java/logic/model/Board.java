package main.java.logic.model;

import main.java.logic.utilities.Pair;


public class Board {

	// Constants.
	// ------------------------------------------------------------
	public static final int WIDTH = 10;

	
	// Instance variables.
	// ------------------------------------------------------------
	private ShipBlock[][] grid;
	
	protected Board () {
		this.grid = new ShipBlock[WIDTH][WIDTH];
	}

	
	// Package methods.
	// ------------------------------------------------------------
	
	/**
	 * Sink the ship block at the specified position.
	 * @param pair, the (row,col) to sink.
	 * @return boolean, whether anything was hit.
	 */
	protected boolean sink (Pair pair) {
		ShipBlock block = grid[pair.x][pair.y];
		if (block == null) return false;
		block.destroy();
		return true;
	}
	
}
