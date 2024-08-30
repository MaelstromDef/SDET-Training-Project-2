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
