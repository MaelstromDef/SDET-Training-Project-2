package com.ahuggins.warehousedemo.componentTests.Aspects;

import org.aspectj.lang.JoinPoint;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ahuggins.warehousedemo.aspects.LoggingAspect;

public class LoggingAspectTests {
    private LoggingAspect aspect;

    @BeforeMethod
    public void setup(){
        aspect = new LoggingAspect();
    }

    @Test
    public void testRequest(){
        JoinPoint joinPoint = Mockito.mock(JoinPoint.class);
        
        Assert.assertThrows(Exception.class, () -> aspect.request(joinPoint));
    }
}
