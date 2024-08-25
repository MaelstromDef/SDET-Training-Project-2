package com.skillstorm.testingComponents.pages.concretePages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.testingComponents.Navbar;
import com.skillstorm.testingComponents.pages.IFormPage;
import com.skillstorm.testingComponents.pages.abstractPages.FormPage;
import com.skillstorm.testingComponents.tools.Config;
import com.skillstorm.testingComponents.tools.PageTools;

public class LoginPage extends FormPage {
    // --- FIELDS --- 

    private String urlExtension = "login";

    // INTERACTABLES

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

    public LoginPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
        this.url = baseUrl + getUrlExtension();
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

    @Override
    public void logIn() {
        // Attempt signup
        PageTools.signUp(driver);
        
        //TODO: code below was causing a loop
        //logIn();    // Very dangerous....

        // Attempt log in
        navigateToPage();
        enterRightFormInformation();
        submitForm();
        
        //TODO: The submission verification was giving failure because there wasn't enough time for the form to redirect
        //to a new page before checking, so i put a pause in and that works for now but we need to find another fix.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
        if(!verifySubmissionSuccess()) throw new RuntimeException("FATAL: Could not log in.");   // Check success
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

    @Override
    public void navigateToPage() {
        logOut();
        driver.get(url);
        loadElements();
    }

    @Override
    protected List<WebElement> getFormFields() {
        return Arrays.asList(inCompanyName, inPassword);
    }

    @Override
    protected WebElement getSubmitButton() {
        return btnLogIn;
    }

    @Override
    protected String getUrlExtension() {
        return urlExtension;
    }
}