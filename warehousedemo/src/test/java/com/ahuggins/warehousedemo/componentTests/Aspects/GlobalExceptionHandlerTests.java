package com.ahuggins.warehousedemo.componentTests.Aspects;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.aspects.GlobalExceptionHandler;

public class GlobalExceptionHandlerTests {
    private GlobalExceptionHandler aspect;

    @BeforeMethod
    public void setup(){
        aspect = new GlobalExceptionHandler();
    }

    /**
     * Tests that handleStatusException creates the appropriate response entity.
     */
    @Test
    public void testHandleStatusException(){
        String message = "Testing status exceptions.";
        HttpStatusCode code = HttpStatus.BAD_REQUEST;
        ResponseStatusException e = new ResponseStatusException(code, message);

        ResponseEntity<Object> expected = new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        ResponseEntity<Object> actual = aspect.handleStatusException(e);

        Assert.assertEquals(actual, expected);
    }

    /**
     * Tests that handleIllegalAccess creates the appropriate response entity.
     */
    @Test
    public void testHandleIllegalAccess(){
        String message = "Testing illegal access.";
        HttpStatusCode code = HttpStatus.UNAUTHORIZED;

        ResponseEntity<Object> expected = new ResponseEntity<>(message, code);
        ResponseEntity<Object> actual = aspect.handleIllegalAccess(new IllegalAccessException(message));

        Assert.assertEquals(actual, expected);
    }
}
