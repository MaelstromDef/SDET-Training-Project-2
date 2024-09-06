package com.ahuggins.warehousedemo.componentTests.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.componentTests.TestResources.WarehouseData;
import com.ahuggins.warehousedemo.controllers.WarehouseController;
import com.ahuggins.warehousedemo.dtos.WarehouseDto;
import com.ahuggins.warehousedemo.mappers.WarehouseMapper;
import com.ahuggins.warehousedemo.models.Item;
import com.ahuggins.warehousedemo.models.StoredItem;
import com.ahuggins.warehousedemo.models.Warehouse;
import com.ahuggins.warehousedemo.services.WarehouseService;


public class WarehouseControllerTests {
    @Mock 
    private WarehouseService warehouseService;

    @InjectMocks
    private WarehouseController warehouseController;

    private AutoCloseable closeable;
    private WarehouseMapper warehouseMapper;

    @BeforeClass
    public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
        this.warehouseMapper = new WarehouseMapper();
    }

    @AfterClass
    public void teardown() throws Exception{
        closeable.close();
        this.warehouseMapper = null;
    }
    

    /**
     * Tests to handle getting list of all warehouses
     * @param warehouses - list of all warehouses that needs to be returned
     */
    @Test(dataProvider = "dp_WarehouseLists", dataProviderClass = WarehouseData.class)
    public void getAllWarehousesTest(Warehouse[] warehouses){
        //Setup Mock Information and Methods
        List<Warehouse> expectedWarehouses = Arrays.asList(warehouses);
        when(warehouseService.getAllWarehouses(1)).thenReturn(expectedWarehouses);

        //Test All Branches of Method
        assertEquals(expectedWarehouses, warehouseController.getAllWarehouses(1));
    }


    /**
     * Tests to handle getting warehouse by Id
     * @param warehouse - warehouse that needs to be returned
     */
    @Test(dataProvider = "dp_Warehouses", dataProviderClass = WarehouseData.class)
    public void getWarehouseByIdTest(Warehouse[] warehouse){
        //Setup Mock Information
        Optional<Warehouse> expectedWarehouse = Optional.of(warehouse[0]);
        Optional<Warehouse> nullWarehouse = Optional.empty();
        
        // Setup Methods
        when(warehouseService.getWarehouseById(1, 1) ).thenReturn(expectedWarehouse);
        when(warehouseService.getWarehouseById(2, 2) ).thenReturn(nullWarehouse);
        
        //Test All Branches of Method
        assertEquals(expectedWarehouse.get(), warehouseController.getWarehouseById(1, 1));
        assertThrows(ResponseStatusException.class, () -> {
            warehouseController.getWarehouseById(2, 2);
        });
    }

    /**
     * Tests to handle getting warehouse by Name
     * @param warehouse - warehouse that needs to be returned
     */
    @Test(dataProvider = "dp_Warehouses", dataProviderClass = WarehouseData.class)
    public void getWarehouseByNameTest(Warehouse[] warehouse){
        //Setup Mock Information
        Optional<Warehouse> expectedWarehouse = Optional.of(warehouse[0]);
        Optional<Warehouse> nullWarehouse = Optional.empty();
        
        // Setup Methods
        when(warehouseService.getWarehouseByName(1, "Warehouse Name 1") ).thenReturn(expectedWarehouse);
        when(warehouseService.getWarehouseByName(2, "Warehouse Name 2") ).thenReturn(nullWarehouse);
        
        //Test All Branches of Method
        assertEquals(expectedWarehouse.get(), warehouseController.getWarehouseByName(1, "Warehouse Name 1") );
        assertThrows(ResponseStatusException.class, () -> {
            warehouseController.getWarehouseByName( 2, "Warehouse Name 2");
        });
    }

    /**
     * Tests to handle getting warehouses by Location Name
     * @param warehouses - List of warehouse that need to be returned
     */
    @Test(dataProvider = "dp_WarehouseLists", dataProviderClass = WarehouseData.class)
    public void getWarehouseByLocationTest(Warehouse[] warehouses){
        //Setup Mock Information
        List<Warehouse> expectedWarehouses = Arrays.asList(warehouses);
        List<Warehouse> nullWarehouseList = new ArrayList<>();
        
        // Setup Methods
        when(warehouseService.getWarehouseByLocation(1, "Warehouse Name 1") ).thenReturn(expectedWarehouses);
        when(warehouseService.getWarehouseByLocation(2, "Warehouse Name 2") ).thenReturn(nullWarehouseList);
        
        //Test All Branches of Method
        assertEquals(expectedWarehouses, warehouseController.getWarehouseByLocation(1, "Warehouse Name 1") );
        assertThrows(ResponseStatusException.class, () -> {
            warehouseController.getWarehouseByLocation( 2, "Warehouse Name 2");
        });
    }


    /**
     * Tests to handle creating a warehouse
     * @param warehouse - warehouse that needs to be returned
     */
    @Test(dataProvider = "dp_Warehouses", dataProviderClass = WarehouseData.class)
    public void createWarehouseTest(Warehouse[] warehouse){
        //Setup Mock Information
        WarehouseDto dummyWarehouseDto = warehouseMapper.toDto(warehouse[0]);
        Optional<WarehouseDto> expectedWarehouseDto = Optional.of(dummyWarehouseDto);
        Optional<WarehouseDto> nullWarehouse = Optional.empty();
        
        // Setup Methods
        when(warehouseService.createWarehouse(1, warehouse[0]) ).thenReturn(expectedWarehouseDto);
        when(warehouseService.createWarehouse(2, warehouse[0]) ).thenReturn(nullWarehouse);
        
        //Test All Branches of Method
        assertEquals(expectedWarehouseDto.get(), warehouseController.createWarehouse(1, warehouse[0]));
        assertThrows(ResponseStatusException.class, () -> {
            warehouseController.createWarehouse(2, warehouse[0]);
        });
    }

    
    /**
     * Tests to handle updating a warehouse
     * @param warehouse - warehouse that needs to be returned
     */
    @Test(dataProvider = "dp_Warehouses", dataProviderClass = WarehouseData.class)
    public void updateWarehouseTest(Warehouse[] warehouse){
        //Setup Mock Information
        WarehouseDto dummyWarehouseDto = warehouseMapper.toDto(warehouse[0]);
        Optional<WarehouseDto> expectedWarehouseDto = Optional.of(dummyWarehouseDto);
        Optional<WarehouseDto> nullWarehouse = Optional.empty();
        
        // Setup Methods
        when(warehouseService.updateWarehouse(1, 1, warehouse[0]) ).thenReturn(expectedWarehouseDto);
        when(warehouseService.updateWarehouse(2, 2, warehouse[0]) ).thenThrow(DataIntegrityViolationException.class);
        when(warehouseService.updateWarehouse(3, 3, warehouse[0]) ).thenReturn(nullWarehouse);
        
        //Test All Branches of Method
        assertEquals(expectedWarehouseDto.get(), warehouseController.updateWarehouse(1, 1, warehouse[0]));
        
        try { warehouseController.updateWarehouse(2, 2, warehouse[0]); } 
        catch (ResponseStatusException e) {
            assertEquals(HttpStatus.CONFLICT, e.getStatusCode());
        }

        try { warehouseController.updateWarehouse(3, 3, warehouse[0]); } 
        catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
        
    }
    
    
    /**
     * Tests to handle deletions of warehouses
     */
    @Test
    public void deleteWarehouseTest(){
        //Test All Branches of Method
        warehouseController.deleteWarehouse(3, 3);
        verify(warehouseService).deleteWarehouse(3, 3);
    }
    
}
