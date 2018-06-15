package com.techelevator.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import com.techelevator.Item;

public class ItemTest {
	
	private Item item;
	
	@Before
	public void setUp() {
		item = new Item ("Moonpie", 1.80, "Candy");
	}
	
	@Test
	public void test_for_correct_sound_returned() {
		Assert.assertEquals("Munch Munch, Yum!", item.getSound());
	}
	
	@Test
	public void test_for_correct_price_returned() {
		Assert.assertEquals(1.80, item.getPrice(), 0.0001);
	}
	
	@Test
	public void test_for_correct_type_returned() {
		Assert.assertEquals("Candy", item.getType());
	}
	
	@Test
	public void test_for_correct_name_returned() {
		Assert.assertEquals("Moonpie", item.getName());
	}

}
