package com.ahuggins.warehousedemo.componentTests.Repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.refEq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.componentTests.TestResources.WarehouseData;
import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.models.Warehouse;
import com.ahuggins.warehousedemo.repositories.AdministratorRepository;
import com.ahuggins.warehousedemo.repositories.WarehouseRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class WarehouseRepositoryTests extends AbstractTestNGSpringContextTests {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private AdministratorRepository adminRepo;

    
    private List<Warehouse> warehouses;
    private Administrator admin1, admin2;
    private WarehouseData dataProvider;
    private int NUM_WAREHOUSES = 5;

    // @Override
    // @BeforeSuite
    // protected void springTestContextPrepareTestInstance() throws Exception {
    //     super.springTestContextPrepareTestInstance();
    // }

    @BeforeClass
    public void setup(){
        //setup dataprovider
        dataProvider = new WarehouseData();
        
        //create two administrators and assign 2 warehouses to one and 4 to the others
        Administrator admin1 = new Administrator(1, "Company 1");        
        Administrator admin2 = new Administrator(2, "Company 2");

        admin1.setPassword("Password 1"); 
        admin2.setPassword("Password 2");
        adminRepo.save(admin1); adminRepo.save(admin2);
        
        warehouses = dataProvider.createWarehouses(NUM_WAREHOUSES);
        for (int i=0; i<warehouses.size(); i++) {
            if (i<2) {
                warehouses.get(i).setAdministrator(admin1);
            } else {
                warehouses.get(i).setAdministrator(admin2);
            }
        }

        // change admin2 to have two warehouses with the same Location
        warehouses.get(NUM_WAREHOUSES-1).setLocation(warehouses.get(NUM_WAREHOUSES-2).getLocation());
        /**
         * +----+--------+-------------+------------+
         * | ID | Owner  | Name        | Location   |
         * +----+--------+-------------+------------+
         * | 0  | admin1 | Warehouse 1 | Location 1 |
         * | 1  | admin1 | Warehouse 2 | Location 2 |
         * +----+--------+-------------+------------+
         * | 2  | admin2 | Warehouse 3 | Location 3 |
         * | 3  | admin2 | Warehouse 4 | Location 4 |
         * | 4  | admin2 | Warehouse 5 | Location 4 | 
         * +----+--------+-------------+------------+
         */ 


        // Save all warehouses to the repository for testing
        for (int i=0; i<warehouses.size(); i++) {
            warehouseRepository.save(warehouses.get(i));
        }

    }

    @AfterClass
    public void teardown() {
        warehouseRepository = null;
        warehouses = null;
        admin1 = null;
        admin2 = null;
        dataProvider = null;
    }

    @Test
    public void generalTest() {
        // This is just to make sure the setup is correct
        List<Warehouse> result = warehouseRepository.findAll();
        assertEquals(warehouses, result);
    }

    @Test
    public void findByIdAndAdministratorTest(){
        int id = warehouses.get(3).getId();
        Optional<Warehouse>  existingWarehouse = warehouseRepository.findByIdAndAdministrator(id, admin2);
        Optional<Warehouse> nullWarhouse = warehouseRepository.findByIdAndAdministrator(id, admin1);

        assertEquals( warehouses.get(3), existingWarehouse.get() );
        assertTrue(nullWarhouse.isEmpty());
    }

    @Test
    public void findByIdOrNameAndAdministratorTest(){
        //TODO Implement Methods Here
        throw new UnsupportedOperationException("Unimplemented Test");
    }

    @Test
    public void findByAdministratorTest(){
        //TODO Implement Methods Here
        throw new UnsupportedOperationException("Unimplemented Test");
    }

    @Test
    public void findByNameTest(){
        //TODO Implement Methods Here
        throw new UnsupportedOperationException("Unimplemented Test");
    }
    @Test
    public void findByNameAndAdministratorTest(){
        //TODO Implement Methods Here
        throw new UnsupportedOperationException("Unimplemented Test");
    }

    @Test
    public void findByLocationTest(){
        //TODO Implement Methods Here
        throw new UnsupportedOperationException("Unimplemented Test");
    }

    @Test
    public void findByLocationAndAdministratorTest(){
        //TODO Implement Methods Here
        throw new UnsupportedOperationException("Unimplemented Test");
    }
}
