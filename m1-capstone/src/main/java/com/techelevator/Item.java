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
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSound() {
		String sound = null;
		if (type.equals("candy")) sound = "Munch Munch, Yum!";
		if (type.equals("chip")) sound = "Crunch Crunch, Yum!";
		if (type.equals("drink")) sound = "Glug Glug, Yum!";
		if (type.equals("gum")) sound = "Chew Chew, Yum!";
		return sound;
		
	}

}
