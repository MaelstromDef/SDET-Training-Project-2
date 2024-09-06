package com.ahuggins.warehousedemo.componentTests.Repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.componentTests.TestResources.AdminData;
import com.ahuggins.warehousedemo.repositories.AdministratorRepository;
import com.ahuggins.warehousedemo.models.Administrator;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class AdminRepositoriesTests extends AbstractTestNGSpringContextTests {
    
    @Autowired
    private AdministratorRepository adminRepo;

    private AdminData adminProvider;
    private List<Administrator> admins;
    private int NUM_ADMINS = 3;

    @BeforeClass
    public void setup(){
        adminProvider = new AdminData();

        //We want 3 admins, two with the same company name, 2 with same password
        admins = adminProvider.createAdmins(NUM_ADMINS);
        for (int i=1; i<=NUM_ADMINS; i++) {
            if (i == 2) {
                //set the second admin to have same password as first
                admins.get(i-1).setPassword(admins.get(i-2).getPassword());
            } else {
                admins.get(i-1).setPassword("Password " + i);
            } 
            
            if (i==NUM_ADMINS) {
                //set the last admin to have the same company name as the first
                admins.get(i-1).setCompanyName(admins.get(i-2).getCompanyName());
            } else {
                admins.get(i-1).setCompanyName("Company " + i);
            }    
        }

        /**
         * Data should look like this
         * 
         * +----+-----------+------------+
         * | ID | Company   | Password   | 
         * +----+-----------+------------+
         * | 0  | Company 1 | Password 1 |
         * | 1  | Company 2 | Password 1 |
         * | 2  | Company 2 | Password 2 |
         * +----+-----------+------------+ 
         */

        for (Administrator admin : admins) {
            adminRepo.save(admin);
        }
    }

    @AfterClass
    public void teardown() {
        adminRepo = null;
        adminProvider = null;
        admins = null;
    }

    /**
     * Test to assure that admins properly got added to database for testing
     */
    @Test
    public void generalTest(){
        List<Administrator> returnedAdmins = adminRepo.findAll();

        Assert.assertEquals(returnedAdmins.size(), admins.size());
    }


    /**
     * Test that handles Finding admins by Name and Password 
     */
    @Test
    public void findByCompanyNameAndPasswordTest(){
        int index = 0;
        String name = admins.get(index).getCompanyName();
        String wrongName = "Wrong Company";
        String pass = admins.get(index).getPassword();
        String wrongPass = "Invalid Password";

        /*
         * There are four options here using A AND B decision tables
         *      +-------+-------+--------+
         *      | name  | pass  | result | 
         *      +-------+-------+--------+
         *      | right | right | exists |
         *      | right | wrong | empty  | 
         *      | wrong | right | empty  |
         *      | wrong | wrong | empty  |
         *      +-------+-------+--------+
         */
        List<Administrator> existingAdmin = adminRepo.findByCompanyNameAndPassword(name, pass);
        List<Administrator> nullAdmin1 = adminRepo.findByCompanyNameAndPassword(name, wrongPass);
        List<Administrator> nullAdmin2 = adminRepo.findByCompanyNameAndPassword(wrongName, pass);
        List<Administrator> nullAdmin3 = adminRepo.findByCompanyNameAndPassword(wrongName, wrongPass);

        Assert.assertEquals(existingAdmin.size(), 1);
        Assert.assertEquals(existingAdmin.get(0), admins.get(index));
        Assert.assertTrue(nullAdmin1.isEmpty());
        Assert.assertTrue(nullAdmin2.isEmpty());
        Assert.assertTrue(nullAdmin3.isEmpty());
    }

    /**
     * Test that handles Finding admins by Name
     */
    @Test
    public void findByCompanyNameTest(){
        // company with two admins
        String fullCompany = admins.get(NUM_ADMINS-1).getCompanyName();      
        // company with one admin
        String singleCompany = admins.get(0).getCompanyName();
        String wrongName = "Wrong Company";


        /*
         * There are three options here
         *      1. Name of Company that multiple companies have
         *      2. Name of Company that is unique
         *      3. Name of Company that doesn't exist
         */
        List<Administrator> adminList = adminRepo.findByCompanyName(fullCompany);
        List<Administrator> singleAdmin = adminRepo.findByCompanyName(singleCompany);
        List<Administrator> nullAdmin = adminRepo.findByCompanyName(wrongName);
        
        Assert.assertEquals(adminList.size(), 2);
        Assert.assertTrue(singleAdmin.size() == 1);
        Assert.assertEquals(singleAdmin.get(0), admins.get(0));
        Assert.assertTrue(nullAdmin.isEmpty());
    }

}
