package com.ahuggins.warehousedemo.componentTests.Services;

import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.models.Item;
import com.ahuggins.warehousedemo.models.StoredItem;
import com.ahuggins.warehousedemo.models.StoredItemKey;
import com.ahuggins.warehousedemo.models.Warehouse;
import com.ahuggins.warehousedemo.repositories.ItemRepository;
import com.ahuggins.warehousedemo.repositories.StoredItemRepository;
import com.ahuggins.warehousedemo.services.ItemService;

import java.util.List;

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

    //#region Data

    @DataProvider(name="dp_StoredItems")
    public Object[][] provideStoredItems(){
        StoredItem item1 = new StoredItem();
        StoredItem item2 = new StoredItem();

        item1.setId(new StoredItemKey(1, 1));
        item1.setQuantity(1);

        item2.setId(new StoredItemKey(2, 2));
        item2.setQuantity(2);

        return new Object[][]{
            {item1},
            {item2}
        };
    }

    @DataProvider(name="dpStoredItemLists")
    public Object[][] provideStoredItemLists(){
        StoredItem item1 = new StoredItem();
        StoredItem item2 = new StoredItem();
        StoredItem item3 = new StoredItem();
        StoredItem item4 = new StoredItem();

        item1.setId(new StoredItemKey(1, 1));
        item1.setQuantity(1);

        item2.setId(new StoredItemKey(2, 2));
        item2.setQuantity(2);

        item3.setId(new StoredItemKey(3, 3));
        item3.setQuantity(3);

        item4.setId(new StoredItemKey(4, 4));
        item4.setQuantity(4);

        return new Object[][]{
            {item1, item2},
            {item3, item4}
        };
    }

    @DataProvider(name="dp_Items")
    public Object[][] provideItems(){
        Item item1 = new Item(1);
        Item item2 = new Item(2);

        item1.setName("Item 1");
        item2.setName("Item 2");

        return new Object[][]{
            {item1},
            {item2}
        };
    }

    //#endregion

    //#region Tests

    @Test(dataProvider = "dp_StoredItemLists")
    public void testGetWarehouseItems(List<StoredItem> items){
        // Set up mocks
        Warehouse warehouse = new Warehouse();
        warehouse.setAdministrator(new Administrator(1));
        warehouse.setId(1);

        when(repo.findByWarehouse(warehouse)).thenReturn(items);

        // Test
        Assert.assertEquals(service.getWarehouseItems(1, 1), items);
    }

    @Test
    public void testGetItemById(){

    }

    @Test
    public void testGetItemByName(){

    }

    @Test
    public void testAddItemToWarehouse(){

    }

    @Test
    public void testUpdateWarehouseItem(){

    }

    @Test
    public void testRemoveItemFromWarehouse(){

    }

    //#endregion
}
