package com.ahuggins.warehousedemo.componentTests.Services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.dtos.AdministratorDto;
import com.ahuggins.warehousedemo.mappers.AdminMapper;
import com.ahuggins.warehousedemo.services.SecurityService;

public class SecurityServiceTests {
    @Mock
    private AdminMapper mapper;

    @InjectMocks
    private SecurityService service;
    private AutoCloseable closeable;

    @BeforeClass
    public void openMocks(){
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterClass
    public void teardown() throws Exception{
        closeable.close();
    }

    @Test
    public void testGetJwt(){
        System.out.println(System.getenv("secretKey"));
        AdministratorDto dto = new AdministratorDto(1, "Company 1");
        
        try{
            assertNotNull(SecurityService.getJwt(dto));
        } catch (Exception e) {
            fail(e);
        }
    }
}
