/**
 * By: Aaron Huggins
 * 
 * Description:
 *      Performs unit tests on the AdminService class.
 */

package com.ahuggins.warehousedemo.componentTests.Services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.mappers.AdminMapper;
import com.ahuggins.warehousedemo.repositories.AdministratorRepository;
import com.ahuggins.warehousedemo.services.AdminService;

public class AdminServiceTests {
    private AdminService service;

    @Mock 
    private AdministratorRepository repo;
    @Mock 
    private AdminMapper mapper;

    @BeforeClass
    public void createService(){
        service = new AdminService(repo, mapper);
    }

    @Test
    public void testMockito(){
        assertNotNull(service);
    }
}
