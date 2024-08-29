package com.ahuggins.warehousedemo.componentTests.Services;

import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.mappers.WarehouseMapper;
import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.models.Warehouse;
import com.ahuggins.warehousedemo.repositories.WarehouseRepository;
import com.ahuggins.warehousedemo.services.WarehouseService;

import java.util.Arrays;
import java.util.List;

public class WarehouseServiceTests {
    @Mock
    private WarehouseRepository repo;
    @Mock
    private WarehouseMapper mapper;

    @InjectMocks
    private WarehouseService service;
    private AutoCloseable closeable;

    @BeforeClass
    public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
        service = new WarehouseService(repo, mapper);
    }

    @AfterClass
    public void teardown() throws Exception{
        closeable.close();
    }

    //#region Data

    /**
     * Provides Warehouse objects for use in testing.
     */
    @DataProvider(name="dp_Warehouses")
    public Object[][] provideWarehouses(){
        Administrator admin = new Administrator(1, "Company 1");
        Warehouse warehouse1 = new Warehouse(1, "Warehouse 1", "Location 1", 1);
        Warehouse warehouse2 = new Warehouse(2, "Warehouse 2", "Location 2", 2);

        warehouse1.setAdministrator(admin);
        warehouse2.setAdministrator(admin);

        return new Object[][]{
            {warehouse1},
            {warehouse2}
        };
    }

    /**
     * Provides Warehouse lists for use in testing.
     */
    @DataProvider(name="dp_WarehouseLists")
    public Object[][] provideWarehouseLists(){
        Administrator admin = new Administrator(1, "Company 1");
        Warehouse warehouse1 = new Warehouse(1, "Warehouse 1", "Location 1", 1);
        Warehouse warehouse2 = new Warehouse(2, "Warehouse 2", "Location 2", 2);
        Warehouse warehouse3 = new Warehouse(3, "Warehouse 3", "Location 3", 3);
        Warehouse warehouse4 = new Warehouse(4, "Warehouse 4", "Location 4",4);

        warehouse1.setAdministrator(admin);
        warehouse2.setAdministrator(admin);
        warehouse3.setAdministrator(admin);
        warehouse4.setAdministrator(admin);

        return new Object[][]{
            {warehouse1, warehouse2},
            {warehouse3, warehouse4}
        };
    }

    //#endregion

    //#region Tests

    @Test(dataProvider = "dp_WarehouseLists")
    public void testGetAllWarehouses(Warehouse[] warehouses){
        Administrator admin = new Administrator(warehouses[0].getAdministrator().getId());
        List<Warehouse> list = Arrays.asList(warehouses);

        // Set up mocks
        when(repo.findByAdministrator(admin)).thenReturn(list);

        // Test
        Assert.assertEquals(service.getAllWarehouses(admin.getId()), list);
    }

    @Test
    public void testGetWarehouseById(){
        
    }

    @Test
    public void testGetWarehouseByName(){

    }

    @Test
    public void testGetWarehouseByLocation(){

    }

    @Test
    public void testCreateWarehouse(){

    }

    @Test
    public void testUpdateWarehouse(){

    }

    @Test
    public void testDeleteWarehouse(){

    }

    //#endregion
}
