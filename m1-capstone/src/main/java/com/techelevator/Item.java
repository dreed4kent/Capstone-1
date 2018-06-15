package com.techelevator;

public class Item {

	private String name;
	private double price;
	private String type;
	
	public Item(String name, double price, String type) {
		this.name = name;
		this.price = price;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public double getPrice() {
		return price;
	}

	public String getType() {
		return type;
	}
	
	public String getSound() {
		String sound = null;
		if (type.equals("Candy")) sound = "Munch Munch, Yum!";
		if (type.equals("Chip")) sound = "Crunch Crunch, Yum!";
		if (type.equals("Drink")) sound = "Glug Glug, Yum!";
		if (type.equals("Gum")) sound = "Chew Chew, Yum!";
		return sound;
		
	}

}
