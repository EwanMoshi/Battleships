package main.java.logic.model;

public class Game {


	// Fields.
	// ------------------------------------------------------------
	private GamePhase gamePhase;
	private ShipPiece[][] boards;
	private Player[] players;
	private int currentPlayer;

	public final static int BOARD_WIDTH = 10;


	// Constructors, factories, setup.
	// ------------------------------------------------------------

	private Game(){}

	public static Game NewGame (Player p1, Player p2) {
		Game g = new Game();
		g.boards = new ShipPiece[][]{
			new ShipPiece[BOARD_WIDTH*BOARD_WIDTH],
			new ShipPiece[BOARD_WIDTH*BOARD_WIDTH]
		};
		g.gamePhase = GamePhase.SETUP;
		g.players = new Player[]{ p1, p2 };
		return g;
	}


	// Public methods.
	// ------------------------------------------------------------

	/**
	 * Sink the ship at the specified position on the board.
	 * @param player: player issuing the command.
	 * @param pair: position on the board.
	 * @return true if successfully sunk a piece; false otherwise.
	 */
	public boolean sink (Player player, int row, int col) {

		// Validate this action.
		if (this.gamePhase != GamePhase.PLAYING) return false;
		if (this.gamePhase != GamePhase.SINKING) return false;
		if (!myTurn(player)) return false;

		// Get the opponent's board.
		ShipPiece[] board = opponentsBoard(player);
		ShipPiece piece = board[row*BOARD_WIDTH + col];
		if (piece == null) return false;

		// Move up and left until you find the origin of the piece at that point on the board.
		// This relies on the fact that the shape of ships is rectangular.
		int rowToHit = row; int colToHit = col;
		while (row > 0 && board[(row-1)*BOARD_WIDTH + col] == piece) row--;
		while (col > 0 && board[row*BOARD_WIDTH + (col-1)] == piece) col--;

		// Perform array arithmetic to find out offset relative to the ship's origin.
		return piece.damage(rowToHit - row, colToHit - col);

	}

	/**
	 * Check to see if it's your turn to make a move.
	 * @param player: player to check.
	 * @return boolean.
	 */
	public boolean myTurn (Player player) {
		return currentPlayer() == player;
	}

	/**
	 * End the current player's turn.
	 * @param player: player who issued command to end turn.
	 * @return boolean: whether the turn was ended.
	 */
	public boolean endTurn (Player player) {
		if (!myTurn(player)) return false;
		this.currentPlayer = (this.currentPlayer + 1) % players.length;
		return true;
	}

	/**
	 * End setup and start playing the game.
	 */
	public void beginGame () {
		if (this.gamePhase != GamePhase.SETUP) return;
		this.gamePhase = GamePhase.PLAYING;
		this.currentPlayer = 0;
	}

	/**
	 * Attempt to place a certain kind of ship for a player at the specified location.
	 * If successful, creates a new ShipPiece on the player's board and returns true.
	 * If unsucessful, returns false.
	 * @param player: player placing the piece.
	 * @param ship2place: kind of ship being placed.
	 * @param row: where to place piece on the board.
	 * @param col: where to place piece on the board.
	 * @return true if piece was placed; false otherwise.
	 */
	public boolean placePiece (Player player, ShipInfo ship2place, int row, int col, Orientation o) {

		// Check if you're in the set up phase. Get the appropriate board.
		if (this.gamePhase != GamePhase.SETUP) return false;
		ShipPiece[] board = myBoard(player);

		// TODO: take orientation into account.
		// If the orientation is right, do what's here now.
		// If the orientation is down, transpose row and col.
		// If the orientation is left, fill in backwards (move the row back by SHIP_WIDTH amount)
		// If the orientation is up, transpose both row and fill in backwards.

		// Check you can place the ship.
		for (int i = row; i < row + ship2place.width; i++) {
			if (i < 0 || i >= Game.BOARD_WIDTH) return false;
			for (int j = col; j < col + ship2place.length; j++) {
				// Check for out of bounds.
				if (j < 0 || j >= Game.BOARD_WIDTH) return false;
				// Check if another piece is already here.
				if (board[i*BOARD_WIDTH + j] != null) return false;
			}
		}

		// Make a new ShipPiece. Place it at the location.
		ShipPiece piece = new ShipPiece(ship2place, o);
		for (int i = row; i < row + ship2place.width; i++) {
			for (int j = col; j < col + ship2place.length; j++) {
				int pos = i*BOARD_WIDTH + j;
				board[pos] = piece;
			}
		}
		return true;

	}

	public ShipPiece[] board (Player pl) {
		for (int i = 0; i < players.length; i++) {
			if (players[i] == pl) return boards[i];
		}
		return null;
	}


	// Internal helper methods.
	// ------------------------------------------------------------

	private ShipPiece[] opponentsBoard (Player player) {
		return player == players[0] ? boards[1] : boards[0];
	}

	private ShipPiece[] myBoard (Player player) {
		return player == players[0] ? boards[0] : boards[1];
	}

	private Player currentPlayer() {
		return this.players[currentPlayer];
	}


}
