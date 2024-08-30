/**
 * By: Aaron Huggins
 * 
 * Description:
 *      Performs unit tests on the AdminService class.
 */

package com.ahuggins.warehousedemo.componentTests.Services;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
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

    /**
     * Provides Administrator lists for testing.
     */
    @DataProvider(name="dp_AdministratorLists")
    public Object[][] provideAdministratorLists(){
        return new Object[][]{
            {new Administrator(1, "Company 1"), new Administrator(2, "Company 2")},
            {new Administrator(3, "Company 3"), new Administrator(4, "Company 4")}
        };
    }

    /**
     * Provides Administrator objects for testing.
     */
    @DataProvider(name="dp_Administrators")
    public Object[][] provideAdministrators(){
        return new Object[][]{
            {new Administrator(1, "Company 1")},
            {new Administrator(2, "Company 2")}
        };
    }

    //#endregion

    //#region Tests

    /**
     * Tests the getAllAdministrator method.
     */
    @Test(dataProvider = "dp_AdministratorLists")
    public void testGetAllAdministrators(Administrator[] admins){
        List<Administrator> expected = Arrays.asList(admins);

        when(repo.findAll()).thenReturn(expected);
        List<AdministratorDto> actual = service.getAllAdministrators();

        for(int i = 0; i < actual.size(); i++){
            Assert.assertNotNull(actual.get(i));
            Assert.assertNotNull(expected.get(i));
            Assert.assertEquals(expected.get(i).getId(), actual.get(i).getId());
            Assert.assertEquals(expected.get(i).getCompanyName(), actual.get(i).getCompanyName());
        }
    }

    /**
     * Tests the getAdministratorById method.
     */
    @Test(dataProvider = "dp_AdministratorLists")
    public void testGetAdministratorById(Administrator[] admins){
        Optional<Administrator> optional = Optional.of(admins[0]);

        when(repo.findById(admins[0].getId())).thenReturn(optional);
        Optional<AdministratorDto> actual = service.getAdministratorById(admins[0].getId());

        Assert.assertEquals(optional.get().getId(), actual.get().getId());
        Assert.assertEquals(optional.get().getCompanyName(), actual.get().getCompanyName());
    }

    /**
     * Tests the login method.
     * 
     * @throws Exception SecurityService.hashString failed.
     */
    @Test(dataProvider = "dp_Administrators")
    public void testLogin(Administrator admin) throws Exception{
        String jwt = null;
        admin.setPassword("password");
        
        when(repo.findByCompanyNameAndPassword(admin.getCompanyName(), SecurityService.hashString(admin.getPassword())))
        .thenReturn(Arrays.asList(admin));

        jwt = service.login(admin);
        Assert.assertNotNull(jwt);
    }

    /**
     * Tests the createAdministrator method for valid creations and 
     * creations that are invalid due to:
     *      - Empty password
     *      - Existing company.
     */
    @Test(dataProvider = "dp_Administrators")
    public void testCreateAdministrator(Administrator admin){
        // INVALID CREATIONS

        // Empty password
        admin.setPassword(null);
        List<Administrator> repoFindReturn = Arrays.asList(new Administrator[0]);
        AdministratorDto dto = new AdministratorDto(admin.getId(), admin.getCompanyName());

        when(repo.findByCompanyName(admin.getCompanyName())).thenReturn(repoFindReturn);
        when(repo.save(admin)).thenReturn(admin);

        Assert.assertTrue(service.createAdministrator(admin).isEmpty());    // Empty password

        // Company exists
        repoFindReturn = Arrays.asList(admin);
        admin.setPassword("password");

        when(repo.findByCompanyName(admin.getCompanyName())).thenReturn(repoFindReturn);

        Assert.assertTrue(service.createAdministrator(admin).isEmpty());    // Company exists

        // VALID CREATIONS
        
        admin.setPassword("password");
        repoFindReturn = Arrays.asList(new Administrator[0]);

        when(repo.findByCompanyName(admin.getCompanyName())).thenReturn(repoFindReturn);

        Optional<AdministratorDto> actual = service.createAdministrator(admin);

        Assert.assertEquals(dto.getId(), actual.get().getId());
        Assert.assertEquals(dto.getCompanyName(), actual.get().getCompanyName());
    }

    /**
     * Tests that AdminService::updateAdministrator runs properly,
     * using a valid and invalid update via ID, and an invalid password.
     * @param admin Administrator to update, which is updated by adding "Updated " to the beginning of the company name.
     */
    @Test(dataProvider = "dp_Administrators")
    public void testUpdateAdministrator(Administrator admin){
        int id = admin.getId();
        admin.setPassword("password");

        // Create an updated admin
        Administrator updatedAdmin = new Administrator();
        updatedAdmin.setCompanyName("Updated " + admin.getCompanyName());
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
            Assert.assertEquals(updatedAdmin.getCompanyName(), dto.get().getCompanyName());

            // Cross-admin attacks
            admin.setId(id + 1);
            service.updateAdministrator(id, updatedAdmin);
            Assert.assertEquals(updatedAdmin.getCompanyName(), dto.get().getCompanyName());
            Assert.assertEquals(id, dto.get().getId());

            // Empty password.
            updatedAdmin.setPassword("");
            Assert.assertThrows(IllegalAccessException.class, () -> service.updateAdministrator(id, updatedAdmin));

            updatedAdmin.setPassword(null);
            Assert.assertThrows(IllegalAccessException.class, () -> service.updateAdministrator(id, updatedAdmin));
        } catch (IllegalAccessException e) {
            Assert.fail("Update failed.\n" + e.getMessage());
        }
    }

    /**
     * Tests the deleteAdministrator method.
     */
    @Test(dataProvider = "dp_Administrators")
    public void testDeleteAdministrator(Administrator admin){
        service.deleteAdministrator(admin.getId());
        Assert.assertTrue(true, "AdminService::deleteAdministrator was called successfully.");
    }

    //#endregion
}
