package com.ahuggins.warehousedemo.componentTests;


import static org.junit.jupiter.api.Assertions.assertTimeout;

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
    }


    @Test
    public void testWarehouses () {
        // TODO: Create Tests for Warehouses
    }


    @Test
    public void testItem () {
        // TODO: Create Tests for Item
    }
    
    @Test
    public void testStoredItems () {
         // TODO: Create Tests for StoredItem / StoredItemKey
    }
       
    @AfterSuite
    public void tearDown () {
        user = null;
        item = null;
        storedItem = null;
        storedItemKey = null;
        warehouse = null;
    }

}
