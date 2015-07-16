package main.java.logic.model;

public class GameMainTest {

	public static void main (String[] args) {
	
		Player p1 = new Player("Scooby");
		Player p2 = new Player("Jim Raynor");		
		Game game = Game.NewGame(p1, p2);
		
	}
	
}
