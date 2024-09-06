package com.ahuggins.warehousedemo.componentTests.Aspects;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.aspects.SecurityFilter;
import com.ahuggins.warehousedemo.dtos.AdministratorDto;
import com.ahuggins.warehousedemo.services.SecurityService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;

public class SecurityFilterTests {
    HttpServletRequest request;
    HttpServletResponse response;
    FilterChain chain;

    private SecurityFilter aspect;

    @BeforeMethod
    public void setup(){
        // Mocks
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        chain = Mockito.mock(FilterChain.class);

        // Aspect
        aspect = new SecurityFilter();
    }

    //#region Data

    /**
     * Provides a list of invalid JWTs. This includes:
     *      - Random strings
     *      - JWTs made with a bad signature.
     *      - Valid JWTs that have been tampered with.
     * @throws UnsupportedEncodingException Can't encode the key properly.
     * @throws InvalidKeyException          Key is invalid.
     * @throws Exception                    Something went wrong with SecurityService::getJwt
     */
    @DataProvider(name = "dp_InvalidJwts")
    public Object[][] provideInvalidJwts() throws InvalidKeyException, UnsupportedEncodingException, Exception{
        String emptyString = "";
        String randomString = "Some random value.";

        String badSign = getBadlySignedJwt();
        String tampered = getTamperedJwt();
        
        return new Object[][]{
            {emptyString},
            {randomString},
            {badSign},
            {tampered}
        };
    }

    /**
     * Creates a JWT that complies with necessary claims, but with an invalid signature.
     * @return  Bad JWT.
     * @throws InvalidKeyException          Key is invalid.
     * @throws UnsupportedEncodingException Can't encode the key properly.
     */
    public String getBadlySignedJwt() throws InvalidKeyException, UnsupportedEncodingException{
        return Jwts.builder()
            .claim(SecurityService.ID_CLAIM, 1)
            .claim(SecurityService.C_NAME_CLAIM, "Company 1")
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plusSeconds(3600)))
            .signWith(getSigningKey())
            .compact();
    }

    /**
     * Creates the bad signing key for getBadlySignedJwt
     * @return  Key to sign the bad JWT with.
     * @throws UnsupportedEncodingException Can't encode the key properly.
     */
    private SecretKey getSigningKey() throws UnsupportedEncodingException {
        // Retrieve key from env and encrypt
        byte[] key;
        key = Base64.getEncoder().encode(("My bad key that has some padding and should not be accepted.").getBytes("UTF-8"));

        return Keys.hmacShaKeyFor(key);
    }

    /**
     * Retrieves a valid JWT and changes it to be invalid.
     * 
     * @return Tampered with JWT
     * @throws Exception Something went wrong with SecurityService::getJwt
     */
    public String getTamperedJwt() throws Exception{
        // Get regular JWT
        String jwt = SecurityService.getJwt(new AdministratorDto(1, "Company 1"));

        // Find a body index
        int headBodySep = jwt.indexOf('.');
        int bodySignSep = jwt.indexOf('.', headBodySep + 1);
        int offset = (bodySignSep - headBodySep) / 2;
        int index = headBodySep + offset;

        // Modify at index
        char[] arr = jwt.toCharArray();
        arr[index] = (char)((int)arr[index] + 1);

        // Return tampered-with jwt
        return new String(arr);
    }

    //#endregion

    //#region Tests

    /**
     * Tests to see if the SecurityFilter handles requests
     * with no authorization header appropriately.
     */
    @Test
    public void testNoAuthorizationHeader(){
        // Set up mocks
        when(request.getHeader("authorization"))
            .thenReturn(null);

        // Test
        try {
			aspect.doFilterInternal(request, response, chain);
		} catch (ServletException e) {
			Assert.fail("ServletException thrown.\n" + e.getMessage());
		} catch (IOException e) {
			Assert.fail("IOException thrown.\n" + e.getMessage());
		}

        verify(request, times(1)).removeAttribute("adminId");
        try {
			verify(chain, times(1)).doFilter(request, response);
		} catch (IOException | ServletException e) {
			Assert.fail("This should never happen with mocks, but an exception was thrown on the mocked chain.\n" + e.getMessage());
		}
    }

    /**
     * Tests to see if the SecurityFilter handles requests
     * with an invalid JWT appropriately.
     */
    @Test(dataProvider = "dp_InvalidJwts")
    public void testInvalidToken(String jwt){
        // Set up mocks
        when(request.getHeader("authorization"))
            .thenReturn(jwt);

        // Test
        try {
			aspect.doFilterInternal(request, response, chain);
		} catch (ServletException e) {
			Assert.fail("ServletException thrown.\n" + e.getMessage());
		} catch (IOException e) {
			Assert.fail("IOException thrown.\n" + e.getMessage());
		}

        verify(response, times(1)).setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * Tests to see if the SecurityFilter handles requests
     * that do not have an admin ID in the token appropriately.
     *
     * @throws Exception SecurityService::getJwt failed.
     */
    @Test
    public void testTokenMissingAdminId() throws Exception{
        // Grab necessary data
        AdministratorDto dto = new AdministratorDto();
        String jwt = SecurityService.getJwt(dto);

        // Set up mocks
        when(request.getHeader("authorization"))
            .thenReturn(jwt);
        
        // Test
        try {
			aspect.doFilterInternal(request, response, chain);
		} catch (ServletException e) {
			Assert.fail("ServletException thrown.\n" + e.getMessage());
		} catch (IOException e) {
			Assert.fail("IOException thrown.\n" + e.getMessage());
		}

        verify(request, times(0)).setAttribute("adminId", 1);
        verify(chain, times(0)).doFilter(request, response);
    }

    /**
     * Tests to see if the SecurityFilter handles requests
     * that are valid appropriately.
     * 
     * @throws Exception SecurityService::getJwt failed.
     */
    @Test
    public void testValidRequest() throws Exception{
        // Grab necessary data
        String jwt = SecurityService.getJwt(new AdministratorDto(1, "Company 1"));

        // Set up mocks
        when(request.getHeader("authorization"))
            .thenReturn(jwt);

        // Test
        try {
			aspect.doFilterInternal(request, response, chain);
		} catch (ServletException e) {
			Assert.fail("ServletException thrown.\n" + e.getMessage());
		} catch (IOException e) {
			Assert.fail("IOException thrown.\n" + e.getMessage());
		}

        verify(request, times(1)).setAttribute("adminId", 1);
        verify(chain, times(1)).doFilter(request, response);
    }

    //#endregion
}
