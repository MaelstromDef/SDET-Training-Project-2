package com.skillstorm.testingComponents.tools;

import org.openqa.selenium.WebDriver;

import com.skillstorm.testingComponents.pages.concretePages.LoginPage;

public class PageTools {
    public static String baseUrl = "http://ahuggins-warehousemanager-frontend.s3-website.us-east-2.amazonaws.com/";

    public static void logIn(WebDriver driver){
        LoginPage loginPage = new LoginPage(driver, baseUrl);
        loginPage.navigateToPage();
        loginPage.enterRightFormInformation();
        loginPage.submitForm();
    }

    public static void logOut(WebDriver driver) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'logOut'");
    }
}
