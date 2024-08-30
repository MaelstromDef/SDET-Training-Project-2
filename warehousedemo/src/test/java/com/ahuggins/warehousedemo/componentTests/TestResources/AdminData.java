package com.ahuggins.warehousedemo.componentTests.TestResources;

import org.testng.annotations.DataProvider;

import com.ahuggins.warehousedemo.dtos.AdministratorDto;
import com.ahuggins.warehousedemo.models.Administrator;

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

}
