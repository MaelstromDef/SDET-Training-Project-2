package com.ahuggins.warehousedemo.componentTests.TestResources;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.models.Warehouse;


public class WarehouseData {
    
    @DataProvider(name="dp_WarehouseLists")
    public Object[][] provideWarehouseLists(){
        List<Warehouse> warehouses = createWarehouses(6);
        warehouses.get(5).setLocation(warehouses.get(0).getLocation());
        
        return new Object[][]{
            { warehouses.get(0), warehouses.get(1) },
            { warehouses.get(2), warehouses.get(3) },
            { warehouses.get(0), warehouses.get(4), warehouses.get(5)}
        };
    }

    @DataProvider(name="dp_Warehouses")
    public Object[][] provideWarehouses(){
        List<Warehouse> warehouses = createWarehouses(2);
        
        return new Object[][]{
            { warehouses.get(0) },
            { warehouses.get(1) }
        };
    }

    private List<Warehouse> createWarehouses(int numWarehouses) {
        Administrator admin = new Administrator(1, "Company 1");
        List<Warehouse> warehouses = new ArrayList<>();
        for (int i=1; i<=numWarehouses; i++) {
            Warehouse warehouse = new Warehouse(i, "Warehouse Name " + i, "Warehouse Location " + i, i*1234);
            warehouse.setAdministrator(admin);
            warehouses.add(warehouse);
        }

        return warehouses;           
    }
}
