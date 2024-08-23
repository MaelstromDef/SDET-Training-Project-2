package com.skillstorm.testingComponents.pages.concretePages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.skillstorm.testingComponents.pages.abstractPages.ObjectPage;

public class WarehousesPage extends ObjectPage {
    private String urlExtension = "/warehouses";

    // --- INTERACTABLES ---

    // Add warehouse form
    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/button")
    private WebElement btnOpenForm;

    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/form/input[1]")
    private WebElement inName;
    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/form/input[2]")
    private WebElement inLocation;
    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/form/input[3]")
    private WebElement inSize;

    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/form/input[4]")
    private WebElement btnAddWarehouse;

    @FindBy(xpath = "//*[@id=\"root\"]/div[2]")
    private WebElement btnCancel;

    // Warehouse management

    @FindBy(xpath = "//*[@id=\"root\"]/table/tbody/tr/td[4]/button[1]")
    private WebElement btnManage;

    @FindBy(xpath = "//*[@id=\"root\"]/table/tbody/tr/td[4]/button[2]")
    private WebElement btnDelete;

    // --- CONSTRUCTORS ---

    public WarehousesPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
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
        return Arrays.asList(inLocation, inName, inSize);
    }

    @Override
    protected WebElement getSubmitButton() {
        return btnAddWarehouse;
    }

    @Override
    protected String getUrlExtension() {
        return urlExtension;
    }
}