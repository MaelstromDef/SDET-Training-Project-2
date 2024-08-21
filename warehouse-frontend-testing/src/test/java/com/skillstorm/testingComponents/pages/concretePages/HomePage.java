package com.skillstorm.testingComponents.pages.concretePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.testingComponents.Navbar;
import com.skillstorm.testingComponents.pages.Page;

public class HomePage implements Page {
    private WebDriver driver;
    private String url;
    private String urlExtension = "/homepage";

    // INTERACTABLES

    private Navbar navbar;

    @FindBy(xpath = "//*[@id=\"root\"]/button[1]")
    private WebElement btnAccount;

    @FindBy(xpath = "//*[@id=\"root\"]/button[2]")
    private WebElement btnWarehouses;

    // CONSTRUTORS
    public HomePage(WebDriver driver, String initialPage) {
        this.driver = driver;
        this.url = initialPage + "/" + urlExtension;

        navbar = new Navbar(driver);
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

    @Override
    public void logIn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'logIn'");
    }

    @Override
    public void logOut() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'logOut'");
    }

    @Override
    public void checkLoggedIn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkLoggedIn'");
    }

    @Override
    public void checkLoggedOut() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkLoggedOut'");
    }
}
