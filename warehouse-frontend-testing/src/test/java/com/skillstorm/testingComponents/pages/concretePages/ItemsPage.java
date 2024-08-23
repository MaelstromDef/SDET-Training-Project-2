package com.skillstorm.testingComponents.pages.concretePages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.testingComponents.Navbar;
import com.skillstorm.testingComponents.pages.IFormPage;
import com.skillstorm.testingComponents.pages.IObjectPage;
import com.skillstorm.testingComponents.pages.IPage;
import com.skillstorm.testingComponents.pages.abstractPages.ObjectPage;
import com.skillstorm.testingComponents.pages.abstractPages.Page;

public class ItemsPage extends ObjectPage {
    // --- FIELDS --- 

    private String urlExtension = "/items";

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
    private WebElement btnCloseForm;

    // Items

    private static final String IN_UPDATE_QUANTITY_XPATH = "//*[@id=\"root\"]/table/tbody/tr/td[2]/div/input";
    @FindBy(xpath = IN_UPDATE_QUANTITY_XPATH)
    private WebElement inUpdateQuantity;

    private static final String BTN_MANAGE_XPATH = "//*[@id=\"root\"]/table/tbody/tr/td[3]/button[1]";
    @FindBy(xpath = BTN_MANAGE_XPATH)
    private WebElement btnManage;

    private static final String BTN_UPDATE_XPATH = "//*[@id=\"root\"]/table/tbody/tr/td[3]/button[1]";
    @FindBy(xpath = BTN_UPDATE_XPATH)
    private WebElement btnUpdateItem;

    private static final String BTN_CANCEL_UPDATE_XPATH = "//*[@id=\"root\"]/table/tbody/tr/td[3]/button[2]";
    @FindBy(xpath = BTN_CANCEL_UPDATE_XPATH)
    private WebElement btnCancelUpdateItem;

    private static final String BTN_DELETE_XPATH = "//*[@id=\"root\"]/table/tbody/tr/td[3]/button[2]";
    @FindBy(xpath = BTN_DELETE_XPATH)
    private WebElement btnDelete;

    // --- CONSTRUCTORS ---

    public ItemsPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    // --- METHODS --- 

    private void openItemModifier(){
        
    }

    @Override
    public void modifyObjectRight() {
        
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'navigateToPage'");
    }

    @Override
    public void clickButton(String btnName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clickButton'");
    }

    @Override
    protected WebElement getDeleteButton() {
        return btnDelete;
    }

    @Override
    protected List<WebElement> getFormFields() {
        return Arrays.asList(inName, inQuantity);
    }

    @Override
    protected WebElement getSubmitButton() {
        return btnAddItem;
    }

    @Override
    protected String getUrlExtension() {
        return urlExtension;
    }  
}