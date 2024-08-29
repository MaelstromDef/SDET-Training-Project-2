package com.skillstorm.testingComponents.pages.concretePages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.StepDefinitions;
import com.skillstorm.testingComponents.pages.abstractPages.FormPage;
import com.skillstorm.testingComponents.tools.Config;

public class SignupPage extends FormPage {
    private String urlExtension = "signup";

    // --- INTERACTABLES ---

    @FindBy(xpath = "//*[@id=\"root\"]/form/input[1]")
    private WebElement inCompanyName;

    @FindBy(xpath = "//*[@id=\"root\"]/form/input[2]")
    private WebElement inPassword;

    @FindBy(xpath = "//*[@id=\"root\"]/form/input[3]")
    private WebElement inConfirmPassword;

    @FindBy(xpath = "//*[@id=\"root\"]/form/input[4]")
    private WebElement btnSignup;

    // FEEDBACK

    @FindBy(xpath = "//*[@id=\"root\"]/p")
    private WebElement txtFeedback;

    private static final String INVALID_SIGNUP_MESSAGE = "Please fill in all fields.";
    private static final String PASSWORD_MISMATCH_MESSAGE = "Passwords don't match.";
    private static final String EXISTING_COMPANY_MESSAGE = "That company is already signed up.";

    // --- CONSTRUCTORS ---
    
    public SignupPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
        this.url = baseUrl + getUrlExtension();
    }

    // METHODS

    /**
     * Interprets the button name to be clicked and calls the appropriate method.
     * 
     * @param btnName Name of the button to click. May be "btnSignup"
     * @throws IllegalArgumentException Button does not exist.
     */
    @Override
    public void clickButton(String btnName) {
        switch(btnName){
            case "btnSignup":
                clickBtnSignup();
                break;
            default:
                navbar.clickButton(btnName);
                break;
        }
    }

    /**
     * Clicks the signup button.
     */
    public void clickBtnSignup(){
        btnSignup.click();
    }

    /**
     * Enters invalid information to the form.
     */
    @Override
    public void enterWrongFormInformation() {
        clearFormInformation();

        inCompanyName.sendKeys(Config.INVALID_COMPANY_NAME);
        inPassword.sendKeys(Config.INVALID_PASSWORD);
        inConfirmPassword.sendKeys(Config.INVALID_CONFIRM_PASSWORD);
    }

    /**
     * Enters valid information to the signup form.
     */
    @Override
    public void enterRightFormInformation() {
        clearFormInformation();

        inCompanyName.sendKeys(Config.VALID_COMPANY_NAME);
        inPassword.sendKeys(Config.VALID_PASSWORD);
        inConfirmPassword.sendKeys(Config.VALID_PASSWORD);
    }

    /**
     * Checks if the signup failure message appears.
     */
    @Override
    public boolean verifySubmissionFailure() {
        return txtFeedback.getText().equals(INVALID_SIGNUP_MESSAGE) ||
            txtFeedback.getText().equals(EXISTING_COMPANY_MESSAGE) ||
            txtFeedback.getText().equals(PASSWORD_MISMATCH_MESSAGE);
    }

    /**
     * Checks if the success message appears, or the page has rerouted to the LoginPage
     */
    @Override
    public boolean verifySubmissionSuccess() {
        return txtFeedback.getText()
            .equals("Success! \"" + Config.VALID_COMPANY_NAME + "\" was successfully registered. Please log in. (You will be redirected in 3 seconds.)")
            
            || driver.getCurrentUrl().equals(new LoginPage(driver, StepDefinitions.initialURL).getUrl());
    }

    @Override
    public void navigateToPage() {
        logOut();
        navbar.clickBtnSignup();

        awaitValidUrl();
        loadElements();
    }

    @Override
    protected List<WebElement> getFormFields() {
        loadElements();
        return Arrays.asList(inCompanyName, inPassword, inConfirmPassword);
    }

    @Override
    protected WebElement getSubmitButton() {
        return btnSignup;
    }

    @Override
    protected String getUrlExtension() {
        return urlExtension;
    }
}
