package com.skillstorm.testingComponents.pages.concretePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.testingComponents.Navbar;
import com.skillstorm.testingComponents.pages.FormPage;
import com.skillstorm.testingComponents.pages.ObjectPage;
import com.skillstorm.testingComponents.pages.Page;

public class ItemsPage implements FormPage, ObjectPage {
    private WebDriver driver;
    private String url;
    private String urlExtension = "/item";

    // INTERACTABLES

    private Navbar navbar;

    // Item form

    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/button")
    private WebElement btnOpenForm;

    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/form/input[1]")
    private WebElement inName;
    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/form/input[2]")
    private WebElement inQuantity;

    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/form/input[3]")
    private WebElement btnAddItem;
    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/button")
    private WebElement btnCancel;

    // Items

    @FindBy(xpath = "//*[@id=\"root\"]/table/tbody/tr/td[3]/button[1]")
    private WebElement btnManage;
    @FindBy(xpath = "//*[@id=\"root\"]/table/tbody/tr/td[3]/button[2]")
    private WebElement btnDelete;

    // CONSTRUCTORS
    public ItemsPage(WebDriver driver, String initialPage) {
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
