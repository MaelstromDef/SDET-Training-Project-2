package com.skillstorm.testingComponents.pages.concretePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.testingComponents.Navbar;
import com.skillstorm.testingComponents.pages.FormPage;
import com.skillstorm.testingComponents.pages.ObjectPage;
import com.skillstorm.testingComponents.pages.Page;

public class AccountPage implements FormPage, ObjectPage {
    private WebDriver driver;
    private String url;
    private String urlExtension = "/account";

    // INTERACTABLES

    private Navbar navbar;

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

    // CONSTRUCTORS
    public AccountPage(WebDriver driver, String initialPage) {
        this.driver = driver;
        this.url = initialPage + "/" + urlExtension;

        navbar = new Navbar(driver);
    }

    // METHODS

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
    public void deleteObject() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteObject'");
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
    public Object getURL() {
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
}
