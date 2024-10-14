/**
 * By: Aaron Huggins
 * 
 * Description:
 *      Tests the ItemService class.
 */

package com.ahuggins.warehousedemo.componentTests.Services;

import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.componentTests.TestResources.ItemData;
import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.models.Item;
import com.ahuggins.warehousedemo.models.StoredItem;
import com.ahuggins.warehousedemo.models.StoredItemKey;
import com.ahuggins.warehousedemo.models.Warehouse;
import com.ahuggins.warehousedemo.repositories.ItemRepository;
import com.ahuggins.warehousedemo.repositories.StoredItemRepository;
import com.ahuggins.warehousedemo.services.ItemService;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;

public class ItemServiceTests {
    @Mock
    private StoredItemRepository repo;
    @Mock 
    private ItemRepository itemRepo;

    @InjectMocks
    private ItemService service;
    private AutoCloseable closeable;

    @BeforeClass
    public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
        service = new ItemService(repo, itemRepo);
    }

    @AfterClass
    public void teardown() throws Exception{
        closeable.close();
    }

    //#region Tests

    /**
     * Tests the getWarehouseItems method.
     * @param items StoredItem list to test with.
     */
    @Test(dataProvider = "dp_StoredItemLists", dataProviderClass = ItemData.class)
    public void testGetWarehouseItems(StoredItem[] items){
        // Set up mocks
        Warehouse warehouse = new Warehouse();
        warehouse.setAdministrator(new Administrator(1));
        warehouse.setId(1);

        List<StoredItem> itemsList = Arrays.asList(items);
        when(repo.findByWarehouse(warehouse)).thenReturn(itemsList);

        // Test
        Assert.assertEquals(service.getWarehouseItems(1, 1), itemsList);
    }

    /**
     * Tests the getItemById method.
     */
    @Test(dataProvider = "dp_Items", dataProviderClass = ItemData.class)
    public void testGetItemById(Item item){
        // Set up mocks.
        StoredItem storedItem = new StoredItem();
        storedItem.setItem(item);
        when(repo.findByItem(new Item(item.getId())))
            .thenReturn(Arrays.asList(storedItem));

        // Tests
        Assert.assertEquals(service.getItemById(item.getId()).get(), item);
        Assert.assertTrue(service.getItemById(item.getId() + 1).isEmpty());
    }

    /**
     * Tests the getItemByName method.
     */
    @Test(dataProvider = "dp_Items", dataProviderClass = ItemData.class)
    public void testGetItemByName(Item item){
        // Set up mocks
        Item mockItem = new Item();
        mockItem.setName(item.getName());
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1);
        warehouse.setAdministrator(new Administrator(1));

        StoredItem storedItem = new StoredItem();
        storedItem.setItem(item);

        when(repo.findByItemAndWarehouse(mockItem, warehouse)).thenReturn(Arrays.asList(storedItem));

        // Tests
        Assert.assertEquals(service.getItemByName(1, 1, item.getName()).get(), item);
        Assert.assertTrue(service.getItemByName(0, 1, item.getName()).isEmpty());
    }

    /**
     * Tests the addItemToWarehouse method. Does so by recreating
     * scenarios based on:
     *  - Item does/does not already exist.
     *  - Warehouse does/does not contain this item.
     */
    @Test(dataProvider = "dp_StoredItems", dataProviderClass = ItemData.class)
    public void testAddItemToWarehouse(StoredItem storedItem){
        // SETUP

        // Data
        int adminId = 1;
        int warehouseId = 1;

        Item item = storedItem.getItem();
        Optional<Item> optionalItem = Optional.of(item);
        Optional<StoredItem> optionalStoredItem = Optional.of(storedItem);

        StoredItemKey key = new StoredItemKey(item.getId(), warehouseId);

        // Mocks
        when(itemRepo.save(item)).thenReturn(item);
        when(repo.save(storedItem)).thenReturn(storedItem);

        // TESTS
        
        // Item exists : Warehouse does contain
        when(itemRepo.findByName(item.getName())).thenReturn(optionalItem);
        when(repo.findById(key)).thenReturn(optionalStoredItem);
        
        Assert.assertTrue(service.addItemToWarehouse(adminId, warehouseId, storedItem).isEmpty());

        // Item exists : warehouse does not contain
        when(repo.findById(key)).thenReturn(Optional.empty());

        Assert.assertEquals(service.addItemToWarehouse(adminId, warehouseId, storedItem), optionalStoredItem);

        // Item does not exist : warehouse does contain
        when(itemRepo.findByName(item.getName())).thenReturn(Optional.empty());
        when(repo.findById(key)).thenReturn(optionalStoredItem);

        Assert.assertTrue(service.addItemToWarehouse(adminId, warehouseId, storedItem).isEmpty());

        // Item does not exist : warehouse does not contain
        when(repo.findById(key)).thenReturn(Optional.empty());

        Assert.assertEquals(service.addItemToWarehouse(adminId, warehouseId, storedItem), optionalStoredItem);

    }

    /**
     * Tests the updateWarehouseItem method. Does so by recreating scenarios based on:
     *  - Item does/does not exist.
     *  - Warehouse does/does not contain the item.
     */
    @Test(dataProvider = "dp_StoredItems", dataProviderClass = ItemData.class)
    public void testUpdateWarehouseItem(StoredItem storedItem){
        // SETUP

        // Data
        int adminId = 1;
        int warehouseId = 1;

        Item item = storedItem.getItem();
        Optional<Item> optionalItem = Optional.of(item);
        Optional<StoredItem> optionalStoredItem = Optional.of(storedItem);

        StoredItemKey key = new StoredItemKey(item.getId(), warehouseId);

        // Mocks
        when(itemRepo.save(item)).thenReturn(item);
        when(repo.save(storedItem)).thenReturn(storedItem);

        // TESTS

        // Item exists : warehouse does contain
        when(itemRepo.findByName(item.getName())).thenReturn(optionalItem);
        when(repo.findById(key)).thenReturn(optionalStoredItem);

        Assert.assertEquals(service.updateWarehouseItem(adminId, warehouseId, storedItem), optionalStoredItem);

        // Item exists : warehouse does not contain
        when(repo.findById(key)).thenReturn(Optional.empty());

        Assert.assertTrue(service.updateWarehouseItem(adminId, warehouseId, storedItem).isEmpty());

        // Item does not exist : warehouse does contain
        when(itemRepo.findByName(item.getName())).thenReturn(Optional.empty());
        when(repo.findById(key)).thenReturn(optionalStoredItem);

        Assert.assertEquals(service.updateWarehouseItem(adminId, warehouseId, storedItem), optionalStoredItem);

        // Item does not exist : warehouse does not contain
        when(repo.findById(key)).thenReturn(Optional.empty());

        Assert.assertTrue(service.updateWarehouseItem(adminId, warehouseId, storedItem).isEmpty());
    }

    /**
     * Tests the removeItemFromWarehouse method.
     * @param storedItem
     */
    @Test(dataProvider = "dp_StoredItems", dataProviderClass = ItemData.class)
    public void testRemoveItemFromWarehouse(StoredItem storedItem){
        // Create data models
        int adminId = 1;
        int warehouseId = 1;
        Warehouse warehouse = new Warehouse();
        warehouse.setAdministrator(new Administrator(adminId));
        warehouse.setId(warehouseId);

        Item item = new Item(storedItem.getItem().getId());
        
        // Set up mocks
        when(repo.findByItemAndWarehouse(item, warehouse)).thenReturn(Arrays.asList(storedItem));

        // Test
        try {
			service.removeItemFromWarehouse(adminId, warehouseId, item.getId());
		} catch (IllegalAccessException e) {
			Assert.fail("Access denied during valid removal.");
		}
        
    }

    //#endregion
}
