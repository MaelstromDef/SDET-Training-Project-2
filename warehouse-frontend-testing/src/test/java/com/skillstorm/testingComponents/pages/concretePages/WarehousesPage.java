package com.skillstorm.testingComponents.pages.concretePages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.skillstorm.testingComponents.pages.abstractPages.ObjectPage;
import com.skillstorm.testingComponents.tools.Config;

public class WarehousesPage extends ObjectPage {
    private String urlExtension = "warehouses";

    // --- INTERACTABLES ---

    // Add warehouse form
    private static final String BTN_OPEN_FORM_XPATH = "//*[@id=\"root\"]/div[2]/button";
    @FindBy(xpath = BTN_OPEN_FORM_XPATH)
    private WebElement btnOpenForm;

    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/form/input[1]")
    private WebElement inName;

    private static final String IN_LOCATION_XPATH = "//*[@id=\"root\"]/div[2]/form/input[2]";
    @FindBy(xpath = IN_LOCATION_XPATH)
    private WebElement inLocation;
    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/form/input[3]")
    private WebElement inSize;

    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/form/input[4]")
    private WebElement btnAddWarehouse;

    @FindBy(xpath = "//*[@id=\"root\"]/div[2]")
    private WebElement btnCloseForm;

    // Warehouse management

    @FindBy(xpath = "//*[@id=\"root\"]/table/tbody/tr/td[4]/button[1]")
    private WebElement btnManage;

    @FindBy(xpath = "//*[@id=\"root\"]/table/tbody/tr/td[4]/button[2]")
    private WebElement btnDelete;
    
    // Warehouse viewpoints

    private static final String TABLE_HEADER_XPATH = "//*[@id=\"root\"]/table/thead";
    @FindBy(xpath = TABLE_HEADER_XPATH)
    private WebElement elTableHeader;

    private static final String OBJECT_ROW_XPATH = "//*[@id=\"root\"]/table/tbody/tr";
    @FindBy(xpath = OBJECT_ROW_XPATH)
    private WebElement elObjectRow;

    private static final String OBJECT_NAME_XPATH = "//*[@id=\"root\"]/table/tbody/tr/td[1]";
    @FindBy(xpath = OBJECT_NAME_XPATH)
    private WebElement txtObjectName;

    // Form Feedback

    private static final String FEEDBACK_XPATH = "//*[@id=\"root\"]/div[2]/form/label[4]";
    @FindBy(xpath = FEEDBACK_XPATH)
    private WebElement txtFeedback;

    private static final String MSG_EMPTY_FIELDS = "Fields cannot be empty.";
    private static final String MSG_NEGATIVE_SIZE = "Size must be greater than 0.";
    private static final String MSG_ERROR = "Something went wrong, please try again.";

    // --- CONSTRUCTORS ---

    public WarehousesPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
        this.url = baseUrl + getUrlExtension();
    }

    @Override
    public void modifyObjectRight() {
        btnManage.click();
    }

    @Override
    public void modifyObjectWrong() {
        // No way to incorrectly modify warehouses in the frontend.
        throw new UnsupportedOperationException("Unimplemented method 'modifyObjectWrong'");
    }

    @Override
    public boolean verifyListExistence() {
        return elTableHeader.isDisplayed();
    }

    @Override
    public boolean verifyObjectExistence() {
        if (elObjectRow == null) {return false;}
        return elObjectRow.isDisplayed();
    }

    @Override
    public boolean verifyObjectUpdated() {
        return !(driver.getCurrentUrl().equals(url));
    }

    @Override
    public void performAction(String action) {
        switch (action) {
            case "New Warehouse":
                btnOpenForm.click();
                break;
        
            default:
                throw new IllegalArgumentException("Action '" + action + "' does not exist.");
        }
    }

    @Override
    public boolean isUserPerformingAction(String action) {
        switch(action){
            case "New Warehouse":
                return !driver.getCurrentUrl().equals(url);
            default:
                throw new IllegalArgumentException("Action '" + action + "' does not exist.");
        }
    }

    @Override
    public void enterWrongFormInformation() {
        clickBtnOpenForm();
        clearFormInformation();

        inName.sendKeys(Config.VALID_WAREHOUSE_NAME);
        inLocation.sendKeys(Config.VALID_WAREHOUSE_LOCATION);
        inSize.sendKeys(Config.VALID_WAREHOUSE_SIZE);
    }

    @Override
    public void enterRightFormInformation() {
        clickBtnOpenForm();
        clearFormInformation();

        inName.sendKeys(Config.VALID_WAREHOUSE_NAME);
        inLocation.sendKeys(Config.VALID_WAREHOUSE_LOCATION);
        inSize.sendKeys(Config.VALID_WAREHOUSE_SIZE);
    }

    @Override
    public boolean verifySubmissionFailure() {
        String feedback = txtFeedback.getText();

        return feedback.equals(MSG_EMPTY_FIELDS) || 
            feedback.equals(MSG_NEGATIVE_SIZE) ||
            feedback.equals(MSG_ERROR);
    }

    @Override
    public boolean verifySubmissionSuccess() {
        return txtObjectName.isDisplayed();
    }

    @Override
    public void navigateToPage() {
        logIn();
        navbar.clickBtnWarehouses();
        loadElements();
    }

    @Override
    public void clickButton(String btnName) {
        switch(btnName){
            case "btnAddWarehouse":
                clickBtnAddWarehouse();
                break;
            case "btnOpenForm":
                clickBtnOpenForm();
                break;
            case "btnCancel":
                clickBtnCancel();
                break;
            case "btnManageWarehouse":
                clickBtnManage();
                break;
            case "btnDeleteWarehouse":
                clickBtnDelete();
                break;
            default:
                navbar.clickButton(btnName);
                break;
        }
    }

    public void clickBtnAddWarehouse(){
        btnAddWarehouse.click();
    }

    public void clickBtnOpenForm(){
        try{
            inName.isDisplayed();
        }catch(NoSuchElementException e){
            btnOpenForm.click();
        }
    }

    public void clickBtnCancel(){
        btnCloseForm.click();
    }

    public void ensureWarehouseExists(){
        createWarehouse();
        loadElements();
    }

    public void clickBtnManage(){
        ensureWarehouseExists();
        btnManage.click();
    }

    public void clickBtnDelete(){
        ensureWarehouseExists();
        btnDelete.click();
    }

    @Override
    protected WebElement getDeleteButton() {
        return btnDelete;
    }

    @Override
    protected List<WebElement> getFormFields() {
        loadElements();
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

    /**
     * Ensures the warehouse is created.
     */
    public void createWarehouse(){
        loadElements();
        enterRightFormInformation();
        submitForm();
    }
}