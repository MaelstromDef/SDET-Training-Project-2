package com.ahuggins.warehousedemo.componentTests.Services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.componentTests.TestResources.WarehouseData;
import com.ahuggins.warehousedemo.dtos.WarehouseDto;
import com.ahuggins.warehousedemo.mappers.WarehouseMapper;
import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.models.Warehouse;
import com.ahuggins.warehousedemo.repositories.WarehouseRepository;
import com.ahuggins.warehousedemo.services.WarehouseService;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    //#region Tests

    /**
     * Tests the getAllWarehouses method when used with a correct and an incorrect admin id.
     */
    @Test(dataProvider = "dp_WarehouseLists", dataProviderClass = WarehouseData.class)
    public void testGetAllWarehouses(Warehouse[] warehouses){
        Administrator admin = new Administrator(warehouses[0].getAdministrator().getId());
        List<Warehouse> list = Arrays.asList(warehouses);

        // Set up mocks
        when(repo.findByAdministrator(any(Administrator.class))).thenReturn(Arrays.asList());
        when(repo.findByAdministrator(admin)).thenReturn(list);

        // Test
        Assert.assertEquals(service.getAllWarehouses(admin.getId()), list);
        Assert.assertNotEquals(service.getAllWarehouses(admin.getId() + 1), list);
    }

    /**
     * Tests the getWarehouseById method when used with a correct and incorrect
     * admin ID, and a correct and incorrect warehouse ID.
     */
    @Test(dataProvider = "dp_Warehouses", dataProviderClass = WarehouseData.class)
    public void testGetWarehouseById(Warehouse warehouse){
        int adminId = warehouse.getAdministrator().getId();
        int warehouseId = warehouse.getId();
        Administrator lookupAdmin = new Administrator(adminId);

        // Typically return empty
        when(repo.findByIdAndAdministrator(anyInt(), any(Administrator.class)))
            .thenReturn(Optional.empty());
        // Return warehouse on specific
        when(repo.findByIdAndAdministrator(warehouseId, lookupAdmin))
            .thenReturn(Optional.of(warehouse));

        // Tests

        Assert.assertEquals(service.getWarehouseById(adminId, warehouseId).get(), warehouse);
        Assert.assertTrue(service.getWarehouseById(adminId, warehouseId + 1).isEmpty());
        Assert.assertTrue(service.getWarehouseById((adminId + 1), warehouseId).isEmpty());
        Assert.assertTrue(service.getWarehouseById((adminId + 1), (warehouseId + 1)).isEmpty());
    }

    /**
     * Tests the getWarehouseByName method when used with a correct and incorrect
     * admin ID, and a correct and incorrect warehouse name.
     */
    @Test(dataProvider = "dp_Warehouses", dataProviderClass = WarehouseData.class)
    public void testGetWarehouseByName(Warehouse warehouse){
        Administrator lookupAdmin = new Administrator(warehouse.getAdministrator().getId());
        when(repo.findByNameAndAdministrator(anyString(), any(Administrator.class))).thenReturn(Optional.empty());
        when(repo.findByNameAndAdministrator(warehouse.getName(), lookupAdmin))
            .thenReturn(Optional.of(warehouse));

        Assert.assertEquals(service.getWarehouseByName(lookupAdmin.getId(), warehouse.getName()).get(), warehouse);
        Assert.assertTrue(service.getWarehouseByName(lookupAdmin.getId(), warehouse.getName() + "bad").isEmpty());
        Assert.assertTrue(service.getWarehouseByName(lookupAdmin.getId() + 1, warehouse.getName()).isEmpty());
        Assert.assertTrue(service.getWarehouseByName(lookupAdmin.getId() + 1, warehouse.getName() + "bad").isEmpty());
    }

    /**
     * Tests the getWarehouseByLocation method when used with a correct and incorrect
     * admin ID, and a correct and incorrect location.
     */
    @Test(dataProvider = "dp_WarehouseLists", dataProviderClass = WarehouseData.class)
    public void testGetWarehouseByLocation(Warehouse[] warehouses){
        Administrator admin = new Administrator(warehouses[0].getAdministrator().getId());
        ArrayList<Warehouse> ofLocation = new ArrayList<Warehouse>();
        String location = warehouses[0].getLocation();
        
        for(int i = 0; i < warehouses.length; i++){
            if(warehouses[i].getLocation().equals(location))
                ofLocation.add(warehouses[i]);
        }

        // Set up mocks
        when(repo.findByLocationAndAdministrator(anyString(), any(Administrator.class))).thenReturn(Arrays.asList());
        when(repo.findByLocationAndAdministrator(location, admin)).thenReturn(ofLocation);

        // Test
        Assert.assertEquals(service.getWarehouseByLocation(admin.getId(), location), ofLocation);
        Assert.assertTrue(service.getWarehouseByLocation(admin.getId(), location + "bad").isEmpty());
        Assert.assertTrue(service.getWarehouseByLocation(admin.getId() + 1, location).isEmpty());
        Assert.assertTrue(service.getWarehouseByLocation(admin.getId() + 1, location + "bad").isEmpty());
    }

    /**
     * Tests the createWarehouse method when a non-existing and an existing
     * warehouse are used.
     */
    @Test(dataProvider = "dp_Warehouses", dataProviderClass = WarehouseData.class)
    public void testCreateWarehouse(Warehouse warehouse){
        Administrator lookupAdmin = new Administrator(warehouse.getAdministrator().getId());
        WarehouseDto expectedDto = new WarehouseDto(warehouse.getId(), warehouse.getName(), warehouse.getLocation(), warehouse.getSize());
        when(repo.save(warehouse)).thenReturn(warehouse);
        when(mapper.toDto(warehouse)).thenReturn(expectedDto);

        // Doesn't exist
        when(repo.findByIdOrNameAndAdministrator(warehouse.getId(), warehouse.getName(), lookupAdmin))
            .thenReturn(Optional.empty());

        Assert.assertEquals(service.createWarehouse(lookupAdmin.getId(), warehouse).get(), expectedDto);

        // Exists
        when(repo.findByIdOrNameAndAdministrator(warehouse.getId(), warehouse.getName(), lookupAdmin))
            .thenReturn(Optional.of(warehouse));

        Assert.assertTrue(service.createWarehouse(lookupAdmin.getId(), warehouse).isEmpty());
    }

    /**
     * Tests the updateWarehouse method when a non-existing and an existing
     * warehouse are used.
     */
    @Test(dataProvider = "dp_Warehouses", dataProviderClass = WarehouseData.class)
    public void testUpdateWarehouse(Warehouse warehouse){
        Administrator lookupAdmin = new Administrator(warehouse.getAdministrator().getId());
        WarehouseDto expectedDto = new WarehouseDto(warehouse.getId(), warehouse.getName(), warehouse.getLocation(), warehouse.getSize());
        warehouse.setAdministrator(lookupAdmin);
        when(repo.save(warehouse)).thenReturn(warehouse);
        when(mapper.toDto(warehouse)).thenReturn(expectedDto);

        // Doesn't exist
        when(repo.findByIdAndAdministrator(warehouse.getId(), lookupAdmin)).thenReturn(Optional.empty());
        Assert.assertTrue(service.updateWarehouse(lookupAdmin.getId(), warehouse.getId(), warehouse).isEmpty());

        // Exists
        when(repo.findByIdAndAdministrator(warehouse.getId(), lookupAdmin)).thenReturn(Optional.of(warehouse));
        Assert.assertEquals(service.updateWarehouse(lookupAdmin.getId(), warehouse.getId(), warehouse).get(), expectedDto);
    }

    /**
     * Tests the deleteWarehouse method when the warehouse does and does not
     * exist under an administrator.
     */
    @Test(dataProvider = "dp_Warehouses", dataProviderClass = WarehouseData.class)
    public void testDeleteWarehouse(Warehouse warehouse){
        Administrator lookupAdmin = new Administrator(warehouse.getAdministrator().getId());
        doThrow(new RuntimeException("Calling repo.deleteById()")).when(repo).deleteById(warehouse.getId());

        // Doesn't exist
        when(repo.findByIdAndAdministrator(warehouse.getId(), lookupAdmin)).thenReturn(Optional.empty());

        try{
            service.deleteWarehouse(lookupAdmin.getId(), warehouse.getId());
        }catch(Exception e){
            fail("repo.deleteById was called when the warehouse did not exist under given administrator.");
        }

        // Exists (deleteById should now throw)
        when(repo.findByIdAndAdministrator(warehouse.getId(), lookupAdmin)).thenReturn(Optional.of(warehouse));
        assertThrows(Exception.class, () -> service.deleteWarehouse(lookupAdmin.getId(), warehouse.getId()));
    }

    //#endregion
}
