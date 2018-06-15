package com.techelevator.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import com.techelevator.Item;
import com.techelevator.Slot;

public class SlotTest {
	
	private Slot slot;
	private Item item;
	
	@Before
	public void setUp() {
		item = new Item("Moonpie", 1.80, "Candy");
		slot = new Slot(item, 5);
	}
	
	@Test
	public void does_the_correct_quantity_display() {
		Assert.assertEquals(5, slot.getQty());
	}
	
	@Test
	public void does_the_correct_name_show() {
		Assert.assertEquals("Moonpie", slot.getItem().getName());
	}
	
	@Test
	public void does_correct_price_show() {
		Assert.assertEquals(1.80, slot.getItem().getPrice(), 0.0001);
	}
	
	@Test
	public void does_correct_type_show() {
		Assert.assertEquals("Candy", slot.getItem().getType());
	}

}
