package com.ahuggins.warehousedemo.componentTests.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.controllers.ItemController;
import com.ahuggins.warehousedemo.models.Item;
import com.ahuggins.warehousedemo.models.StoredItem;
import com.ahuggins.warehousedemo.models.StoredItemKey;
import com.ahuggins.warehousedemo.models.Warehouse;
import com.ahuggins.warehousedemo.services.ItemService;


public class ItemControllerTests {
    @Mock 
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    private AutoCloseable closeable;

    @BeforeClass
    public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterClass
    public void teardown() throws Exception{
        closeable.close();
    }

    //#region Data

    /**
     * Provides StoredItem objects for testing.
     */
    @DataProvider(name="dp_StoredItems")
    public Object[][] provideStoredItems(){
        StoredItem item1 = new StoredItem();
        StoredItem item2 = new StoredItem();
        Item someItem = new Item(1);
        someItem.setName("Item");

        item1.setId(new StoredItemKey(1, 1));
        item1.setQuantity(1);
        item1.setItem(someItem);

        item2.setId(new StoredItemKey(2, 2));
        item2.setQuantity(2);
        item2.setItem(someItem);

        return new Object[][]{
            {item1},
            {item2}
        };
    }


    
    @Test(dataProvider = "ItemDtoList")
    public void getWarehouseItemsTest(Item[] items){
        //TODO: Setup Mock Information and Methods
        
        //TODO: Test All Branches of Method
    }

    @Test(dataProvider = "ItemList")
    public void getItemByIdTest(Item[] items){
        //TODO: Setup Mock Information and Methods
        
        //TODO: Test All Branches of Method
    }

    @Test(dataProvider = "ItemList")
    public void getItemByNameTest(Item[] items){
        //TODO: Setup Mock Information and Methods
        
        //TODO: Test All Branches of Method
    }

    @Test(dataProvider = "ItemList")
    public void addItemToWarehouseTest(Item[] items){
        //TODO: Setup Mock Information and Methods
        
        //TODO: Test All Branches of Method
    }

    @Test(dataProvider = "ItemDtoList")
    public void updateWarehouseItemTest(Item[] items){
        //TODO: Setup Mock Information and Methods
        
        //TODO: Test All Branches of Method
    }

    @Test(dataProvider = "ItemDtoList")
    public void removeItemFromWarehouseTest(Item[] items){
        //TODO: Setup Mock Information and Methods
        
        //TODO: Test All Branches of Method
    }
}
