package com.ahuggins.warehousedemo;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.testng.Assert;
import org.testng.annotations.Test;


public class HelloWorldTests {
    @Test
    public void helloWorld(){
        Assert.assertTrue(true);
    }

    @Test
    public void checkEnvironment(){
        assertNotNull(System.getenv("secretKey"));
    }
}
