package com.skillstorm.testingComponents.pages.concretePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.testingComponents.Navbar;
import com.skillstorm.testingComponents.PageTools;
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

    /**
     * Navigates the browser to the Home page.
     * @throws Exception If some failure to navigate to the Home page occurs.
     */
    @Override
    public void navigateToPage() throws Exception {
        logIn();                // Ensures logged in.
        navbar.clickBtnHome();  // Navigates to home.
        
        if(driver.getCurrentUrl() != url) throw new Exception("Failed to navigate to Landing page.");
    }

    /**
     * Retrieves the page's URL.
     * 
     * @return The page's URL.
     */
    @Override
    public Object getURL() {
        return url;
    }

    /**
     * Goes through the process of logging in.
     */
    @Override
    public void logIn() {
        try{
            navbar.loadLoggedInButtons();
        }catch(Exception e){
            PageTools.logIn(driver);
        }
    }

    /**
     * Goes through the process of logging out.
     */
    @Override
    public void logOut() {
        try{
            navbar.clickBtnLogOut();
        }catch(Exception e){}
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
