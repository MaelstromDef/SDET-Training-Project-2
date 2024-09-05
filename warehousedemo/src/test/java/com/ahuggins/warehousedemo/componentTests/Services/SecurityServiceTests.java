package com.ahuggins.warehousedemo.componentTests.Services;

import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Security;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.componentTests.TestResources.AdminData;
import com.ahuggins.warehousedemo.dtos.AdministratorDto;
import com.ahuggins.warehousedemo.mappers.AdminMapper;
import com.ahuggins.warehousedemo.models.Administrator;
import com.ahuggins.warehousedemo.services.SecurityService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import java.util.Base64;

public class SecurityServiceTests {
    @Mock
    private AdminMapper mapper;

    @InjectMocks
    private SecurityService service;
    private AutoCloseable closeable;

    @BeforeClass
    public void openMocks(){
        closeable = MockitoAnnotations.openMocks(this);
        service = new SecurityService(mapper);
    }

    @AfterClass
    public void teardown() throws Exception{
        closeable.close();
    }

//#region Data

    /**
     * Provides a list of strings that are valid for use in the hashString function.
     */
    @DataProvider(name = "dp_ValidStrings")
    public Object[][] provideValidStrings(){
        return new Object[][]{
            {"password"},
            {"hullaballoo"},
            {"123MyPassword456*"}
        };
    }

    /**
     * Provides a list of strings that are invalid for use in the hashString function.
     */
    @DataProvider(name = "dp_InvalidStrings")
    public Object[][] provideInvalidStrings(){
        return new Object[][]{
            {""},
            {null}
        };
    }

//#endregion

//#region Tests

    /**
     * Ensures that valid administrators can receive a jwt.
     * @param dto Administrator representation.
     */
    @Test(dataProvider = "dp_AdministratorDtos", dataProviderClass = AdminData.class)
    public void testValidGetJwt(AdministratorDto dto){
        try{
            Assert.assertNotNull(SecurityService.getJwt(dto));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Ensures that invalid administrators (with null fields) can not
     * receive a jwt.
     */
    @Test
    public void testInvalidGetJwt(){
        AdministratorDto dto = new AdministratorDto();
        try{
            Assert.assertNull(SecurityService.getJwt(dto));
        }catch(Exception e){ }  // Null exception passes.
    }

    /**
     * Tests that valid strings are hashed by hashString.
     * @param str
     */
    @Test(dataProvider = "dp_ValidStrings")
    public void testValidHashString(String str){
        // Valid string to hash
        try{
            Assert.assertNotNull(SecurityService.hashString(str));
        }catch(Exception e){
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Tests that invalid strings are appropriately handled by hashString.
     */
    @Test(dataProvider = "dp_InvalidStrings")
    public void testInvalidHashString(String str){
        // Invalid string to hash
        try{
            Assert.assertNull(SecurityService.hashString(str));
        }catch(Exception e){}   // Failure to run an invalid string passes.
    }

    /**
     * Tests the validateAdmin methods when a valid and invalid admin/dto are used.
     * 
     * @apiNote Depends on the getJwt method, ensure testValidGetJwt is robust.
     * @throws Exception When the getJwt method can not make a valid JWT.
     */
    @Test(dependsOnMethods = {"testValidGetJwt"},
        dataProvider = "dp_AdministratorDtos", dataProviderClass = AdminData.class)
    public void testValidateAdmin(AdministratorDto dto) throws Exception{
        // Get necessary information
        String jwt = null;
        jwt = SecurityService.getJwt(dto);

        // Set up mocks
        Administrator admin = new Administrator(dto.getId(), dto.getCompanyName());
        when(mapper.toDto(admin)).thenReturn(dto);

        // Test
        Assert.assertTrue(SecurityService.validateAdmin(jwt, dto));
        Assert.assertTrue(SecurityService.validateAdmin(jwt, admin));
        
        dto.setId(dto.getId() + 1);
        admin.setId(admin.getId() + 1);
        
        Assert.assertFalse(SecurityService.validateAdmin(jwt, dto));
        Assert.assertFalse(SecurityService.validateAdmin(jwt, admin));
    }

    /**
     * Tests the validate method when a valid JWT, invalid JWT, and random string are used.
     * 
     * @apiNote Depends on the getJwt method, ensure testValidGetJwt is robust.
     * @throws Exception When the getJwt method can not make a valid JWT.
     */
    @Test(dependsOnMethods = {"testValidGetJwt"})
    public void testValidate() throws Exception{
        // Get necessary information
        String jwt = SecurityService.getJwt(new AdministratorDto(1, "Company 1"));

        // Create a bad JWT
        String badJwt;
        SecretKey badKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode("bad key.paddingpaddingpadding".getBytes("UTF-8")));
        badJwt = Jwts.builder()
            .claim(SecurityService.ID_CLAIM, 1)
            .claim(SecurityService.C_NAME_CLAIM, "Company 1")
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plusSeconds(3600)))
            .signWith(badKey)
            .compact();

        // Tests

        Assert.assertTrue(SecurityService.validate(jwt));
        Assert.assertFalse(SecurityService.validate("jwt"));
        Assert.assertFalse(SecurityService.validate(badJwt));
    }

    /**
     * Tests the getClaim method for valid claims and an invlaid claim.
     * 
     * @apiNote Depends on the getJwt method, ensure testValidGetJwt is robust.
     * @throws Exception When the getJwt method can not make a valid JWT.
     */
    @Test(dependsOnMethods = {"testValidGetJwt"})
    public void testGetClaim() throws Exception{
        // Get necessary information
        String jwt = SecurityService.getJwt(new AdministratorDto(1, "Company 1"));

        // Tests
        Assert.assertEquals(SecurityService.getClaim(jwt, SecurityService.ID_CLAIM), "1");
        Assert.assertEquals(SecurityService.getClaim(jwt, SecurityService.C_NAME_CLAIM), "Company 1");

        Assert.assertEquals(SecurityService.getClaim(jwt, SecurityService.ID_CLAIM, Integer.class), Integer.valueOf(1));

        Assert.assertNull(SecurityService.getClaim(jwt, "InvalidClaim."));
    }

//#endregion
}
