package com.techelevator;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS,
													   MAIN_MENU_OPTION_PURCHASE };
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
			PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION };
	
	private Scanner in;
	private Menu menu;
	private static int balance = 0;
	List<Item> listOfPurchasedItems = new ArrayList<Item>();
	Inventory inventory= new Inventory();
	Map<String, Item> intList;
	
	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}
	
	public void run() throws FileNotFoundException {
		intList = inventory.setInventory();
		while(true) {
			String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			
			if(choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
			} else if(choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				purchase();
			}
		}
	}
	
	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
	
	public void purchase() {
		String purchaseChoice = (String)menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
		
		
		
		if(purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
			feedMoney();
		} else if(purchaseChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
			sale();
		} else if(purchaseChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
			finishTransaction();
		}
	}
	
	public void feedMoney() {
		System.out.println("Please insert 1, 2, 5, or 10 dollar bills");
		String userInput = in.nextLine();
		boolean flag = false;
		int input = 0;
		int [] bills = { 1, 2, 5, 10 };
		try {
			input = Integer.valueOf(userInput);
			for(int i = 0; i < bills.length; i++) {
				if(input == bills[i]) {
					flag = true;
				};
			}
		} catch(NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		
		if (!flag) {
			System.out.println("You entered an invalid bill");
			feedMoney();
		}
		
		balance += input;
	}
	
	public void sale() {
		showInventory();
		System.out.println("Please enter your selection");
		String userChoice = in.nextLine();
		boolean flag = false;
		String [] slots = { "A1", "A2" };
			for(int i = 0; i < slots.length; i++) {
				if(userChoice.equals(slots[i])) {
					flag = true;
				}
			}
			if (!flag) {
				System.out.println("Your choice is not recognized");
				sale();
			}
		if (productIsAvailable) {
			// reduce inventory
			listOfPurchasedItems.add(inventory.get(userChoice));
			// reduce balance
			// print log file
		} else {
			System.out.println("This item is Sold Out");
			sale();
		}
	}
	
	private void showInventory() {
		
		
	}

	public void finishTransaction() {
		for (Item i : listOfPurchasedItems) {
			System.out.println(i.getSound());
		}
		
		// print log file
		
		double [] coins = { 0.25, 0.10, 0.05 };
		String [] coinNames = { "Quarter ", "Dime ", "Nickel " };
		while (balance > 0) {
			for (int j = 0; j < 3; j++) {
				if (balance >= coins[j]) {
					balance -= coins[j];
					System.out.println(coinNames[j]);
				}
			}
			
		}
		
	}
}
