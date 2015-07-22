package main.java.logic.model;

import java.io.IOException;
import java.util.Scanner;

import main.java.io.Parser;

public class GameMainTest {

	private static Player[] players;
	private static Game game;
	private static final Scanner scan = new Scanner(System.in);

	public static void main (String[] args) {
		players = new Player[]{ new Player("Scooby"), new Player("Jim Raynor") };
		game = Game.NewGame(players[0], players[1]);
		setup();
		play();
	}

	private static ShipInfo[] shipsToPlace ()
	throws IOException {
		ShipInfo carrier = Parser.LoadShipInfo("carrier");
		ShipInfo battleship = Parser.LoadShipInfo("battleship");
		ShipInfo destroyer = Parser.LoadShipInfo("destroyer");
		return new ShipInfo[]{ carrier, battleship, battleship, destroyer, destroyer, destroyer };
	}

	private static void play() {
		// TODO Auto-generated method stub

	}

	private static void setup() {

		ShipInfo[] ships2place = null;
		try {
			ships2place = shipsToPlace();
		}
		catch (IOException ioe) {
			System.err.println("Error loading ship info data.");
			System.exit(1);
		}

		for (int i = 0; i < ships2place.length; i++) {
			placeShip(0, ships2place[i]);
			placeShip(1, ships2place[i]);
		}

	}

	private static boolean isNum (String string) {
		for (int i = 0; i < string.length(); i++) {
			if (!Character.isDigit(string.charAt(i))) return false;
		}
		return true;
	}

	private static void placeShip (int playerIndex, ShipInfo ship) {
		
		Player player = players[playerIndex];
		ShipPiece[] board = game.board(player);
		System.out.println("== PLAYER " + playerIndex);
		while (true) {

			// Prompt player to place their piece.
			drawBoard(board);
			String description = ship.modelName + "("+ship.width + " x " + ship.length + ")";
			System.out.println("Your turn to place, " + player + ". Please place your " + description + "\n");
			System.out.println("type \"l\" or \"r\" to rotate your ship.\n");

			// Check for malformed input.
			String[] line = scan.nextLine().split(" ");
			if (line.length != 2 || !isNum(line[0]) || !isNum(line[1])) {
				System.out.println("Bad input. Please enter like so \"ROW COL\" where ROW and COL are numbers.");
				continue;
			}

			// Check if specified location is in bounds.
			int row = Integer.parseInt(line[0]);
			int col = Integer.parseInt(line[1]);
			if (row < 0 || col < 0 || row >= Game.BOARD_WIDTH || col >= Game.BOARD_WIDTH) {
				System.out.println("Specified location is out of bounds of game board.");
				continue;
			}

			// Check if something in the way.
			if (!game.placePiece(player, ship, row, col)) {
				System.out.println("Could not place at specified location. Try again.");
				continue;
			}

			System.out.println();
			drawBoard(board);

			break;
		}
	}

	public static void drawBoards (Game game, Player[] players) {
		System.out.println("== PLAYER ONE");
		drawBoard(game.board(players[0]));
		System.out.println("== PLAYER TWO");
		drawBoard(game.board(players[1]));
	}

	public static void drawBoard (ShipPiece[] board) {
		System.out.print("  ");
		for (int i = 0; i < Game.BOARD_WIDTH; i++) System.out.print(i);
		System.out.println();
		for (int row = 0; row < Game.BOARD_WIDTH; row++) {
			System.out.print(row + " ");
			for (int col = 0; col < Game.BOARD_WIDTH; col++) {
				ShipPiece sh = board[row*Game.BOARD_WIDTH + col];
				if (sh == null) System.out.print(".");
				else System.out.print(sh);
			}
			System.out.println();
		}
		System.out.println();
	}

}
