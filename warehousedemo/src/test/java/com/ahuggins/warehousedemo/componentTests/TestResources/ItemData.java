package com.ahuggins.warehousedemo.componentTests.TestResources;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.ahuggins.warehousedemo.models.Item;
import com.ahuggins.warehousedemo.models.StoredItem;
import com.ahuggins.warehousedemo.models.StoredItemKey;

public class ItemData {
    
    

     /**
     * Provides StoredItem objects for testing.
     */
    @DataProvider(name="dp_StoredItems")
    public Object[][] provideStoredItems(){
        List<StoredItem> storedItems = createStoreItems(2);

        return new Object[][]{
            {storedItems.get(0)},
            {storedItems.get(1)}
        };
    }

    /**
     * Provides StoredItem Lists for testing.
     */
    @DataProvider(name="dp_StoredItemLists")
    public Object[][] provideStoredItemLists(){
        List<StoredItem> storedItems = createStoreItems(4);
        System.out.println(storedItems);
        return new Object[][]{
            {storedItems.get(0), storedItems.get(1)},
            {storedItems.get(2), storedItems.get(3)}
        };
    }

    /**
     * Provides Item objects for testing.
     */
    @DataProvider(name="dp_Items")
    public Object[][] provideItems(){
        List<Item> items = createItems(2);

        return new Object[][]{
            {items.get(0)},
            {items.get(1)}
        };
    }

    public List<Item> createItems(int numItems) {
        
        List<Item> items= new ArrayList<>();
        for (int i=1; i<=numItems; i++) {
            Item item = new Item(i);
            item.setName("Item Name " + i);
            items.add(item);
        }

        return items;
    }

    public List<StoredItem> createStoreItems(int numStoredItems) {
        
        List<StoredItem> storedItems= new ArrayList<>();
        for (int i=1; i<=numStoredItems; i++) {
            StoredItem storedItem = new StoredItem();

            storedItem.setId(new StoredItemKey(i, i));
            storedItem.setItem(new Item(i));
            storedItem.setQuantity(i);

            storedItems.add(storedItem);
        }

        return storedItems;
    }

}
