package com.ahuggins.warehousedemo.componentTests.TestResources;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.ahuggins.warehousedemo.models.Warehouse;


public class WarehouseData {
    
    @DataProvider(name="dp_WarehouseLists")
    public Object[][] provideWarehouseLists(){
        List<Warehouse> warehouses = createWarehouses(4);
        
        return new Object[][]{
            { warehouses.get(0), warehouses.get(1) },
            { warehouses.get(2), warehouses.get(3) }        
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
        List<Warehouse> warehouses = new ArrayList<>();
        for (int i=1; i<=numWarehouses; i++) {
            warehouses.add(new Warehouse(i, "Warehouse Name " + i, "Warehouse Location " + i, i*1234));
        }

        return warehouses;           
    }

}
