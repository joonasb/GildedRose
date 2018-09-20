package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;
//Joonas Perunka
public class GildedRoseTest {

// Example scenarios for testing
//   Item("+5 Dexterity Vest", 10, 20));
//   Item("Aged Brie", 2, 0));
//   Item("Elixir of the Mongoose", 5, 7));
//   Item("Sulfuras, Hand of Ragnaros", 0, 80));
//   Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
//   Item("Conjured Mana Cake", 3, 6));

	@Test //BUG QUALITY SHOULD INCREASE BY 3
	public void testUpdateEndOfDay_AgedBrie_Quality_10_13() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 2, 10) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		assertEquals(13, itemBrie.getQuality());
	}
    
	@Test
	public void testUpdateEndOfDay_Conjured_Mana_Cake_3_2_6_5() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Conjured Mana Cake", 3, 6));
		
		//Act
		store.updateEndOfDay();
		
		//Assert
		List<Item> items = store.getItems();
		Item itemCake = items.get(0);
		assertEquals(2, itemCake.getSellIn());
		assertEquals(5, itemCake.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_Quality_Is_Never_Negative() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Elixir of the Mongoose", 3, 1));
		
		//Act
		store.updateEndOfDay();
		
		//Assert
		List<Item> items = store.getItems();
		Item itemElixir = items.get(0);
		assertEquals(0, itemElixir.getQuality());
		
		//Act2
		store.updateEndOfDay();
		
		//Assert2
		assertEquals(0, itemElixir.getQuality());
			
	}
	
	@Test
	public void testQualityIsNeverOver50() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 3, 49) );
		
		//Act
		store.updateEndOfDay();
		
		//Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		assertEquals(50, itemBrie.getQuality());
		
		//Act2
		store.updateEndOfDay();
		
		//Assert2
		assertEquals(50, itemBrie.getQuality());
	}
	
	@Test
	public void testOnceTheSellDateHasPassed_QualityDegradesTwiceAsFast() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("+5 Dexterity Vest", 0, 20));
		
		//Act
		store.updateEndOfDay();
		
		//Assert
		List<Item> items = store.getItems();
		Item itemDex = items.get(0);
		assertEquals(18, itemDex.getQuality());
		
		
	}
	
	@Test
	public void testBackStagePassQualityIncreasesByOne() {
		//Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
		
		//Act
		store.updateEndOfDay();
		
		//Assert
		List<Item> items = store.getItems();
		Item itemBack = items.get(0);
		assertEquals(21, itemBack.getQuality());
	}
	
	@Test
	public void testBackStagePassQualityIncreasesByTwoWhenSellingDaysLowerThan10() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 9, 20));
		
		//Act
		store.updateEndOfDay();
		
		//Assert
		List<Item> items = store.getItems();
		Item itemBack = items.get(0);
		assertEquals(22, itemBack.getQuality());
	}
	
	@Test
	public void testBackStagePassQualityIncreasesByThreeWhenSellingDaysLowerThan5() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 4, 20));
		
		//Act
		store.updateEndOfDay();
		
		//Assert
		List<Item> items = store.getItems();
		Item itemBack = items.get(0);
		assertEquals(23, itemBack.getQuality());
	}
	
	@Test
	public void testBackStagePassQualityWhenSellingDaysLowerThan0() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20));
		
		//Act
		store.updateEndOfDay();
		
		//Assert
		List<Item> items = store.getItems();
		Item itemBack = items.get(0);
		assertEquals(0, itemBack.getQuality());
	}
	
	
	@Test
	public void testSulfurasNeverSoldQualityDontDrop() {
		// Arrange 
		GildedRose store = new GildedRose();
		store.addItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
			
	}
	
	@Test // BUG Quality should increase by 2
	public void TestAgedBrieWhenSellingDaysLessThan10() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 9, 10) );
		
		//Act
		store.updateEndOfDay();
		
		//Assert
		List<Item> items = store.getItems();
		Item itemBack = items.get(0);
		assertEquals(12, itemBack.getQuality());
	}
	
	@Test // BUG Quality should be 0
	public void TestAgedBrieWhenSellingDaysZero() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 1, 10) );
		
		//Act
		store.updateEndOfDay();
		
		//Assert
		List<Item> items = store.getItems();
		Item itemBack = items.get(0);
		assertEquals(0, itemBack.getQuality());
	}
	
	@Test
	public void testQualityWhenSellingDaysZero() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("+5 Dexterity Vest", 0, 20));
		
		//Act
		store.updateEndOfDay();
		
		//Assert
		List<Item> items = store.getItems();
		Item itemDex = items.get(0);
		assertEquals(18, itemDex.getQuality());
	}
	
	

}
