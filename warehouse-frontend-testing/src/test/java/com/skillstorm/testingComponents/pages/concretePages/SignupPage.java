package com.skillstorm.testingComponents.pages.concretePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.StepDefinitions;
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

    private static final String INVALID_SIGNUP_MESSAGE = "Please fill in all fields.";
    private static final String PASSWORD_MISMATCH_MESSAGE = "Passwords don't match.";
    private static final String EXISTING_COMPANY_MESSAGE = "That company is already signed up.";

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

    /**
     * Clears all signup form fields.
     */
    @Override
    public void clearFormInformation(){
        inCompanyName.clear();
        inPassword.clear();
        inConfirmPassword.clear();
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
     * Submits the signup form.
     */
    @Override
    public boolean submitForm() {
        clickBtnSignup();
        return true;
    }

    /**
     * Navigates the browser to the Signup page.
     * @throws Exception If some failure to navigate to the Signup page occurs.
     */
    @Override
    public void navigateToPage() throws Exception {
        logOut();                   // Ensure logged out.
        navbar.clickBtnSignup();     // Navigate to page.

        if(!driver.getCurrentUrl().equals(url)) throw new Exception("Failed to navigate to Signup page.");
    }

    /**
     * Retrieves the page's URL.
     * 
     * @return The page's URL.
     */
    @Override
    public String getURL() {
        return url;
    }

    /**
     * Goes through the process of logging in.
     * 
     * @throws UnsupportedOperationException The Signup page is a logged-out only page. Use the LoginPage class instead.
     */
    @Override
    public void logIn() {
        // Purposeful exception.
        throw new UnsupportedOperationException("Signup page is a logged-out only page.");
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
            
            || driver.getCurrentUrl().equals(new LoginPage(driver, StepDefinitions.initialURL).getURL());
    }
}
