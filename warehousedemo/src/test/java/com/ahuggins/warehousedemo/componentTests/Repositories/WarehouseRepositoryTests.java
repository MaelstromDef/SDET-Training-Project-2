package com.ahuggins.warehousedemo.componentTests.Repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.componentTests.TestResources.AdminData;
import com.ahuggins.warehousedemo.componentTests.TestResources.WarehouseData;
import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.models.Warehouse;
import com.ahuggins.warehousedemo.repositories.AdministratorRepository;
import com.ahuggins.warehousedemo.repositories.WarehouseRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class WarehouseRepositoryTests extends AbstractTestNGSpringContextTests  {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private AdministratorRepository adminRepo;

    
    private List<Warehouse> warehouses;
    private List<Administrator> admins;
    private AdminData adminProvider;
    private WarehouseData warehouseProvider;
    private int NUM_WAREHOUSES = 5;
    private int NUM_ADMINS = 3;


    @BeforeClass
    public void setup(){
        //setup dataprovider
        warehouseProvider = new WarehouseData();
        adminProvider = new AdminData();
        
        //create two administrators and assign 2 warehouses to one and 4 to the others
        admins = adminProvider.createAdmins(NUM_ADMINS); 
        warehouses = warehouseProvider.createWarehouses(NUM_WAREHOUSES);
        
        for (int i=0; i<warehouses.size(); i++) {
            if (i<2) {
                warehouses.get(i).setAdministrator(admins.get(0));
            } else {
                warehouses.get(i).setAdministrator(admins.get(1));
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
         * | -  | admin3 | -           | -          | 
         * +----+--------+-------------+------------+
         */ 
        

        //save all admins to database
        for (Administrator admin : admins) {
            adminRepo.save(admin);
        }
        
        // Save all warehouses to the repository for testing
        for (int i=0; i<warehouses.size(); i++) {
            warehouseRepository.save(warehouses.get(i));
        }
    }

    @AfterClass
    public void teardown() {
        warehouseRepository = null;
        adminRepo = null;
        warehouses = null;
        admins = null;
        adminProvider = null;
        warehouseProvider = null;
    }


    /**
     * Test to assure that data properly got added to database for testing
     */
    @Test
    public void generalTest() {
        // This is just to make sure the setup is correct
        List<Warehouse> result = warehouseRepository.findAll();
        assertEquals(warehouses.size(), result.size());
    }

    /**
     * Test to handle finding Warehouses by Id or Administrator object
     */
    @Test
    public void findByIdAndAdministratorTest(){
        int id = warehouses.get(3).getId();

        //Test comprised of warehouse that exists and admin who is/isn't related to that warehouse
        Optional<Warehouse>  existingWarehouse = warehouseRepository.findByIdAndAdministrator(id, admins.get(1));
        Optional<Warehouse> nullWarehouse = warehouseRepository.findByIdAndAdministrator(id, admins.get(0));

        Assert.assertEquals(existingWarehouse.get(),  warehouses.get(3) );
        Assert.assertTrue(nullWarehouse.isEmpty());
    }

    /*
     * Test to handl finding warehouses by (Id) OR by (Name AND administrator object)
     */
    @Test
    public void findByIdOrNameAndAdministratorTest(){
        /** 
         * Test Cases:
         *      | id        | Name AND Admin | result    |
         *      | correct   | correct        | not empty |
         *      | correct   | incorrect      | not empty |
         *      | incorrect | correct        | not empty |
         *      | incorrect | incorrect      | empty     |
         */
        int index = 1;
        Administrator admin = admins.get(0);
        Administrator wrongAdmin = admins.get(2);

        int id = warehouses.get(index).getId();
        int wrongId = 999;

        String name = warehouses.get(index).getName();
        String wrongName = "Wrong Name";

        Optional<Warehouse> existingWarehouse1 = warehouseRepository.findByIdOrNameAndAdministrator(id, name, admin);
        Optional<Warehouse> existingWarehouse2 = warehouseRepository.findByIdOrNameAndAdministrator(id, wrongName, admin);
        Optional<Warehouse> existingWarehouse3 = warehouseRepository.findByIdOrNameAndAdministrator(wrongId, name, admin);
        Optional<Warehouse> nullWarehouse = warehouseRepository.findByIdOrNameAndAdministrator(wrongId, name, wrongAdmin);
        
        Assert.assertEquals( warehouses.get(index), existingWarehouse1.get() );
        Assert.assertEquals( warehouses.get(index), existingWarehouse2.get() );
        Assert.assertEquals( warehouses.get(index), existingWarehouse3.get() );
        Assert.assertTrue(nullWarehouse.isEmpty());
    }


    /**
     * Tests to handle finding warehouses by Administrator Object
     */
    @Test
    public void findByAdministratorTest(){
        Administrator admin = admins.get(0);
        Administrator emptyAdmin = admins.get(2);

        //2 cases, admin with no warehouses and admin with
        List<Warehouse> existingWarehouses = warehouseRepository.findByAdministrator(admin);
        List<Warehouse> nullWarehouses = warehouseRepository.findByAdministrator(emptyAdmin);

        Assert.assertTrue(existingWarehouses.size() == 2);
        Assert.assertTrue(nullWarehouses.size() == 0);

    }

    /**
     * Test to find warehouses by Name
     */
    @Test
    public void findByNameTest(){
        int index = 3;
        String name =  warehouses.get(index).getName();
        String wrongName = "Wrong Name";

        //Warehouse names are unique, so only two cases are needed
        Optional<Warehouse> existingWarehouse = warehouseRepository.findByName(name);
        Optional<Warehouse> nullWarehouse = warehouseRepository.findByName(wrongName);

        Assert.assertEquals( warehouses.get(index), existingWarehouse.get() );
        Assert.assertTrue(nullWarehouse.isEmpty());
    }


    /**
     * Tests to handle finding warehouses by Name AND Admininstrator object
     */
    @Test
    public void findByNameAndAdministratorTest(){
        int id =0;
        String name = warehouses.get(id).getName();
        String wrongName = "Wrong Name";
        Administrator admin = admins.get(id);
        Administrator wrongAdmin = admins.get(NUM_ADMINS-1);

        /** 
         * Test Cases:
         *      | Name      | Admin          | result     |
         *      | correct   | exists         | not empty  |
         *      | correct   | incorrect      | empty      |
         *      | incorrect | correct        | empty      |
         *      | incorrect | incorrect      | Not needed |
         */

        Optional<Warehouse> existingWarehouse = warehouseRepository.findByNameAndAdministrator(name, admin);
        Optional<Warehouse> nullWarehouse1 = warehouseRepository.findByNameAndAdministrator(wrongName, admin);
        Optional<Warehouse> nullWarehouse2 = warehouseRepository.findByNameAndAdministrator(name, wrongAdmin);

        Assert.assertEquals( warehouses.get(id), existingWarehouse.get() );
        Assert.assertTrue(nullWarehouse1.isEmpty());
        Assert.assertTrue(nullWarehouse2.isEmpty());
    }

    /**
     * Tests that handels finding warehouses by Location
     */
    @Test
    public void findByLocationTest(){
        //location that is the same for two warehouses
        String location1 = warehouses.get(NUM_WAREHOUSES-1).getLocation();
        //location that only hase one warehouse match
        String location2 = warehouses.get(0).getLocation();
        String wrongLocation = "Wrong Location";

        /**
         * Three test cases:
         *      1. Location that multiple Warehouses have
         *      2. Location that warehouse uniqu
         *      3. Location that doesn't exist
         */

        List<Warehouse> existingWarehouseList = warehouseRepository.findByLocation(location1);
        List<Warehouse> existingWarehouse = warehouseRepository.findByLocation(location2);
        List<Warehouse> nullWarehouse = warehouseRepository.findByLocation(wrongLocation);
        
        Assert.assertTrue( existingWarehouseList.size() == 2 );
        Assert.assertTrue( existingWarehouse.size() == 1 );
        Assert.assertEquals(existingWarehouse.get(0), warehouses.get(0));
        Assert.assertTrue(nullWarehouse.isEmpty());
    }

    /**
     * Test to hand finding warehouses by Location and Administrator
     */
    @Test
    public void findByLocationAndAdministratorTest(){
        //This actually needs more testing on it to test for cases where two different admins have same location
        int idLocation = NUM_WAREHOUSES-1;
        String locationList = warehouses.get(idLocation).getLocation();
        String locationSingle = warehouses.get(0).getLocation();
        String wrongLocation = "Wrong Location";
        Administrator adminList = admins.get(1);
        Administrator adminSingle = admins.get(0);
        Administrator wrongAdmin = admins.get(2);

        /*
         * Test Cases should cover:
         *      1. Location AND Admin object that match multipl warehouses
         *      2. Location AND ADmin object that match one warehouse
         *      3. Location with multiple warehouses but admin that doesn't match any of them
         *      4. Location that doesn't exist
         */

        List<Warehouse> existingWarehouseList = warehouseRepository.findByLocationAndAdministrator(locationList, adminList);
        List<Warehouse> existingWarehouse = warehouseRepository.findByLocationAndAdministrator(locationSingle, adminSingle);
        List<Warehouse> nullWarehouse1 = warehouseRepository.findByLocationAndAdministrator(locationList, wrongAdmin);
        List<Warehouse> nullWarehouse2 = warehouseRepository.findByLocationAndAdministrator(wrongLocation, adminList);

        Assert.assertTrue(existingWarehouseList.size() == 2);
        Assert.assertTrue( existingWarehouse.size() == 1 );
        Assert.assertTrue(nullWarehouse1.isEmpty());
        Assert.assertTrue(nullWarehouse2.isEmpty());
    }
}
