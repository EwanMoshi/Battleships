package main.java.logic.model;

import java.util.Scanner;

public class ScannerTest {

	public static void main (String[] args) {
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.println("Enter: >>");
			String str = scan.nextLine();
			String[] stuff = str.split(" ");
			System.out.println("First thing: " + stuff[0]);
			System.out.println("Second thing: " + stuff[1]);
		}
	}

}
