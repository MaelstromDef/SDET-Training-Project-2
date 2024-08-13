package com.ahuggins.warehousedemo.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class StoredItemKey implements Serializable{
    @Column(name="item_id")
    @NotNull
    private int itemId;

    @Column(name="warehouse_id")
    @NotNull
    private int warehouseId;

    public StoredItemKey(){}
    public StoredItemKey(int itemId, int warehouseId){
        this.itemId = itemId;
        this.warehouseId = warehouseId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + itemId;
        result = prime * result + warehouseId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StoredItemKey other = (StoredItemKey) obj;
        if (itemId != other.itemId)
            return false;
        if (warehouseId != other.warehouseId)
            return false;
        return true;
    }
}
