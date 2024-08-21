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

    // --- INTERACTABLES ---

    private Navbar navbar;

    @FindBy(xpath = "//*[@id=\"root\"]/button[1]")
    private WebElement btnAccount;

    @FindBy(xpath = "//*[@id=\"root\"]/button[2]")
    private WebElement btnWarehouses;

    //  --- CONSTRUCTORS ---

    public HomePage(WebDriver driver, String initialPage) {
        this.driver = driver;
        this.url = initialPage + "/" + urlExtension;

        navbar = new Navbar(driver);
    }

    // --- METHODS ---

    @Override
    public void navigateToPage() throws Exception {
        try{
            navbar.loadLoggedInButtons();
        }catch(Exception e){
            logIn();
        }

        navbar.clickBtnHome();
        if(driver.getCurrentUrl() != url) throw new Exception("Failed to navigate to Landing page.");
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

    /**
     * Checks to see if the page is currently logged in.
     * 
     * @return logged in status.
     */
    @Override
    public boolean checkLoggedIn() {
        navbar.loadLoggedInButtons();
        return true;
    }

    /**
     * Checks to see if the page is currently logged out.
     * 
     * @return logged out status.
     */
    @Override
    public boolean checkLoggedOut() {
        navbar.loadLoggedOutButtons();
        return true;
    }
}
