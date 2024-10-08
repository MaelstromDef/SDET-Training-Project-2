package com.skillstorm.testingComponents.pages.concretePages;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.StepDefinitions;
import com.skillstorm.testingComponents.Navbar;
import com.skillstorm.testingComponents.pages.IFormPage;
import com.skillstorm.testingComponents.pages.IObjectPage;
import com.skillstorm.testingComponents.pages.IPage;
import com.skillstorm.testingComponents.pages.abstractPages.ObjectPage;
import com.skillstorm.testingComponents.pages.abstractPages.Page;
import com.skillstorm.testingComponents.tools.Config;

public class ItemsPage extends ObjectPage {
    // --- FIELDS --- 

    private String urlExtension = "items";

    // Item form

    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/button")
    private WebElement btnOpenForm;

    private static final String IN_NAME_XPATH = "//*[@id=\"root\"]/div[2]/form/input[1]";
    @FindBy(xpath = IN_NAME_XPATH)
    private WebElement inName;

    private static final String IN_QUANTITY_XPATH = "//*[@id=\"root\"]/div[2]/form/input[2]";
    @FindBy(xpath = IN_QUANTITY_XPATH)
    private WebElement inQuantity;

    private static final String BTN_ADD_ITEM_XPATH = "//*[@id=\"root\"]/div[2]/form/input[3]";
    @FindBy(xpath = BTN_ADD_ITEM_XPATH)
    private WebElement btnAddItem;

    private static final String BTN_CLOSE_FORM_XPATH = "//*[@id=\"root\"]/div[2]/button";
    @FindBy(xpath = BTN_CLOSE_FORM_XPATH)
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
    private static final String BTN_DELETE_XPATH_MANAGING = "//*[@id=\"root\"]/table/tbody/tr/td[3]/button[3]";
    @FindBy(xpath = BTN_DELETE_XPATH)
    private WebElement btnDelete;

    // Object Viewpoints

    private static final String TABLE_HEAD_XPATH = "//*[@id=\"root\"]/table/thead";
    @FindBy(xpath = TABLE_HEAD_XPATH)
    private WebElement elTableHead;

    private static final String OBJECT_ROW_XPATH = "//*[@id=\"root\"]/table/tbody/tr";
    @FindBy(xpath = OBJECT_ROW_XPATH)
    private WebElement elObjectRow;

    private static final String OBJECT_NAME_XPATH = "//*[@id=\"root\"]/table/tbody/tr/td[1]";
    @FindBy(xpath = OBJECT_NAME_XPATH)
    private WebElement txtObjectName;

    private static final String OBJECT_QUANTITY_XPATH = "//*[@id=\"root\"]/table/tbody/tr/td[2]/div";
    @FindBy(xpath = OBJECT_QUANTITY_XPATH)
    private WebElement txtObjectQuantity;

    // Feedback

    private static final String TXT_FEEDBACK_XPATH = "//*[@id=\"root\"]/div[2]/form/label[3]";
    @FindBy(xpath = TXT_FEEDBACK_XPATH)
    private WebElement txtFeedback;

    private static final String MSG_ITEM_EXISTS = "That item already exists, please modify the quantity below.";
    private static final String MSG_ERROR = "Something went wrong, please try again.";
    private static final String MSG_EMPTY_FIELDS = "Name cannot be empty.";
    private static final String MSG_INVALID_QUANTITY = "Quantity must be greater than 0.";

    // --- CONSTRUCTORS ---

    public ItemsPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
        this.url = baseUrl + getUrlExtension();
    }

    // --- METHODS --- 

    @Override
    public void navigateToPage() {
        WarehousesPage warehousesPage = new WarehousesPage(driver, StepDefinitions.initialURL);
        warehousesPage.navigateToPage();
        warehousesPage.clickBtnManage();

        loadElements();
    }

    public void createItem(){
        loadElements();
        enterRightFormInformation();
        submitForm();
    }

    public void ensureItemExists(){
        createItem();
        loadElements();
    }

    //#region OBJECT

    @Override
    public void modifyObjectRight() {
        clickBtnManage();

        inUpdateQuantity.sendKeys(Config.VALID_ITEM_QUANTITY);
    }

    @Override
    public void modifyObjectWrong() {
        clickBtnManage();

        inUpdateQuantity.sendKeys(Config.INVALID_ITEM_QUANTITY);
    }

    @Override
    public boolean verifyListExistence() {
        return elTableHead.isDisplayed();
    }

    @Override
    public boolean verifyObjectExistence() {
        if (elObjectRow == null || txtObjectName == null) {return false;}
        return elObjectRow.isDisplayed() && txtObjectName.getText().equals(Config.VALID_ITEM_NAME);
    }

    @Override
    public boolean verifyObjectUpdated() {
        return txtObjectQuantity.getText().equals(Config.VALID_ITEM_QUANTITY);
    }

    //#endregion

    //#region FORM

    @Override
    public boolean verifySubmissionFailure() {
        String feedback = txtFeedback.getText();

        return feedback.equals(MSG_EMPTY_FIELDS) ||
            feedback.equals(MSG_ERROR) ||
            feedback.equals(MSG_INVALID_QUANTITY) ||
            feedback.equals(MSG_ITEM_EXISTS);
    }

    @Override
    public void enterWrongFormInformation() {
        clickBtnOpenForm();
        clearFormInformation();

        inName.sendKeys(Config.INVALID_ITEM_NAME);
        inQuantity.sendKeys(Config.INVALID_ITEM_QUANTITY);
    }

    @Override
    public void enterRightFormInformation() {
        clickBtnOpenForm();
        clearFormInformation();

        inName.sendKeys(Config.VALID_ITEM_NAME);
        inQuantity.sendKeys(Config.VALID_ITEM_QUANTITY);
    }

    @Override
    public boolean verifySubmissionSuccess() {
        return verifyObjectExistence();
    }

    @Override
    protected List<WebElement> getFormFields() {
        clickBtnOpenForm();
        return Arrays.asList(inName, inQuantity);
    }

    //#endregion

    //#region ACTIONS

    @Override
    public void performAction(String action) {
        switch (action) {
            case "New Item":
                clickBtnOpenForm();
                break;
            case "Item Update":
                clickBtnManage();
                break;
            default:
                throw new IllegalArgumentException("Action '" + action + "' does not exist.");
        }
    }

    @Override
    public boolean isUserPerformingAction(String action) {
        loadElements();
        switch(action){
            case "New Item":
                try {
                    return inName.isDisplayed();
                } catch (NoSuchElementException e) {
                    return false;
                }
            case "Item Update":
                try {
                    return inUpdateQuantity.isDisplayed();
                } catch (NoSuchElementException e) {
                    return false;
                }
            default:
                throw new IllegalArgumentException("Action '" + action + "' does not exist.");
        }
    }

    //#endregion

    //#region BUTTONS

    @Override
    public void clickButton(String btnName) {
        switch (btnName) {
            case "btnOpenForm":
                clickBtnOpenForm();
                break;
            case "btnCloseForm":
                clickBtnCloseForm();
                break;
            case "btnAddItem":
                clickBtnAddItem();
                break;
            case "btnManage":
                clickBtnManage();
                break;
            case "btnUpdateItem":
                clickBtnUpdateItem();
                break;
            case "btnCancel":
                clickBtnCancelUpdateItem();
                break;
            case "btnDeleteItem":
                clickBtnDelete();
                break;
            default:
                navbar.clickButton(btnName);
                break;
        }
    }

    public void clickBtnOpenForm(){
        try{
            inName.isDisplayed();
        }catch(NoSuchElementException e){
            btnOpenForm.click();
        }
    }

    public void clickBtnCloseForm(){
        btnCloseForm.click();
    }

    public void clickBtnAddItem(){
        clickBtnOpenForm();
        btnAddItem.click();
    }

    public void clickBtnManage(){
        ensureItemExists();
        btnManage.click();
    }

    public void clickBtnUpdateItem(){
        ensureItemExists();
        clickBtnManage();
        btnUpdateItem.click();
    }

    public void clickBtnCancelUpdateItem(){
        ensureItemExists();
        clickBtnManage();
        btnCancelUpdateItem.click();
    }

    public void clickBtnDelete(){
        ensureItemExists();
        try{
            btnDelete = driver.findElement(By.xpath(BTN_DELETE_XPATH));
            if(!btnDelete.getText().equals("Delete")){
                btnDelete = driver.findElement(By.xpath(BTN_DELETE_XPATH_MANAGING));
            }
        }catch(NoSuchElementException e){
            e.addInfo("Description", "btnDelete could not be loaded.");
            throw e;
        }
    }

    @Override
    protected WebElement getDeleteButton() {
        return btnDelete;
    }

    @Override
    protected WebElement getSubmitButton() {
        return btnAddItem;
    }

    //#endregion

    @Override
    protected String getUrlExtension() {
        return urlExtension;
    }
}