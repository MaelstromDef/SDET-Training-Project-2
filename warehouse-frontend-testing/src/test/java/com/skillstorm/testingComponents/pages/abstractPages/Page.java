package com.skillstorm.testingComponents.pages.abstractPages;

import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;

import com.skillstorm.testingComponents.Navbar;
import com.skillstorm.testingComponents.pages.IPage;
import com.skillstorm.testingComponents.tools.PageTools;

public abstract class Page implements IPage {
    // --- FIELDS ---

    protected WebDriver driver;
    protected String url;

    protected Navbar navbar;

    // --- CONSTRUCTORS ---

    public Page(WebDriver driver, String baseUrl){
        this.driver = driver;
        this.url = baseUrl + getUrlExtension();

        navbar = new Navbar(driver);
    }

    // --- METHODS ---

    /**
     * Retrieves the page's URL.
     */
    @Override
    public String getUrl() {
        return url;
    }

    /**
     * Logs the browser in.
     * 
     * @apiNote Does not navigate back to the represented page, use with caution. 'navigateToPage' method performs necessary log in/out functions, and is more highly advised.
     */
    @Override
    public void logIn() {
        PageTools.logIn(driver);
    }

    /**
     * Logs the browser out.
     * 
     * @apiNote Does not navigate back to the represented page, use with caution. 'navigateToPage' method performs necessary log in/out functions, and is more highly advised.
     */
    @Override
    public void logOut() {
        PageTools.logOut(driver);
    }

    /**
     * Checks if the browser is logged in.
     */
    @Override
    public boolean checkLoggedIn() {
        try {
            navbar.loadLoggedInButtons();
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the browser is logged out.
     */
    @Override
    public boolean checkLoggedOut() {
        try {
            navbar.loadLoggedOutButtons();
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    protected abstract String getUrlExtension();
}
