package com.skillstorm.testingComponents.pages.concretePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.testingComponents.pages.Page;

public class LandingPage implements Page {
    private WebDriver driver;
    private String url;
    private String urlExtension = "/landing";

    @FindBy(xpath = "//*[@id=\"root\"]/button[1]")
    private WebElement btnLogin;
    @FindBy(xpath = "//*[@id=\"root\"]/button[2]")
    private WebElement btnSignup;

    // CONSTRUCTORS
    public LandingPage(WebDriver driver, String initialPage) {
        this.driver = driver;
        this.url = initialPage + "/" + urlExtension;
    }

    @Override
    public void navigateToPage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'navigateToPage'");
    }

    @Override
    public Object getURL() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getURL'");
    }
}
