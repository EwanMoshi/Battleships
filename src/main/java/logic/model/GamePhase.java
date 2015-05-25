package main.java.logic.model;

/**
 * The GamePhase is what part of your turn you're on.
 * @author craig
 */
public enum GamePhase {

	// player is selecting a tile to bombard.
	SINKING,

	// player has bombarded a tile.
	DONE,

	// players are setting up the ships on their boards.
	SETUP,

	// players are taking turns.
	PLAYING,

	// this comment is only here because PLAYING and SETUP have comments.
	FINISHED;

}
