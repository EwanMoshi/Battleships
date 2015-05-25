package main.java.logic.model;

import main.java.logic.utilities.Pair;

public class Game {


	// Fields.
	// ------------------------------------------------------------
	private GamePhase gamePhase;
	private Board board;
	private Player[] players;
	private int currentPlayer;


	// Constructors, factories, setup.
	// ------------------------------------------------------------

	private Game(){}

	public static Game NewGame (Player p1, Player p2) {
		Game g = new Game();
		g.board = new Board();
		g.gamePhase = GamePhase.SETUP;
		return g;
	}


	// Public methods.
	// ------------------------------------------------------------

	/**
	 * Sink the ship at the specified position on the board.
	 * @param player: player issuing the command.
	 * @param pair: position on the board.
	 */
	public boolean sink (Player player, Pair pair) {

		// validate this action.
		if (this.gamePhase != GamePhase.PLAYING) return false;
		if (this.gamePhase != GamePhase.SINKING) return false;
		if (!myTurn(player)) return false;

		// perform.
		this.board.sink(pair);
		return true;

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
		if (this.gamePhase != gamePhase.SETUP) return;
		this.gamePhase = gamePhase.PLAYING;
		this.currentPlayer = 0;
	}


	// Internal helper methods.
	// ------------------------------------------------------------

	private Player currentPlayer() {
		return this.players[currentPlayer];
	}


}
