package com.ahuggins.warehousedemo.componentTests.Controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties.Admin;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.componentTests.TestResources.AdminData;
import com.ahuggins.warehousedemo.controllers.AdminController;
import com.ahuggins.warehousedemo.dtos.AdministratorDto;
import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.services.AdminService;



public class AdminControllerTests {
    @Mock 
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    private AutoCloseable closeable;

    @BeforeClass
    public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterClass
    public void teardown() throws Exception{
        closeable.close();
    }

    //#region Data

    /**
     * Tests that handles Controller to return all admins
     */
    @Test(dataProvider = "dp_AdministratorDtoLists", dataProviderClass = AdminData.class)
    public void getAdministratorsTest(AdministratorDto[] adminDtos){
        //Setup Mock Information and Methods
        List<AdministratorDto> expectedAdminDtos = Arrays.asList(adminDtos);
        when(adminService.getAllAdministrators()).thenReturn(expectedAdminDtos);
        
        //Test All Branches of Method
        List<AdministratorDto> actualAdminDtos = adminController.getAdministrators();
        assertEquals(expectedAdminDtos, actualAdminDtos);
    }


    /**
     * Test that handles login of a user
     * @param admins = List of Administrators that will be used to login/ fail logins
     */
    @Test(dataProvider = "dp_AdministratorLists", dataProviderClass = AdminData.class)
    public void loginTest(Administrator[] admins) {
        //Setup Mock Information and Methods
        List<Administrator> inputAdmins = Arrays.asList(admins);
        Administrator admin1 = inputAdmins.get(0);
        Administrator admin2 = inputAdmins.get(1);
        String expectedJwt = "Valid JWT String";
        // When logging in, Response should be either proper JWT or return UNAUTHORIZED
        try {
            when(adminService.login(admin1)).thenReturn(expectedJwt);
            when(adminService.login(admin2)).thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        } catch (Exception e) {e.printStackTrace();}
        
        //Test All Branches of Method
        assertEquals(expectedJwt, adminController.login(admin1));
        assertThrows(ResponseStatusException.class, () ->{ adminController.login(admin2); });
    }

    /**
     * Test that handles signin of a user
     * @param admins = List of Administrators that will be used to signup/ fail signups
     */
    @Test(dataProvider = "dp_AdministratorLists", dataProviderClass = AdminData.class)
    public void signupTest(Administrator[] admins) {
        //Setup Mock Information
        List<Administrator> inputAdmins = Arrays.asList(admins);
        Administrator admin1 = inputAdmins.get(0);
        Administrator admin2 = inputAdmins.get(1);
        Optional<AdministratorDto> expectedAdminDto1 = Optional.of(new AdministratorDto(1, "Company 1"));
        Optional<AdministratorDto> expectedAdminDto2 = Optional.empty();
        
        // Setup Methods
        when(adminService.createAdministrator(admin1) ).thenReturn(expectedAdminDto1);
        when(adminService.createAdministrator(admin2) ).thenReturn(expectedAdminDto2);
    
        
        //Test All Branches of Method
        assertEquals(expectedAdminDto1.get(), adminController.signup(admin1));
        assertThrows(ResponseStatusException.class, () ->{ adminController.signup(admin2); }); 
    }


    /**
     * Test that handles Updating of an account
     * @param admins = = List of Administrators that will be used to update accounts/fail updates
     */
    @Test(dataProvider = "dp_AdministratorLists", dataProviderClass = AdminData.class)
    public void updateAccountTest(Administrator[] admins) {
         //Setup Mock Information and Methods
         int id1 = 1, id2 = 2;
         List<Administrator> inputAdmins = Arrays.asList(admins);
         Administrator admin1 = inputAdmins.get(0);
         Administrator admin2 = inputAdmins.get(1);
         Optional<AdministratorDto> expectedAdminDto1 = Optional.of(new AdministratorDto(1, "Company 1"));
         Optional<AdministratorDto> expectedAdminDto2 = Optional.empty();
         
         // Setup Methods
         try {
            when(adminService.updateAdministrator(id1, admin1) ).thenReturn(expectedAdminDto1);
            when(adminService.updateAdministrator(id2, admin2) ).thenReturn(expectedAdminDto2);
         } catch (Exception e) {e.printStackTrace();}
     
         
         //Test All Branches of Method
         try {
            assertEquals(expectedAdminDto1.get(), adminController.updateAccount(id1, admin1));
            assertThrows(ResponseStatusException.class, () ->{ adminController.updateAccount(id2, admin2); }); 
        } catch (Exception e) {e.printStackTrace();}
    }


    /**
     * Tests that will handle Deletion/ failed Deletion of Account
     */
    @Test
    public void deleteAccountTest() {
        //Test All Branches of Method
        adminController.deleteAccount(3);
        verify(adminService).deleteAdministrator(3);
    }
}
