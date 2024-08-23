package com.skillstorm.testingComponents.pages.concretePages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.testingComponents.Navbar;
import com.skillstorm.testingComponents.pages.IFormPage;
import com.skillstorm.testingComponents.pages.IObjectPage;
import com.skillstorm.testingComponents.pages.abstractPages.ObjectPage;
import com.skillstorm.testingComponents.tools.PageTools;

public class AccountPage extends ObjectPage {
    // --- FIELDS --- 

    private String urlExtension = "/account";

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
    @FindBy(xpath = "//*[@id=\"root\"]/form/div[2]/input[1]")
    private WebElement btnConfirmDelete;    // Sends the request to delete the account.
    @FindBy(xpath = "//*[@id=\"root\"]/form/div[2]/input[2]")
    private WebElement btnCancelDelete;     // Closes the extra web elements shown when btnDelete is clicked.

    // FEEDBACK

    @FindBy(xpath = "//*[@id=\"root\"]/p")
    private WebElement txtFeedback;

    // --- CONSTRUCTORS ---

    public AccountPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
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

    @Override
    public void clickButton(String btnName) {
        switch(btnName){
            case "btnUpdate":
                clickBtnUpdate();
                break;
            case "btnDelete":
                clickBtnDelete();
                break;
            case "btnConfirmDelete":
                clickBtnConfirmDelete();
                break;
            case "btnCancelDelete":
                clickBtnCancelDelete();
                break;
            default:
                throw new InvalidArgumentException("Button '" + btnName + "' does not exist.");
        }
    }

    @Override
    public void modifyObjectRight() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyObjectRight'");
    }

    @Override
    public void modifyObjectWrong() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyObjectWrong'");
    }

    @Override
    public boolean verifyListExistence() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifyListExistence'");
    }

    @Override
    public boolean verifyObjectExistence() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifyObjectExistence'");
    }

    @Override
    public boolean verifyObjectUpdated() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifyObjectUpdated'");
    }

    @Override
    public void performAction(String action) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'performAction'");
    }

    @Override
    public boolean isUserPerformingAction(String action) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isUserPerformingAction'");
    }

    @Override
    public void enterWrongFormInformation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enterWrongFormInformation'");
    }

    @Override
    public void enterRightFormInformation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enterRightFormInformation'");
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
