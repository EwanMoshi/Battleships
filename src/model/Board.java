package model;

public class Board {

	// Constants.
	// ------------------------------------------------------------
	public static final int WIDTH = 10;

	// Instance variables.
	// ------------------------------------------------------------
	private Cell[][] grid;

	// Constructors.
	// ------------------------------------------------------------
	public Board () {
		this.grid = new Cell[WIDTH][WIDTH];
	}

	public void sink (int row, int col) {
		Cell c = this.grid[row][col];
		c.sink();
	}

}
