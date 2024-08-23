package com.skillstorm.testingComponents.pages.concretePages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.testingComponents.Navbar;
import com.skillstorm.testingComponents.pages.IFormPage;
import com.skillstorm.testingComponents.pages.IObjectPage;
import com.skillstorm.testingComponents.pages.abstractPages.ObjectPage;
import com.skillstorm.testingComponents.tools.Config;
import com.skillstorm.testingComponents.tools.PageTools;

public class AccountPage extends ObjectPage {
    // --- FIELDS --- 

    private String urlExtension = "account";

    // INTERACTABLES

    // Company name
    @FindBy(xpath = "//*[@id=\"root\"]/form/div/input")
    private WebElement inCompanyName;
    @FindBy(xpath = "//*[@id=\"root\"]/form/div/div/input")
    private WebElement chkCompanyName;  // Checkbox to enable company name input field.

    // Password
    @FindBy(xpath = "//*[@id=\"root\"]/form/input[1]")
    private WebElement inPassword;

    // Update account
    @FindBy(xpath = "//*[@id=\"root\"]/form/input[2]")
    private WebElement btnUpdate;

    // Delete account
    @FindBy(xpath = "//*[@id=\"root\"]/form/input[3]")
    private WebElement btnDelete;

    private static final String BTN_CONFIRM_DELETE_XPATH = "//*[@id=\"root\"]/form/div[2]/input[1]";
    @FindBy(xpath = BTN_CONFIRM_DELETE_XPATH)
    private WebElement btnConfirmDelete;    // Sends the request to delete the account.

    private static final String BTN_CANCEL_DELETE_XPATH = "//*[@id=\"root\"]/form/div[2]/input[2]";
    @FindBy(xpath = BTN_CANCEL_DELETE_XPATH)
    private WebElement btnCancelDelete;     // Closes the extra web elements shown when btnDelete is clicked.

    // FEEDBACK

    @FindBy(xpath = "//*[@id=\"root\"]/p")
    private WebElement txtFeedback;

    private static final String MSG_ACCOUNT_UPDATED = "Account updated.";

    // --- CONSTRUCTORS ---

    public AccountPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
        this.url = baseUrl + getUrlExtension();
    }

    // --- METHODS ---

    // PAGE UNIQUES

    /**
     * Clicks the update button.
     */
    public void clickBtnUpdate(){
        btnUpdate.click();
    }

    /**
     * Clicks the delete button.
     */
    public void clickBtnDelete(){
        btnDelete.click();
    }

    /**
     * Clicks the confirm delete button.
     */
    public void clickBtnConfirmDelete(){
        btnConfirmDelete.click();
    }

    /**
     * Clicks the cancel delete button.
     */
    public void clickBtnCancelDelete(){
        btnCancelDelete.click();
    }

    // BASIC PAGE

    /**
     * Interprets button names and clicks the appropriate button.
     * 
     * @param btnName Name of the button to click
     * @throws IllegalArgumentException Button name passed does not correspond to any existing button.
     */
    @Override
    public void clickButton(String btnName) {
        switch(btnName){
            case "btnUpdateAccount":
                clickBtnUpdate();
                break;
            case "btnDeleteAccount":
                clickBtnDelete();
                break;
            case "btnConfirmDeleteAccount":
                clickBtnConfirmDelete();
                break;
            case "btnCancelDeleteAccount":
                clickBtnCancelDelete();
                break;
            default:
                throw new InvalidArgumentException("Button '" + btnName + "' does not exist.");
        }
    }

    // OBJECT

    /**
     * Ensures the company name can be editted.
     */
    private void makeCompanyNameEditable(){
        if(!chkCompanyName.isSelected()){
            chkCompanyName.click();
        }
    }

    /**
     * Correctly modifies the object fields
     */
    @Override
    public void modifyObjectRight() {
        makeCompanyNameEditable();

        inCompanyName.sendKeys(Config.VALID_COMPANY_NAME);
        inPassword.sendKeys(Config.VALID_PASSWORD);
    }

    /**
     * Incorrectly modifies the object fields.
     */
    @Override
    public void modifyObjectWrong() {
        makeCompanyNameEditable();

        inCompanyName.sendKeys(Config.INVALID_COMPANY_NAME);
        inPassword.sendKeys(Config.INVALID_PASSWORD);
    }

    /**
     * Verifies the existence of the objects list.
     */
    @Override
    public boolean verifyListExistence() {
        return true;    // Account has special object type that does not have a list.
    }

    /**
     * Verifies the existence of the specific object.
     */
    @Override
    public boolean verifyObjectExistence() {
        return !(inCompanyName.getText().trim().equals(""));
    }

    /**
     * Verifies the object was correctly updated.
     */
    @Override
    public boolean verifyObjectUpdated() {
        return txtFeedback.getText().equals(MSG_ACCOUNT_UPDATED);
    }

    /**
     * Performs a given action.
     * 
     * @param action Action to perform.
     */
    @Override
    public void performAction(String action) {
        switch (action) {
            case "Deleting Account":
                btnDelete.click();
                break;
        
            default:
                throw new IllegalArgumentException("Action '" + action + "' does not exist.");
        }
    }

    /**
     * Checks if a given action is being performed.
     * 
     * @param action Action to check for.
     */
    @Override
    public boolean isUserPerformingAction(String action) {
        switch(action){
            case "Deleting Account":
                btnConfirmDelete = driver.findElement(By.xpath(BTN_CONFIRM_DELETE_XPATH));
                btnCancelDelete = driver.findElement(By.xpath(BTN_CANCEL_DELETE_XPATH));

                return btnConfirmDelete != null && btnCancelDelete != null;
            default:
                throw new IllegalArgumentException("Action '" + action + "' does not exist.");
        }
    }

    /**
     * Enters incorrect information into the form.
     */
    @Override
    public void enterWrongFormInformation() {
        modifyObjectWrong();
    }

    /**
     * Enters correct information into the form.
     */
    @Override
    public void enterRightFormInformation() {
        modifyObjectRight();
    }

    /**
     * Checks if the form submission was unsuccessful.
     */
    @Override
    public boolean verifySubmissionFailure() {
        return !verifyObjectUpdated();
    }

    /**
     * Checks if the form submission was successful.
     */
    @Override
    public boolean verifySubmissionSuccess() {
        return verifyObjectUpdated();
    }

    /**
     * Navigates to the represented page.
     */
    @Override
    public void navigateToPage() {
        logIn();
        driver.get(url);
    }

    @Override
    protected WebElement getDeleteButton() {
        return btnDelete;
    }

    @Override
    protected List<WebElement> getFormFields() {
        return Arrays.asList(inCompanyName, inPassword);
    }

    @Override
    protected WebElement getSubmitButton() {
        return btnUpdate;
    }

    @Override
    protected String getUrlExtension() {
        return urlExtension;
    }
}
