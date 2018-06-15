package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE };
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
			PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION };

	private Scanner in;
	private Menu menu;

	private static double balance = 0;
	Inventory inventory = new Inventory();
	File logFile = new File("log.txt");
	String fileName = "Sales Report" + LocalDateTime.now() + ".txt";
	File salesReport = new File(fileName);
	Map<String, Slot> intList;
	Map<Item, Integer> soldItems = new HashMap<Item, Integer>();
	List<Item> listOfPurchasedItems = new ArrayList<Item>();

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		this.in = new Scanner(System.in);
	}

	public void run() throws FileNotFoundException {
		intList = inventory.setInventory();
		salesReport = new File(fileName);
		PrintWriter eraser = new PrintWriter(logFile);
		eraser.print("");
		eraser.close();
		try {
			logFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				showInventory();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				purchase();
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}

	public void purchase() {

		String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

		if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
			feedMoney();
			purchase();
		} else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
			sale();
			purchase();
		} else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
			finishTransaction();
		}
	}

	public void feedMoney() {
		System.out.println("Please insert 1, 2, 5, or 10 dollar bills");
		String userInput = in.nextLine();
		boolean flag = false;
		int input = 0;
		int[] bills = { 1, 2, 5, 10 };
		try {
			input = Integer.valueOf(userInput);
			for (int i = 0; i < bills.length; i++) {
				if (input == bills[i]) {
					flag = true;
				}
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will
			// be null
		}

		if (!flag) {
			System.out.println("You entered an invalid bill");
			feedMoney();
		} else {
			balance += (double) input;
			printBalance(balance);
			writeToLogFile("FEED MONEY", (double) input);
		}
	}

	public void sale() {
		showInventory();
		System.out.println("Please enter your selection >>>>>");
		String userChoice = in.nextLine().toUpperCase();
		boolean flag = false;

		for (Map.Entry<String, Slot> entry : intList.entrySet()) {
			String key = entry.getKey();
			if (userChoice.equals(key)) {
				flag = true;
			}
		}
		if (!flag) {
			System.out.println("\nYour choice is not recognized. Please try again!\n");
			sale();
		}

		if (balance >= intList.get(userChoice).getItem().getPrice()) {
			if (intList.get(userChoice).getQty() > 0) {
				// reduce inventory
				int s = intList.get(userChoice).getQty();
				intList.put(userChoice, new Slot(intList.get(userChoice).getItem(), s - 1));
				// adding to list of purchased items
				listOfPurchasedItems.add(intList.get(userChoice).getItem());
				// reduce balance
				balance -= intList.get(userChoice).getItem().getPrice();
				System.out.println("You got " + intList.get(userChoice).getItem().getName());
				printBalance(balance);
				// print log file
				String outputToFile = intList.get(userChoice).getItem().getName() + "  " + userChoice;
				writeToLogFile(outputToFile, -1 * intList.get(userChoice).getItem().getPrice());
				writeToSalesReport(intList.get(userChoice).getItem());
			} else {
				System.out.println("This item is Sold Out\n");
				purchase();
			}

		} else {
			System.out.println("Your don't have enough money to buy this item.");
			System.out.println("Please add money or make another choice");
			printBalance(balance);
			purchase();
		}
	}

	public void finishTransaction() {
		for (Item i : listOfPurchasedItems) {
			System.out.println(i.getSound());
		}
		System.out.println("\nYour change is:\n");
		// print log file
		double changeAmount = -balance;
		double[] coins = { 0.25, 0.10, 0.05 };
		String[] coinNames = { "Quarter ", "Dime ", "Nickel " };

		for (int j = 0; j < 3; j++) {
			while (balance > 0.0) {
				//balance = Math.round(balance * 1000)/1000;
				if (balance >= coins[j]) {
					balance -= coins[j];
					System.out.println(coinNames[j]);
				} else {
					break;
				}
			}
		}
		writeToLogFile("GIVE CHANGE", changeAmount);
	}

	public void showInventory() {
		System.out.println("Slot    Item Name           Price   ");
		System.out.println("===================================");
		for (Map.Entry<String, Slot> entry : intList.entrySet()) {
			String key = entry.getKey();
			Slot value = entry.getValue();

			String output = String.format("%-5s %-20s $%6.2f", key, value.getItem().getName(), value.getItem().getPrice());

			System.out.println(output);
		}
	}

	public void printBalance(double b) {
		System.out.println(String.format("%-15s $%6.2f", "Your balance is : ", b));
	}

	public void writeToLogFile(String actionType, double balanceChange) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(logFile, true));
			String output = String.format("%-5s %-10s $%4.2f $%4.2f", LocalDateTime.now(), actionType,
					balance - balanceChange, balance);
			writer.println(output);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeToSalesReport(Item item) {
		if (!soldItems.containsKey(item)) {
			soldItems.put(item, 0);
		}

		soldItems.put(item, soldItems.get(item) + 1);

		PrintWriter eraser;
		try {
			eraser = new PrintWriter(salesReport);
			eraser.print("");
			eraser.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		double totalSales = 0;
		for (Map.Entry<Item, Integer> entry : soldItems.entrySet()) {
			Item key = entry.getKey();
			Integer value = entry.getValue();
			String output = key.getName() + " | " + value;
			totalSales += key.getPrice() * value;
			try {
				PrintWriter writer = new PrintWriter(new FileWriter(salesReport, true));
				writer.println(output);
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(salesReport, true));
			writer.print("\n**TOTALSALES**  $");
			writer.println(totalSales);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
