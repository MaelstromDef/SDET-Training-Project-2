/*
 * Notice the distinction between a StoredItem and an Item in these models.
 * This was caused due to an m:n relationship between items and warehouses, since
 * a number of items could be stored at a number of warehouses. In order to account for
 * this and reduce redundancies, a standard m:n relationship between items and warehouses
 * was established, with an added "quantity" field to indicate the number of items within a
 * single warehouse.
 */

package com.ahuggins.warehousedemo.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name="item_locations")
public class StoredItem {
    @EmbeddedId
    @JsonIgnore
    private StoredItemKey id;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name="item_id")
    private Item item;
    
    @ManyToOne
    @MapsId("warehouseId")
    @JoinColumn(name="warehouse_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Warehouse warehouse;

    @Min(value = 1)
    int quantity;

    public StoredItemKey getId() {
        return id;
    }

    public void setId(StoredItemKey id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((item == null) ? 0 : item.hashCode());
        result = prime * result + ((warehouse == null) ? 0 : warehouse.hashCode());
        result = prime * result + quantity;
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
        StoredItem other = (StoredItem) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (item == null) {
            if (other.item != null)
                return false;
        } else if (!item.equals(other.item))
            return false;
        if (warehouse == null) {
            if (other.warehouse != null)
                return false;
        } else if (!warehouse.equals(other.warehouse))
            return false;
        if (quantity != other.quantity)
            return false;
        return true;
    }
}
