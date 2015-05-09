package main.java.model;

/**
 * The game state is recorded as an enum.
 * @author craig
 */
public enum GameState {
	
	// players are setting up the ships on their boards.
	SETUP,
		
	// players are taking turns.
	PLAYING,
	
	// this comment is only here because PLAYING and SETUP have comments.
	FINISHED;
	
}