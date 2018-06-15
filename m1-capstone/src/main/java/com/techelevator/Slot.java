package com.techelevator;

public class Slot {

	private Item item;
	private int qty;

	public Slot(Item item, int qty) {
		this.item = item;
		this.qty = qty;
	}

	public Item getItem() {
		return item;
	}

	public int getQty() {
		return qty;
	}
	
	

}
