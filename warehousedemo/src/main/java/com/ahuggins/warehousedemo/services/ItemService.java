package com.ahuggins.warehousedemo.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.models.Item;
import com.ahuggins.warehousedemo.models.StoredItem;
import com.ahuggins.warehousedemo.models.StoredItemKey;
import com.ahuggins.warehousedemo.models.Warehouse;
import com.ahuggins.warehousedemo.repositories.ItemRepository;
import com.ahuggins.warehousedemo.repositories.StoredItemRepository;

@Service
public class ItemService {
    Logger logger = LoggerFactory.getLogger(getClass());    // DELETE ME

    private StoredItemRepository repo;
    private ItemRepository itemRepo;

    public ItemService(StoredItemRepository storeageRepo, ItemRepository itemRepo){
        this.repo = storeageRepo;
        this.itemRepo = itemRepo;
    }

    /**
     * Retrieves all items in a warehouse.
     * @param adminId       ID of the owning administrator.
     * @param warehouseId   ID of the warehouse to retrieve from.
     * @return              A list of all the items found in the warehouse.
     */
    public List<StoredItem> getWarehouseItems(int adminId, int warehouseId) {
        // Create warehouse for comparator
        Warehouse warehouse = new Warehouse();
        warehouse.setAdministrator(new Administrator(adminId));
        warehouse.setId(warehouseId);

        return repo.findByWarehouse(warehouse); // Return result
    }

    /**
     * Retrieves an item.
     * @param itemId    ID of the item to retrieve.
     * @return          An optional containing the item if found.
     */
    public Optional<Item> getItemById(int itemId) {
        List<StoredItem> items = repo.findByItem(new Item(itemId)); // Grab items
        
        // Handle returns
        if(items.size() == 0) return Optional.empty();
        return Optional.of(items.get(0).getItem());
    }

    /**
     * Retrieves an item from a warehouse.
     * @param adminId       ID of the owning administrator.
     * @param warehouseId   ID of the warehouse to retrieve from.
     * @param name          Name of the item to retrieve.
     * @return              An optional containing the item if found.
     */
    public Optional<Item> getItemByName(int adminId, int warehouseId, String name) {
        // Construct query components
        Item item = new Item();
        item.setName(name);
        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseId);
        warehouse.setAdministrator(new Administrator(adminId));

        // Execute
        List<StoredItem> items = repo.findByItemAndWarehouse(item, warehouse);

        // Handle returns
        if(items.isEmpty()) return Optional.empty();
        return Optional.of(items.get(0).getItem());
    }

    /**
     * Add an item to a warehouse.
     * @param adminId       ID of the owning administrator.
     * @param warehouseId   ID of warehouse to add to.
     * @param storedItem    Item to add.
     * @return              An optional containing the item, if added.
     */
    public Optional<StoredItem> addItemToWarehouse(int adminId, int warehouseId, StoredItem storedItem) {
        // Ensure that the item entity exists
        Optional<Item> fromRepo = itemRepo.findByName(storedItem.getItem().getName());
        if(fromRepo.isEmpty()) {
            fromRepo = Optional.of(itemRepo.save(storedItem.getItem()));
        }

        // Ensure that the warehouse does not already have this item
        StoredItemKey key = new StoredItemKey(fromRepo.get().getId(), warehouseId);
        if(repo.findById(key).isPresent()) return Optional.empty();

        // Construct stored item warehouse
        Warehouse warehouse = new Warehouse();
        warehouse.setAdministrator(new Administrator(adminId));
        warehouse.setId(warehouseId);

        // Add the item to the database
        storedItem.setId(key);
        storedItem.setItem(fromRepo.get());
        storedItem.setWarehouse(warehouse);
        return Optional.of(repo.save(storedItem));
    }

    /**
     * Updates an item that is in a warehouse.
     * @param adminId       ID of the owning administrator.
     * @param warehouseId   ID of the warehouse containing the item.
     * @param storedItem    Entity containing the ID of the item to update, and the information to update the item with.
     * @return              An optional containing the updated item, if it was updated.
     */
    public Optional<StoredItem> updateWarehouseItem(int adminId, int warehouseId, StoredItem storedItem) {
        // Ensure that the item entity exists
        Optional<Item> fromRepo = itemRepo.findByName(storedItem.getItem().getName());
        if(fromRepo.isEmpty()) {
            fromRepo = Optional.of(itemRepo.save(storedItem.getItem()));
        }

        // Ensure that the warehouse has this item
        StoredItemKey key = new StoredItemKey(fromRepo.get().getId(), warehouseId);
        if(repo.findById(key).isEmpty()) return Optional.empty();

        // Construct stored item warehouse
        Warehouse warehouse = new Warehouse();
        warehouse.setAdministrator(new Administrator(adminId));
        warehouse.setId(warehouseId);

        // Add the item to the database
        storedItem.setId(key);
        storedItem.setItem(fromRepo.get());
        storedItem.setWarehouse(warehouse);
        return Optional.of(repo.save(storedItem));
    }

    /**
     * Removes an item from a warehouse
     * @param adminId       ID of the owning administrator.
     * @param warehouseId   ID of the warehouse to remove an item from.
     * @param itemId        ID of the item to remove.
     * @throws IllegalAccessException   If there is no record matching the provided properties, an IllegalAccessException is thrown.
     */
    public void removeItemFromWarehouse(int adminId, int warehouseId, int itemId) throws IllegalAccessException {
        // Ensure ownership
        Warehouse warehouse = new Warehouse();
        warehouse.setAdministrator(new Administrator(adminId));
        warehouse.setId(warehouseId);

        Item item = new Item(itemId);

        if(repo.findByItemAndWarehouse(item, warehouse).size() != 1) throw new IllegalAccessException();

        // Remove from database
        StoredItemKey itemKey = new StoredItemKey(itemId, warehouseId);
        repo.deleteById(itemKey);
    }   
}
