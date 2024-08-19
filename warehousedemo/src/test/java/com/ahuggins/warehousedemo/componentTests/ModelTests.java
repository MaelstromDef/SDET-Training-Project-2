package com.ahuggins.warehousedemo.componentTests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.models.Item;
import com.ahuggins.warehousedemo.models.StoredItem;
import com.ahuggins.warehousedemo.models.StoredItemKey;
import com.ahuggins.warehousedemo.models.Warehouse;

/**
 * Administrator:   
 *      int id
 *      String companyName (len = 255)
 *      String password (len = 255)
 *      List<Warehouse> warehouses
 * 
 * Item:
 *      int id
 *      String name (len=255, unique)
 *      List<StoredItem> storedItems
 * 
 * StoredItem:
 *      StoredItemKey id
 *      Item item
 *      Warehouse Warehouse
 *      int quantity
 * 
 * StoredItemKey;
 *      int ItemId;
 *      int warehouseId;
 * 
 * Warehouses:
 *      int id;
 *      String name;
 *      String location;
 *      int size;
 * 
 */
public class ModelTests {
    private Administrator user;
    private Item item;
    private StoredItem storedItem;
    private StoredItemKey storedItemKey;
    private Warehouse warehouse;

    @BeforeSuite //Initial code to run before beginning tests
    public void setup (){
        System.out.println("Starting Setup");
        System.out.println("Assigning Blank Models");

        user = new Administrator();
        item = new Item();
        storedItem = new StoredItem();
        storedItemKey = new StoredItemKey();
        warehouse = new Warehouse();

        System.out.println("Finished Setup");
    }

    
    @Test
    public void testAdministrator () {
        /** * Administrator:   
         *      int id
         *      String companyName (len = 255)
         *      String password (len = 255)
         *      List<Warehouse> warehouses
         */

        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add( new Warehouse(13, "name13", "location13", 1300) );
        warehouses.add( new Warehouse(6, "name6", "location6", 6000) );

        /** ***************************************************
         * NO ARG CONSTRUTOR TESTING
         ******************************************************/
        System.out.println("Starting Tests for No Arg Administrator");     
        
        user.setId(20);
        user.setCompanyName("Company Name 1");
        user.setPassword("Password 1");
        user.setWarehouses(warehouses);

        assertTrue(user.getId() == 20);
        assertEquals(user.getCompanyName(), "Company Name 1" );
        assertEquals(user.getPassword(), "Password 1" );
        assertEquals(user.getWarehouses(), warehouses);
        
        // reset model for possible future tests
        user = new Administrator();

        /** ***************************************************
         * ALL ARG CONSTRUTOR TESTING
         ******************************************************/
        System.out.println("Starting Tests for All Arg Administrator");
        Administrator user2 = new Administrator( 3, "Company Name 2");
        user2.setPassword("Password 2");
        user2.setWarehouses(warehouses);

        assertTrue(user2.getId() == 3);
        assertEquals(user2.getCompanyName(), "Company Name 2" );
        assertEquals(user2.getPassword(), "Password 2" );
        assertEquals(user2.getWarehouses(), warehouses);

        System.out.println("Finished Testing Administrator Model");
    }


    @Test
    public void testWarehouses () {
        // TODO: add on any tests for extra methods
        /**
         * Warehouses:
         *      int id;
         *      String name;
         *      String location;
         *      int size;
         */

        /** ***************************************************
         * NO ARG CONSTRUTOR TESTING
         ******************************************************/
        System.out.println("Starting Tests for No Arg Warehouse");     
        
        warehouse.setId(20);
        warehouse.setName("Warehouse 1");
        warehouse.setLocation("Location 1");
        warehouse.setSize(2000);

        assertTrue(warehouse.getId() == 20);
        assertEquals(warehouse.getName(), "Warehouse 1" );
        assertEquals(warehouse.getLocation(), "Location 1" );
        assertTrue(warehouse.getSize() == 2000);
        
        // reset model for possible future tests
        warehouse = new Warehouse();

        /** ***************************************************
         * ALL ARG CONSTRUTOR TESTING
         ******************************************************/
        System.out.println("Starting Tests for All Arg Warehouse");
        Warehouse warehouse2 = new Warehouse( 3, "Warehouse 2", "Location 2", 1234);

        assertTrue(warehouse2.getId() == 3);
        assertEquals(warehouse2.getName(), "Warehouse 2" );
        assertEquals(warehouse2.getLocation(), "Location 2" );
        assertTrue(warehouse2.getSize() == 1234);

        System.out.println("Finished Testing Warehouse Model");
    }


    @Test
    public void testItem () {
        // TODO: Create Tests for Item

        /**
         * Item:
         *      int id
         *      String name (len=255, unique)
         *      List<StoredItem> storedItems
         */
        List<StoredItem> mockStoredItems =  new ArrayList<>();
        mockStoredItems.add(new StoredItem());
        mockStoredItems.add(new StoredItem());

        /** ***************************************************
         * NO ARG CONSTRUTOR TESTING
         ******************************************************/
        System.out.println("Starting Tests for Item");     
        
        item.setId(20);
        item.setName("Item Name 1");
        item.setStoredItems(mockStoredItems);

        assertTrue(item.getId() == 20);
        assertEquals(item.getName(), "Item Name 1");
        assertEquals(item.getStoredItems(), mockStoredItems);

        
        // reset model for possible future tests
        item = new Item();

        /** ***************************************************
         * ALL ARG CONSTRUTOR TESTING
         ******************************************************/
        Item item2 = new Item(3);
        assertTrue(item2.getId() == 3);
        
        System.out.println("Finished Testing form Item Model");
    }
    
    @Test
    public void testStoredItems () {
         // TODO: add on any extra tests for extra methods
        /**
         * StoredItem:
         *      StoredItemKey id
         *      Item item
         *      Warehouse Warehouse
         *      int quantity
         * 
         * StoredItemKey;
         *      int ItemId;
         *      int warehouseId;
         */
        Item mockItem = new Item(1);
        mockItem.setName("mockItem");
        Warehouse mockWarehouse = new Warehouse(2, "mock Warehouse", "mock Location", 13);
        StoredItemKey mockId = new StoredItemKey(1, 2);

        /** ***************************************************
         * StoredItemKey CONSTRUTOR TESTING
         ******************************************************/
        System.out.println("Starting Tests for StoredItem Key");

        storedItemKey.setItemId(3);
        storedItemKey.setItemId(4);
        assertTrue(storedItemKey.getItemId() == 3);
        assertTrue(storedItemKey.getWarehouseId() == 4);
        assertTrue(mockId.getItemId() == 1);
        assertTrue(mockId.getWarehouseId() == 2);

        // reset model for possible future tests
        storedItemKey = new StoredItemKey();

        /** ***************************************************
         * NO ARG CONSTRUTOR TESTING
         ******************************************************/
        System.out.println("Starting Tests for No Arg StoredItem");     
        
        storedItem.setId(mockId);
        storedItem.setItem(mockItem);
        storedItem.setWarehouse(mockWarehouse);
        storedItem.setQuantity(1234);

        assertEquals(storedItem.getId(), mockId);
        assertEquals(storedItem.getItem(), mockItem );
        assertEquals(storedItem.getWarehouse(), mockWarehouse );
        assertTrue(storedItem.getQuantity() == 1234);
        
        // reset model for possible future tests
        storedItem = new StoredItem();

        System.out.println("Finished Testing storedItem and storedItemKey Models");
    }
       
    @AfterSuite
    public void tearDown () {
        System.out.println("Tearing Down Blank Models");

        user = null;
        item = null;
        storedItem = null;
        storedItemKey = null;
        warehouse = null;
    }

}
