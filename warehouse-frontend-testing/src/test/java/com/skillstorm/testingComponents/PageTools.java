package com.skillstorm.testingComponents;

import org.openqa.selenium.WebDriver;

import com.skillstorm.StepDefinitions;
import com.skillstorm.testingComponents.pages.concretePages.LoginPage;

public class PageTools {
    public static void logIn(WebDriver driver){
        LoginPage loginPage = new LoginPage(driver, StepDefinitions.initialURL);
        loginPage.navigateToPage();
        loginPage.enterRightFormInformation();
        loginPage.submitForm();
    }
}
