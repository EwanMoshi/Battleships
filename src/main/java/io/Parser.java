package main.java.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import main.java.logic.model.ShipInfo;

public class Parser {

	private Parser() {}

	private static Map<String, ShipInfo> shipInfoDB;

	private static ShipInfo parseShip (String name, BufferedReader br)
	throws IOException {

		// Null every attribute.
		String model = null;
		Integer width, length;
		width = length = null;

		// Parse each attribute which appears in declaration.
		String line;
		while ((line=br.readLine()) != null) {

			if (line.isEmpty()) continue;
			String[] attribs = line.split(":");
			String attrib = attribs[0];

			switch (attrib) {

			case "model":
				model = attribs[1];
				break;
			case "size":
				String[] dimensions = attribs[1].split("x");
				width = Integer.parseInt(dimensions[0]);
				length = Integer.parseInt(dimensions[1]);
				break;

			}
		}

		// Check every attribute has been declared.
		if (model == null || width == null || length == null)
			throw new IOException("Not every attribute has been declared for the ship " + name);

		// Create and return specified ShipInfo class.
		return new ShipInfo(model, width, length);
	}

	private static void LoadShipInfoDB (String fname)
	throws IOException {
		File file = new File(fname);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		while ((line=br.readLine()) != null) {
			if (line.isEmpty()) continue;
			String[] attribs = line.split(":");
			if (attribs[0].equals("name")) {
				String name = attribs[1];
				shipInfoDB.put(name, parseShip(name, br));
			}
		}
	}

	public static ShipInfo LoadShipInfo(String name) throws IOException {
		if (shipInfoDB == null) LoadShipInfoDB("res" + File.separatorChar + "ships.craigml");
		ShipInfo ship = shipInfoDB.get(name);
		if (ship == null) throw new IOException("No ship called " + name + " to load.");
		return ship;
	}


}
