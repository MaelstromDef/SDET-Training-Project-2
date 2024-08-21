package com.skillstorm.testingComponents.pages.concretePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.testingComponents.Navbar;
import com.skillstorm.testingComponents.pages.Page;

public class LandingPage implements Page {
    private WebDriver driver;
    private String url;
    private String urlExtension = "/landing";

    // --- INTERACTABLES ---

    private Navbar navbar;

    @FindBy(xpath = "//*[@id=\"root\"]/button[1]")
    private WebElement btnLogin;
    @FindBy(xpath = "//*[@id=\"root\"]/button[2]")
    private WebElement btnSignup;

    // --- CONSTRUCTORS ---

    public LandingPage(WebDriver driver, String initialPage) {
        this.driver = driver;
        this.url = initialPage + "/" + urlExtension;

        navbar = new Navbar(driver);
    }

    // --- METHODS ---

    /**
     * Navigates the browser to the Landing page.
     * @throws Exception If some failure to navigate to the Landing page occurs.
     */
    @Override
    public void navigateToPage() throws Exception {
        logOut();                   // Ensure page is logged out.
        navbar.clickBtnLanding();   // Navigate to landing.

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
     * 
     * @throws UnsupportedOperationException The Landing page is a logged-out only page. Use the LoginPage class instead.
     */
    @Override
    public void logIn() {
        // Purposeful exception.
        throw new UnsupportedOperationException("Landing page is a logged-out only page.");
    }

    /**
     * Goes through the process of logging out.
     */
    @Override
    public void logOut() {
        try{
            navbar.loadLoggedOutButtons();
        }catch(Exception e){
            navbar.clickBtnLogOut();
        }
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
