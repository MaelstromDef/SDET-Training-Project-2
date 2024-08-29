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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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

    @DataProvider(name="AdministratorList")
    public Object[][] provideAdministratorList(){
        return new Object[][] {
            { new Administrator(1, "Company 1"),
            new Administrator(2, "Company 2") }
        };
    }

    @DataProvider(name="AdministratorDtoList")
    public Object[][] provideAdministratorDtoList(){
        return new Object[][] {
            { new AdministratorDto(3, "Company 3"),
            new AdministratorDto(4, "Company 4") }
        };
    }

    
    @Test(dataProvider = "AdministratorDtoList")
    public void getAdministratorsTest(AdministratorDto[] adminDtos){
        //Setup Mock Information and Methods
        List<AdministratorDto> expectedAdminDtos = Arrays.asList(adminDtos);
        when(adminService.getAllAdministrators()).thenReturn(expectedAdminDtos);
        
        //Test All Branches of Method
        List<AdministratorDto> actualAdminDtos = adminController.getAdministrators();
        assertEquals(expectedAdminDtos, actualAdminDtos);
    }

    @Test(dataProvider = "AdministratorList")
    public void loginTest(Administrator[] admins) {
        //Setup Mock Information and Methods
        Administrator admin1 = admins[0];
        Administrator admin2 = admins[1];
        String expectedJwt = "Valid JWT String";
        try {
            when(adminService.login(admin1)).thenReturn(expectedJwt);
            when(adminService.login(admin2)).thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        } catch (Exception e) {e.printStackTrace();}
        
        //Test All Branches of Method
        assertEquals(expectedJwt, adminController.login(admin1));
        assertThrows(ResponseStatusException.class, () ->{ adminController.login(admin2); });
    }

    @Test(dataProvider = "AdministratorList")
    public void signupTest(Administrator[] admins) {
        //Setup Mock Information and Methods
        Administrator admin1 = admins[0];
        Administrator admin2 = admins[1];
        Optional<AdministratorDto> expectedAdminDto1 = Optional.of(new AdministratorDto(1, "Company 1"));
        Optional<AdministratorDto> expectedAdminDto2 = Optional.empty();
        when(adminService.createAdministrator(admin1) ).thenReturn(expectedAdminDto1);
        when(adminService.createAdministrator(admin2) ).thenReturn(expectedAdminDto2);
    
        
        //Test All Branches of Method
        assertEquals(expectedAdminDto1.get(), adminController.signup(admin1));
        assertThrows(ResponseStatusException.class, () ->{ adminController.signup(admin2); }); 
    }

    @Test(dataProvider = "AdministratorList")
    public void updateAccountTest(Administrator[] admins) {
         //Setup Mock Information and Methods
         int id1 = 1, id2 = 2;
         Administrator admin1 = admins[0];
         Administrator admin2 = admins[1];
         Optional<AdministratorDto> expectedAdminDto1 = Optional.of(new AdministratorDto(1, "Company 1"));
         Optional<AdministratorDto> expectedAdminDto2 = Optional.empty();
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

    @Test
    public void deleteAccountTest() {
        //Test All Branches of Method
        adminController.deleteAccount(3);
        verify(adminService).deleteAdministrator(3);
    }
}
