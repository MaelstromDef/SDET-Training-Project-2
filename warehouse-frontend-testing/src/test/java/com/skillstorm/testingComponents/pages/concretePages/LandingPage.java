package com.skillstorm.testingComponents.pages.concretePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.testingComponents.pages.abstractPages.Page;

public class LandingPage extends Page {
    private String urlExtension = "landing";

    // --- INTERACTABLES ---

    @FindBy(xpath = "//*[@id=\"root\"]/button[1]")
    private WebElement btnLogin;
    @FindBy(xpath = "//*[@id=\"root\"]/button[2]")
    private WebElement btnSignup;

    // --- CONSTRUCTORS ---

    public LandingPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
        this.url = baseUrl + getUrlExtension();
    }

    // --- METHODS ---

    /**
     * Interprets the button name to be clicked and calls the appropriate method.
     * 
     * @param btnName Name of the button to click. May be either "btnLogin" or "btnSignup"
     * @throws IllegalArgumentException Button does not exist.
     */
    @Override
    public void clickButton(String btnName) {
        switch(btnName){
            case "btnLogin":
                clickBtnLogin();
                break;
            case "btnSignup":
                clickBtnSignup();
                break;
            default:
                throw new IllegalArgumentException("Button '" + btnName + "' does not exist.");
        }
    }

    /**
     * Clicks the login button.
     */
    public void clickBtnLogin(){
        btnLogin.click();
    }

    /**
     * Clicks the signup button.
     */
    public void clickBtnSignup(){
        btnSignup.click();
    }

    /**
     * Navigates the browser to the Landing page.
     * @throws Exception If some failure to navigate to the Landing page occurs.
     */
    @Override
    public void navigateToPage() {
        logOut();                   // Ensure page is logged out.
        navbar.clickBtnLanding();   // Navigate to landing.
    }

    @Override
    protected String getUrlExtension() {
        return urlExtension;
    }
}
