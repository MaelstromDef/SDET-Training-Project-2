package com.skillstorm.testingComponents.pages.abstractPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.skillstorm.testingComponents.pages.IFormPage;

public abstract class FormPage extends Page implements IFormPage {
    public FormPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }
    
    /**
     * Clears the information from all form fields.
     */
    @Override
    public void clearFormInformation(){
        loadElements();
        List<WebElement> fields = getFormFields();

        for(int i = 0; i < fields.size(); i++){
            fields.get(i).clear();
        }
    }

    /**
     * Submits the form.
     */
    @Override
    public boolean submitForm(){
        getSubmitButton().click();
        return true;
    }

    /**
     * Retrieves all fields present in the page's form.
     * @return Form fields.
     */
    protected abstract List<WebElement> getFormFields();

    /**
     * Retrieves the submit button for a page's form.
     * @return Submit button.
     */
    protected abstract WebElement getSubmitButton();
}
