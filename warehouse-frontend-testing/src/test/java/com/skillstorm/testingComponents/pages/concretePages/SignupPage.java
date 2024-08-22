package com.skillstorm.testingComponents.pages.concretePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.testingComponents.Navbar;
import com.skillstorm.testingComponents.pages.FormPage;
import com.skillstorm.testingComponents.tools.Config;

public class SignupPage implements FormPage {
    private WebDriver driver;
    private String url;
    private String urlExtension = "/signup";

    // --- INTERACTABLES ---

    private Navbar navbar;

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

    // --- CONSTRUCTORS ---
    
    public SignupPage(WebDriver driver, String initialPage) {
        this.driver = driver;
        this.url = initialPage + "/" + urlExtension;

        navbar = new Navbar(driver);
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
                throw new IllegalArgumentException("Button '" + btnName + "' does not exist.");
        }
    }

    /**
     * Clicks the signup button.
     */
    public void clickBtnSignup(){
        btnSignup.click();
    }

    @Override
    public void clearFormInformation(){
        inCompanyName.clear();
        inPassword.clear();
        inConfirmPassword.clear();
    }

    @Override
    public void enterWrongFormInformation() {
        clearFormInformation();

        inCompanyName.sendKeys(Config.INVALID_COMPANY_NAME);
        inPassword.sendKeys(Config.INVALID_PASSWORD);
        inConfirmPassword.sendKeys(Config.INVALID_CONFIRM_PASSWORD);
    }
    @Override
    public void enterRightFormInformation() {
        clearFormInformation();

        inCompanyName.sendKeys(Config.VALID_COMPANY_NAME);
        inPassword.sendKeys(Config.VALID_PASSWORD);
        inConfirmPassword.sendKeys(Config.VALID_PASSWORD);
    }
    @Override
    public boolean submitForm() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'submitForm'");
    }

    @Override
    public void navigateToPage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'navigateToPage'");
    }

    @Override
    public String getURL() {
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

    @Override
    public boolean verifySubmissionFailure() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifySubmissionFailure'");
    }

    @Override
    public boolean verifySubmissionSuccess() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifySubmissionSuccess'");
    }
}
