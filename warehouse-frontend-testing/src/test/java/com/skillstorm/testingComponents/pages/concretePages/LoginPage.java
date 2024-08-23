package com.skillstorm.testingComponents.pages.concretePages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.testingComponents.Navbar;
import com.skillstorm.testingComponents.pages.FormPage;
import com.skillstorm.testingComponents.tools.Config;

public class LoginPage implements FormPage {
    private WebDriver driver;
    private String url;
    private String urlExtension = "/login";

    // --- INTERACTABLES ---

    private Navbar navbar;

    @FindBy(xpath = "//*[@id=\"root\"]/form/input[1]")
    private WebElement inCompanyName;

    @FindBy(xpath = "//*[@id=\"root\"]/form/input[2]")
    private WebElement inPassword;

    @FindBy(xpath = "//*[@id=\"root\"]/form/input[3]")
    private WebElement btnLogIn;

    // FEEDBACK

    @FindBy(xpath = "//*[@id=\"root\"]/p")
    private WebElement txtFeedback;

    private static final String INVALID_LOGIN_MESSAGE = "Please fill in all fields.";
    private static final String FAILED_LOGIN_MESSAGE = "Invalid credentials.";

    // --- CONSTRUCTORS ---

    public LoginPage(WebDriver driver, String initialPage) {
        this.driver = driver;
        this.url = initialPage + "/" + urlExtension;

        navbar = new Navbar(driver);
    }

    // --- METHODS ---

    /**
     * Interprets the button name to be clicked and calls the appropriate method.
     * 
     * @param btnName Name of the button to click. May be "btnLogin"
     * @throws IllegalArgumentException Button does not exist.
     */
    @Override
    public void clickButton(String btnName) {
        switch (btnName) {
            case "btnLogin":
                clickBtnLogIn();
                break;
            default:
                throw new IllegalArgumentException("Button '" + btnName + "' does not exist.");
        }
    }

    /**
     * Clicks the log in button.
     */
    public void clickBtnLogIn(){
        btnLogIn.click();
    }

    /**
     * Clears all login form fields.
     */
    @Override
    public void clearFormInformation(){
        inCompanyName.clear();
        inPassword.clear();
    }

    /**
     * Enters invalid login information to the login form.
     */
    @Override
    public void enterWrongFormInformation() {
        clearFormInformation();

        inCompanyName.sendKeys(Config.INVALID_COMPANY_NAME);
        inPassword.sendKeys(Config.INVALID_PASSWORD);
    }

    /**
     * Enters valid login information to the login form.
     */
    @Override
    public void enterRightFormInformation() {
        clearFormInformation();

        inCompanyName.sendKeys(Config.VALID_COMPANY_NAME);
        inPassword.sendKeys(Config.VALID_PASSWORD);
    }

    /**
     * Submits the login form.
     */
    @Override
    public boolean submitForm() {
        btnLogIn.click();
        return true;
    }

    /**
     * Navigates the browser to the Login page.
     * @throws Exception If some failure to navigate to the Login page occurs.
     */
    @Override
    public void navigateToPage() throws Exception {
        logOut();                   // Ensure logged out.
        navbar.clickBtnLogin();     // Navigate to page.

        if(!driver.getCurrentUrl().equals(url)) throw new Exception("Failed to navigate to Landing page.");
    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public void logIn() {
        enterRightFormInformation();
        submitForm();
    }

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

    /**
     * Checks if the login failure message appears.
     */
    @Override
    public boolean verifySubmissionFailure() {
        return txtFeedback.getText().equals(INVALID_LOGIN_MESSAGE) ||
            txtFeedback.getText().equals(FAILED_LOGIN_MESSAGE);
    }

    /**
     * Checks if the page redirected to Home.
     */
    @Override
    public boolean verifySubmissionSuccess() {
        return !driver.getCurrentUrl().equals(url);
    }
}