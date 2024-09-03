package com.ahuggins.warehousedemo.componentTests.TestResources;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.ahuggins.warehousedemo.dtos.AdministratorDto;
import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.models.Warehouse;

public class AdminData {
    
    @DataProvider(name="dp_Administrators")
    public Object[][] provideAdministrators(){
        return new Object[][]{
            { new Administrator(1, "Company 1") },
            { new Administrator(2, "Company 2") }
        };
    }

    @DataProvider(name="dp_AdministratorDtos")
    public Object[][] provideAdministratorDtos(){
        return new Object[][] {
            { new AdministratorDto(1, "Company 1") },
            { new AdministratorDto(2, "Company 2") }
        };
    }

    @DataProvider(name="dp_AdministratorLists")
    public Object[][] provideAdministratorLists(){
        return new Object[][]{
            {new Administrator(1, "Company 1"), new Administrator(2, "Company 2")},
            {new Administrator(3, "Company 3"), new Administrator(4, "Company 4")}
        };
    }

    @DataProvider(name="dp_AdministratorDtoLists")
    public Object[][] provideAdministratorDtoLists(){
        return new Object[][]{
            {new AdministratorDto(1, "Company 1"), new AdministratorDto(2, "Company 2")},
            {new AdministratorDto(3, "Company 3"), new AdministratorDto(4, "Company 4")}
        };
    }

    public List<Administrator> createAdmins(int numAdmins) {
        
        List<Administrator> admins = new ArrayList<>();
        for (int i=1; i<=numAdmins; i++) {
            Administrator admin = new Administrator(i, "Company " + i);
            admin.setPassword("Password " + i);
            admins.add(admin);
        }

        return admins;           
    }

}
