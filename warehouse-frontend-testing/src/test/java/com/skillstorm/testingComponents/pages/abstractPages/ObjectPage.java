package com.skillstorm.testingComponents.pages.abstractPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.skillstorm.testingComponents.pages.IObjectPage;

public abstract class ObjectPage extends FormPage implements IObjectPage {

    public ObjectPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    /**
     * Deletes the object from the page.
     */
    @Override
    public void deleteObject(){
        getDeleteButton().click();
    }
    
    /**
     * Retrieves the button to delete the page object with.
     * @return Delete button.
     */
    protected abstract WebElement getDeleteButton();
}
