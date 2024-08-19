package com.ahuggins.warehousedemo.componentTests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
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
        user = null;

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
        System.out.println("Tearing Down Blank Models");

        user = null;
        item = null;
        storedItem = null;
        storedItemKey = null;
        warehouse = null;
    }

}
