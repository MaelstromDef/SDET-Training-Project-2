package com.ahuggins.warehousedemo.componentTests.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.componentTests.TestResources.ItemData;
import com.ahuggins.warehousedemo.controllers.ItemController;
import com.ahuggins.warehousedemo.dtos.AdministratorDto;
import com.ahuggins.warehousedemo.models.Administrator;
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

    
    @Test(dataProvider = "dp_StoredItemLists", dataProviderClass = ItemData.class)
    public void getWarehouseItemsTest(StoredItem[] items){
        //Setup Mock Information and Methods
        List<StoredItem> expectedItems = Arrays.asList(items);
        when(itemService.getWarehouseItems(1, 2)).thenReturn(expectedItems);

        //Test All Branches of Method
        assertEquals(expectedItems, itemController.getWarehouseItems(1, 2));
    }

    @Test(dataProvider = "dp_Items", dataProviderClass = ItemData.class)
    public void getItemByIdTest(Item[] item){
        //Setup Mock Information and Methods
        Optional<Item> expectedItem = Optional.of(item[0]);
        Optional<Item> nullItem = Optional.empty();
        when(itemService.getItemById(1) ).thenReturn(expectedItem);
        when(itemService.getItemById(2) ).thenReturn(nullItem);
        
        //Test All Branches of Method
        assertEquals(expectedItem.get(), itemController.getItemById(999, 999, 1));
        assertThrows(ResponseStatusException.class, () -> {
            itemController.getItemById(999, 999, 2);
        });
    }

    @Test(dataProvider = "dp_Items", dataProviderClass = ItemData.class)
    public void getItemByNameTest(Item[] item){
        //Setup Mock Information and Methods
        Optional<Item> expectedItem = Optional.of(item[0]);
        Optional<Item> nullItem = Optional.empty();
        when(itemService.getItemByName(1, 1, "Item Name 1") ).thenReturn(expectedItem);
        when(itemService.getItemByName(2, 2, "Item Name 2") ).thenReturn(nullItem);
        
        //Test All Branches of Method
        assertEquals(expectedItem.get(), itemController.getItemByName(1, 1, "Item Name 1"));
        assertThrows(ResponseStatusException.class, () -> {
            itemController.getItemByName(2, 2, "Item Name 2");
        });
    }

    @Test(dataProvider = "dp_StoredItemLists", dataProviderClass = ItemData.class)
    public void addItemToWarehouseTest(StoredItem[] storedItems){
        //Setup Mock Information and Methods
        List<StoredItem> itemList = Arrays.asList(storedItems);
        StoredItem dummyItem = itemList.get(0);
        StoredItem itemInOptional = itemList.get(1);
        Optional<StoredItem> expectedItem = Optional.of(itemInOptional);
        Optional<StoredItem> nullItem = Optional.empty();
        when(itemService.addItemToWarehouse(1, 1, dummyItem) ).thenReturn(expectedItem);
        when(itemService.addItemToWarehouse(2, 2, dummyItem) ).thenReturn(nullItem);
        
        //Test All Branches of Method
        assertEquals(expectedItem.get(), itemController.addItemToWarehouse(1, 1, dummyItem));
        assertThrows(ResponseStatusException.class, () -> {
            itemController.addItemToWarehouse(2, 2, dummyItem);
        });
    }

    @Test(dataProvider = "dp_StoredItemLists", dataProviderClass = ItemData.class)
    public void updateWarehouseItemTest(StoredItem[] storedItems){
        //Setup Mock Information and Methods
        List<StoredItem> itemList = Arrays.asList(storedItems);
        StoredItem dummyItem = itemList.get(0);
        StoredItem itemInOptional = itemList.get(1);
        Optional<StoredItem> expectedItem = Optional.of(itemInOptional);
        Optional<StoredItem> nullItem = Optional.empty();
        when(itemService.updateWarehouseItem(1, 1, dummyItem) ).thenReturn(expectedItem);
        when(itemService.updateWarehouseItem(2, 2, dummyItem) ).thenReturn(nullItem);
        
        //Test All Branches of Method
        assertEquals(expectedItem.get(), itemController.updateWarehouseItem(1, 1, dummyItem));
        assertThrows(ResponseStatusException.class, () -> {
            itemController.updateWarehouseItem(2, 2, dummyItem);
        });
    }

    @Test
    public void removeItemFromWarehouseTest(){
        try {
            //Setup Mock Information and Methods
            doNothing().when(itemService).removeItemFromWarehouse(1, 1, 1);
            doThrow(new IllegalAccessException()).when(itemService).removeItemFromWarehouse(2, 2, 2);
        
            //Test All Branches of Method
            itemController.removeItemFromWarehouse(1, 1, 1);
            itemController.removeItemFromWarehouse(2, 2, 2);
            verify(itemService).removeItemFromWarehouse(1,1,1);
            verify(itemService).removeItemFromWarehouse(2,2,2);
        } catch (IllegalAccessException e) {e.printStackTrace();}
        
    }
}
