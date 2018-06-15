package com.techelevator.view;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import com.techelevator.Inventory;
import com.techelevator.Slot;

public class InventoryTest {
	
	private Inventory inventory;
	
	@Before
	public void setUp() {
		inventory = new Inventory();
	}
	
	@Test
	public void return_correct_item() {
		Map<String, Slot> inventoryMap = null;
		try {
			inventoryMap = inventory.setInventory();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Slot slot = inventoryMap.get("A1");
//		slot.item.getName()
		Assert.assertEquals("Potato Crisps", slot.getItem().getName());
	}
	
	@Test
	public void return_correct_price() {
		Map<String, Slot> inventoryMap = null;
		try {
			inventoryMap = inventory.setInventory();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Slot slot = inventoryMap.get("A1");
		Assert.assertEquals(3.05, slot.getItem().getPrice(), 0.0001);
	}
	
	@Test
	public void return_correct_type() {
		Map<String, Slot> inventoryMap = null;
		try {
			inventoryMap = inventory.setInventory();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Slot slot = inventoryMap.get("A1");
		Assert.assertEquals("Chip", slot.getItem().getType());
	}
	
	@Test
	public void return_correct_sound() {
		Map<String, Slot> inventoryMap = null;
		try {
			inventoryMap = inventory.setInventory();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Slot slot = inventoryMap.get("A1");
		Assert.assertEquals("Crunch Crunch, Yum!", slot.getItem().getSound());
	}
}
