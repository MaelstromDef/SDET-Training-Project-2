package com.skillstorm.testingComponents.tools;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.skillstorm.testingComponents.pages.concretePages.LoginPage;
import com.skillstorm.testingComponents.pages.concretePages.SignupPage;

public class PageTools {
    public static String baseUrl = "http://ahuggins-warehousemanager-frontend.s3-website.us-east-2.amazonaws.com/";

    public static void logIn(WebDriver driver){
        LoginPage loginPage = new LoginPage(driver, baseUrl);
        loginPage.logIn();
    }

    public static void logOut(WebDriver driver) {
        try {
            WebElement btnLogOut = driver.findElement(By.xpath("//*[@id=\"root\"]/div/a[4]"));
            btnLogOut.click();
        } catch (NoSuchElementException e) {}
    }

    public static void signUp(WebDriver driver) {
        SignupPage signupPage = new SignupPage(driver, baseUrl);
        signupPage.navigateToPage();
        signupPage.enterRightFormInformation();
        signupPage.submitForm();
    }
}
