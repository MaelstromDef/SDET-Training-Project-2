package com.ahuggins.warehousedemo.componentTests;

import org.testng.annotations.BeforeSuite;

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
    // TODO: Create Tests for Warehouses
    // TODO: Create Tests for Item
    // TODO: Create Tests for Administrator
    // TODO: Create Tests for StoredItem / StoredItemKey


    @BeforeSuite //Initial code to run before beginning tests
    public void setup (){}

}
