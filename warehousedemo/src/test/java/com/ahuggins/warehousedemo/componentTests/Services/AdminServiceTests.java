/**
 * By: Aaron Huggins
 * 
 * Description:
 *      Performs unit tests on the AdminService class.
 */

package com.ahuggins.warehousedemo.componentTests.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.dtos.AdministratorDto;
import com.ahuggins.warehousedemo.mappers.AdminMapper;
import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.repositories.AdministratorRepository;
import com.ahuggins.warehousedemo.services.AdminService;
import com.ahuggins.warehousedemo.services.SecurityService;

public class AdminServiceTests {
    @Mock 
    private AdministratorRepository repo;
    @Mock
    private AdminMapper mapper;

    @InjectMocks
    private AdminService service;

    private AutoCloseable closeable;

    @BeforeClass
    public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
        mapper = new AdminMapper();
        service = new AdminService(repo, mapper);
    }

    @AfterClass
    public void teardown() throws Exception{
        closeable.close();
    }

    //#region Data

    @DataProvider(name="dp_AdministratorLists")
    public Object[][] provideAdministratorLists(){
        return new Object[][]{
            {new Administrator(1, "Company 1"), new Administrator(2, "Company 2")},
            {new Administrator(3, "Company 3"), new Administrator(4, "Company 4")}
        };
    }

    @DataProvider(name="dp_Administrators")
    public Object[][] provideAdministrators(){
        return new Object[][]{
            {new Administrator(1, "Company 1")},
            {new Administrator(2, "Company 2")}
        };
    }

    //#endregion

    //#region Tests

    @Test(dataProvider = "dp_AdministratorLists")
    public void testGetAllAdministrators(Administrator[] admins){
        List<Administrator> expected = Arrays.asList(admins);

        when(repo.findAll()).thenReturn(expected);
        List<AdministratorDto> actual = service.getAllAdministrators();

        for(int i = 0; i < actual.size(); i++){
            assertNotNull(actual.get(i));
            assertNotNull(expected.get(i));
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getCompanyName(), actual.get(i).getCompanyName());
        }
    }

    @Test(dataProvider = "dp_AdministratorLists")
    public void testGetAdministratorById(Administrator[] admins){
        Optional<Administrator> optional = Optional.of(admins[0]);

        when(repo.findById(admins[0].getId())).thenReturn(optional);
        Optional<AdministratorDto> actual = service.getAdministratorById(admins[0].getId());

        assertEquals(optional, actual);
    }

    // WARNING: NEEDS ENV
    @Test(dataProvider = "dp_Administrators")
    public void testLogin(Administrator admin) throws Exception{
        

        String jwt = null;
        admin.setPassword("password");
        
        when(repo.findByCompanyNameAndPassword(admin.getCompanyName(), SecurityService.hashString(admin.getPassword())))
        .thenReturn(Arrays.asList(admin));

        jwt = service.login(admin);
        assertNotNull(jwt);
    }

    // WARNING: NEEDS ENV
    @Test
    public void testCreateAdministrator(){

    }

    // WARNING: NEEDS ENV
    @Test(dataProvider = "dp_Administrators")
    public void testUpdateAdministrator(Administrator admin){
        int id = admin.getId();

        // Create an updated admin
        Administrator updatedAdmin = new Administrator();
        updatedAdmin.setCompanyName("Updated Company Name");
        updatedAdmin.setId(id);
        updatedAdmin.setPassword(admin.getPassword());
        updatedAdmin.setWarehouses(admin.getWarehouses());

        // Setup mocks
        when(repo.findById(id)).thenReturn(Optional.of(admin));
        when(repo.save(updatedAdmin)).thenReturn(updatedAdmin);
        
        // Run tests
        try {
            // Valid update
            Optional<AdministratorDto> dto = service.updateAdministrator(admin.getId(), updatedAdmin);
            assertEquals(updatedAdmin.getCompanyName(), dto.get().getCompanyName());

            // Cross-admin attacks
            admin.setId(id + 1);
            service.updateAdministrator(id, updatedAdmin);
            assertEquals(updatedAdmin.getCompanyName(), dto.get().getCompanyName());
            assertEquals(id, dto.get().getId());
        } catch (IllegalAccessException e) {
            fail("Update failed.\n" + e.getMessage());
        }
    }

    @Test
    public void testDeleteAdministrator(){

    }

    //#endregion
}
