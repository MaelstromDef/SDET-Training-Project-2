package com.skillstorm.testingComponents.pages.concretePages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.testingComponents.pages.FormPage;
import com.skillstorm.testingComponents.pages.ObjectPage;
import com.skillstorm.testingComponents.pages.Page;

public class WarehousesPage implements FormPage, ObjectPage {
    // INTERACTABLES

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
}