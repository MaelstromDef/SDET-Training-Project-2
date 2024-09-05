package com.ahuggins.warehousedemo.componentTests.Repositories;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.componentTests.TestResources.ItemData;
import com.ahuggins.warehousedemo.componentTests.TestResources.WarehouseData;
import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.models.Item;
import com.ahuggins.warehousedemo.models.StoredItem;
import com.ahuggins.warehousedemo.models.StoredItemKey;
import com.ahuggins.warehousedemo.models.Warehouse;
import com.ahuggins.warehousedemo.repositories.AdministratorRepository;
import com.ahuggins.warehousedemo.repositories.ItemRepository;
import com.ahuggins.warehousedemo.repositories.StoredItemRepository;
import com.ahuggins.warehousedemo.repositories.WarehouseRepository;

import jakarta.transaction.Transactional;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ItemRepositoriesTests extends AbstractTestNGSpringContextTests {

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private StoredItemRepository storedItemRepo;

    @Autowired
    private WarehouseRepository warehouseRepo;

    @Autowired
    private AdministratorRepository adminRepo;


    
    private List<Item> items;
    private List<StoredItem> storedItems;
    private List<Warehouse> warehouses;
    private ItemData itemProvider;
    private WarehouseData warehouseProvider;
    private int NUM_ITEMS = 3;
    private int NUM_STORED = NUM_ITEMS-1;


    @BeforeClass
    public void setup(){
        itemProvider = new ItemData();
        warehouseProvider = new WarehouseData();
        /**
         * Item:
         *      int     id   (NOT NULL)
         *      String  name (NOT NULL)
         *      List<StoredItems>
         * 
         * StoredItem:
         *      Item      item
         *      Warehouse warehouse
         *      int       quantity  @Min=1
         * 
         * StoredItemKey
         *      Item      item
         *      Warehouse warehouse
         *      int       quantity  @Min=1
         */

        /** for these tests, we need:
         *      2 warehouses: one with stored items one without
         *      3 items: 
         *          - 2 will be saved to 1 warehouse
         *          - 1 will only be saved to item repo
         *      2 stored items:
         *          - the 2 items that are saved to the warehouse above  
         */
        warehouses = warehouseProvider.createWarehouses(2);
        items = itemProvider.createItems(NUM_ITEMS);
        storedItems = new ArrayList<>();
        
        for (int i=1; i<=NUM_STORED; i++) {
            StoredItem storedItem =  new StoredItem();
            storedItem.setId(new StoredItemKey(i, 1)); //set all items to first warehouse
            storedItem.setItem(items.get(i-1)); // last item in list will not be saved to a warehouse
            storedItem.setWarehouse(warehouses.get(0));
            storedItem.setQuantity(i);

            storedItems.add(storedItem);
        }

        /**
         * +---------+--------------+--------+
         * | Item ID | Warehouse ID | Name   | 
         * +---------+--------------+--------+
         * | 0       | -            | Item 1 |
         * +---------+--------------+--------+
         * | 1       | 1            | Item 2 |
         * | 2       | 1            | Item 3 |
         * +---------+--------------+--------+ 
         * | -       | 2            | -      |
         * +---------+--------------+--------+
         */ 
        // System.out.println("-------------Printing Items/StoredItems-----------------");
        // for (StoredItem item: storedItems) {
        //     System.out.printf(
        //         "SItem ID: %s | SItem Warehouse: %s | SItem Quantity: %s\n",
        //         item.getId().getItemId(), item.getId().getWarehouseId(), item.getQuantity()
        //     );
        // }

        // for (Item item: items) {
        //     System.out.printf(
        //         "Item ID: %s | Item Name: %s\n",
        //         item.getId(), item.getName()
        //     );
        // }

        Administrator admin = warehouses.get(0).getAdministrator();
        admin.setPassword("Valid Password");
        adminRepo.save(admin); //Should be the only admin needed
        
        for (Warehouse wh : warehouses) {
            warehouseRepo.save(wh);
        }
        for (Item item : items) {
            itemRepo.save(item);
        }
        for (StoredItem si : storedItems) {
            storedItemRepo.save(si);
        }
    }

    @AfterClass
    public void teardown() {
        itemRepo = null;
        storedItemRepo = null;
        warehouseRepo = null;
        adminRepo = null;
        items = null;
        storedItems = null;
        warehouses = null;
        itemProvider = null;
        warehouseProvider = null;
  
    }

    @Test
    public void generalTest() {
        // This is just to make sure the setup is correct
        List<Item> itemsResult = itemRepo.findAll();
        List<StoredItem> storedResult = storedItemRepo.findAll();
        Assert.assertEquals(items.size(), itemsResult.size());
        Assert.assertEquals(storedItems.size(), storedResult.size());
    }

    /** ************************************************
     *    ITEM repository tests
     *  ************************************************
     */
    @Test
    public void findByNameTest(){
        int index = 0;
        String name =  items.get(index).getName();
        String wrongName = "Wrong Name";

        Optional<Item> existingItem = itemRepo.findByName(name);
        Optional<Item> nullItem = itemRepo.findByName(wrongName);

        Assert.assertEquals( existingItem.get(), items.get(index) );
        Assert.assertTrue(nullItem.isEmpty());
    }

    /** ************************************************
     *    STOREDITEM repository tests
     *  ************************************************
     */
    @Test
    public void findByWarehouseTest(){
        List<StoredItem> warehouseItems = storedItemRepo.findByWarehouse(warehouses.get(0)); 
        List<StoredItem> nullItemList = storedItemRepo.findByWarehouse(warehouses.get(1));

        Assert.assertTrue(warehouseItems.size() == 2);
        Assert.assertTrue(nullItemList.size() == 0);
    }

    @Test
    public void findByItemTest(){
        //Item that exists in a warehouse
        Item storedItem = itemRepo.findById(1).get();
        //Item that isn't in warehouse
        Item notStoredItem = itemRepo.findById(NUM_ITEMS).get();

        List<StoredItem> itemInWarehouse = storedItemRepo.findByItem(storedItem);
        List<StoredItem> nullItem = storedItemRepo.findByItem(notStoredItem);

        Assert.assertEquals(itemInWarehouse.size(), 1);
        Assert.assertTrue(nullItem.size() == 0);
    }

    @Test
    public void findByItemAndWarehouseTest(){
        Item savedItem = items.get(0);
        Item notSavedItem = items.get(NUM_ITEMS-1);

        Warehouse fullWarehouse = warehouses.get(0);
        Warehouse emptyWarehouse = warehouses.get(1);

        List<StoredItem> existingStoredItem = storedItemRepo.findByItemAndWarehouse(savedItem, fullWarehouse);
        List<StoredItem> emptyStoredItem1 = storedItemRepo.findByItemAndWarehouse(savedItem, emptyWarehouse);
        List<StoredItem> emptyStoredItem2 = storedItemRepo.findByItemAndWarehouse(notSavedItem, fullWarehouse);
        List<StoredItem> emptyStoredItem3 = storedItemRepo.findByItemAndWarehouse(notSavedItem, emptyWarehouse);
    
        Assert.assertEquals(existingStoredItem.size(), 1);
        Assert.assertEquals(existingStoredItem.get(0).getItem(), savedItem);
        Assert.assertTrue(emptyStoredItem1.isEmpty());
        Assert.assertTrue(emptyStoredItem2.isEmpty());
        Assert.assertTrue(emptyStoredItem3.isEmpty());
    
    }

}
