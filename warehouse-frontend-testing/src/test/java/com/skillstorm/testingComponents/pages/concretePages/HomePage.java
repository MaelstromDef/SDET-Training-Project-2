package com.skillstorm.testingComponents.pages.concretePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.testingComponents.Navbar;
import com.skillstorm.testingComponents.pages.IPage;
import com.skillstorm.testingComponents.pages.abstractPages.Page;
import com.skillstorm.testingComponents.tools.PageTools;

public class HomePage extends Page {
    private String urlExtension = "/homepage";

    // --- INTERACTABLES ---

    @FindBy(xpath = "//*[@id=\"root\"]/button[1]")
    private WebElement btnAccount;

    @FindBy(xpath = "//*[@id=\"root\"]/button[2]")
    private WebElement btnWarehouses;

    //  --- CONSTRUCTORS ---

    public HomePage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    // --- METHODS ---

    /**
     * Interprets the button name to be clicked and calls the appropriate method.
     * 
     * @param btnName Name of the button to click. May be either "btnWarehouses" or "btnAccount"
     * @throws IllegalArgumentException Button does not exist.
     */
    @Override
    public void clickButton(String btnName) {
        switch(btnName){
            case "btnWarehouses":
                clickBtnWarehouses();
                break;
            case "btnAccount":
                clickBtnAccount();
                break;
            default:
                throw new IllegalArgumentException("Button '" + btnName + "' does not exist.");
        }
    }

    /**
     * Clicks the warehouses button.
     */
    public void clickBtnWarehouses(){
        btnWarehouses.click();
    }

    /**
     * Clicks the account button.
     */
    public void clickBtnAccount(){
        btnAccount.click();
    }

    @Override
    public void navigateToPage() {
        logOut();
        driver.get(url);
    }

    @Override
    protected String getUrlExtension() {
        return urlExtension;
    }   
}