package com.ahuggins.warehousedemo.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    Logger logger = LoggerFactory.getLogger(getClass());

    public LoggingAspect(){
        logger.debug("Hello World! This is logger!");
    }

    // Basic admin retrieval commands should only be used in testing

    @Pointcut("within(com.ahuggins.warehousedemo.controllers.AdminController)")
    public void checkFindAdmins(){}

    @Pointcut("")
    public void allAdminControllerMethods(){}

    @Before("execution(public * com.ahuggins.warehousedemo.controllers.AdminController.find*(..))")
    public void request(JoinPoint joinPoint) throws Exception{
        String flag = System.getenv("allowFindAdmin");
        logger.debug("CURRENT FLAG " + flag);
        if(flag.isEmpty() || flag.equals("prod")) throw new UnsupportedOperationException("Find admininstrator operations no longer allowed");
    }

    @Pointcut("execution(public ResponseEntity<AdministratorDto> com.ahuggins.warehousedemo.controllers.AdminController.findAdministratorById(int))")
    void checkFindAdminById(){}
}
