package com.ahuggins.warehousedemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahuggins.warehousedemo.models.StoredItem;
import com.ahuggins.warehousedemo.models.StoredItemKey;
import com.ahuggins.warehousedemo.models.Warehouse;

import java.util.List;
import com.ahuggins.warehousedemo.models.Item;

/*
 * Connects with the item_locations table in the database and allows Spring JPA operations.
 */
public interface StoredItemRepository extends JpaRepository<StoredItem, StoredItemKey>{
    public List<StoredItem> findByWarehouse(Warehouse warehouse);
    public List<StoredItem> findByItem(Item item);
    public List<StoredItem> findByItemAndWarehouse(Item item, Warehouse warehouse);
}
