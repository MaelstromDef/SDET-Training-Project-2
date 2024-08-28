/**
 * By: Aaron Huggins
 * 
 * Description:
 *      Performs unit tests on the AdminService class.
 */

package com.ahuggins.warehousedemo.componentTests.Services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
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
    private AdminMapper mapper;

    @InjectMocks
    private AdminService service;
    private AutoCloseable closeable;

    @BeforeClass
    public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
        mapper = new AdminMapper();
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
        System.out.println(admins.toString());

        List<Administrator> listAdmins = Arrays.asList(admins);
        System.out.println(listAdmins.toString());
        List<AdministratorDto> expected = listAdmins.stream().map(mapper::toDto).toList();

        when(repo.findAll()).thenReturn(listAdmins);
        List<AdministratorDto> actual = service.getAllAdministrators();

        for(int i = 0; i < actual.size(); i++){
            assertNotNull(actual.get(i));
            assertNotNull(expected.get(i));
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getCompanyName(), actual.get(i).getCompanyName());
        }
    }

    @Test
    public void testGetAdministratorById(){

    }

    @Test(dataProvider = "dp_Administrators")
    public void testLogin(Administrator admin){
        
    }

    @Test
    public void testCreateAdministrator(){

    }

    @Test
    public void testUpdateAdministrator(){

    }

    @Test
    public void testDeleteAdministrator(){

    }

    //#endregion
}
