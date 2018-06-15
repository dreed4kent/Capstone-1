package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Inventory {
	private static String path;
	Map<String, Slot> inventory = new HashMap<String, Slot>();
	
	public Map<String, Slot> setInventory() throws FileNotFoundException {
		path = "vendingmachine.csv";
		File inputFile = getFileByPath(path);
		try (Scanner fileScanner = new Scanner(inputFile)) {
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				String key = line.substring(0,2);
				int nameEnd = line.indexOf("|",3);
				String name = line.substring(3, nameEnd);
				int priceEnd = line.indexOf("|",nameEnd+1);
				double price = Double.parseDouble(line.substring(nameEnd +1, priceEnd));
				String type = line.substring(priceEnd+1);
				inventory.put(key, new Slot(new Item(name, price, type),5));
			}
			
		}
		return inventory;
	}
	
	private static File getFileByPath(String path) {

		File inputFile = new File(path);
		if (inputFile.exists() == false) { // checks for the existence of a file
			System.out.println(path + " does not exist");
			System.exit(1); // Ends the program
		} else if (inputFile.isFile() == false) {
			System.out.println(path + " is not a file");
			System.exit(1); // Ends the program
		}
		return inputFile;
	}
	
	
	
}
