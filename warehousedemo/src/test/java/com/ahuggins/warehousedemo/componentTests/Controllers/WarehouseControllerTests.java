package com.ahuggins.warehousedemo.componentTests.Controllers;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.controllers.WarehouseController;
import com.ahuggins.warehousedemo.models.Item;
import com.ahuggins.warehousedemo.models.Warehouse;
import com.ahuggins.warehousedemo.services.WarehouseService;


public class WarehouseControllerTests {
        @Mock 
    private WarehouseService warehouseService;

    @InjectMocks
    private WarehouseController warehouseController;

    private AutoCloseable closeable;

    @BeforeClass
    public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterClass
    public void teardown() throws Exception{
        closeable.close();
    }

    @DataProvider(name="WarehouseList")
    public Object[][] provideItemList(){
        return new Object[][] {{
            new Warehouse(1, "Warehouse Name 1", "Location 1", 1000),
            new Warehouse(1, "Warehouse Name 2", "Location 2", 2000),
            new Warehouse(1, "Warehouse Name 3", "Location 3", 3000)
        }};
    }


    
    @Test(dataProvider = "ItemDtoList")
    public void getAllWarehousesTest(Warehouse[] warehouses){
        //TODO: Setup Mock Information and Methods
        
        //TODO: Test All Branches of Method
    }

    @Test(dataProvider = "ItemDtoList")
    public void getWarehouseByIdTest(Warehouse[] warehouses){
        //TODO: Setup Mock Information and Methods
        
        //TODO: Test All Branches of Method
    }

    @Test(dataProvider = "ItemDtoList")
    public void getWarehouseByNameTest(Warehouse[] warehouses){
        //TODO: Setup Mock Information and Methods
        
        //TODO: Test All Branches of Method
    }

    @Test(dataProvider = "ItemDtoList")
    public void getWarehouseByLocationTest(Warehouse[] warehouses){
        //TODO: Setup Mock Information and Methods
        
        //TODO: Test All Branches of Method
    }

    @Test(dataProvider = "ItemDtoList")
    public void createWarehouseTest(Warehouse[] warehouses){
        //TODO: Setup Mock Information and Methods
        
        //TODO: Test All Branches of Method
    }

    @Test(dataProvider = "ItemDtoList")
    public void updateWarehouseTest(Warehouse[] warehouses){
        //TODO: Setup Mock Information and Methods
        
        //TODO: Test All Branches of Method
    }
    
    @Test(dataProvider = "ItemDtoList")
    public void deleteWarehouseTest(Warehouse[] warehouses){
        //TODO: Setup Mock Information and Methods
        
        //TODO: Test All Branches of Method
    }
    
}
