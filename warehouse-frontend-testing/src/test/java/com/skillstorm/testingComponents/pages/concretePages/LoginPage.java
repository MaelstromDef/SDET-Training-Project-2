package com.skillstorm.testingComponents.pages.concretePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.skillstorm.testingComponents.pages.FormPage;

public class LoginPage implements FormPage {
    private WebDriver driver;
    private String url;
    private String urlExtension = "/login";
    // INTERACTABLES

    @FindBy(xpath = "//*[@id=\"root\"]/form/input[1]")
    private WebElement inCompanyName;

    @FindBy(xpath = "//*[@id=\"root\"]/form/input[2]")
    private WebElement inPassword;

    @FindBy(xpath = "//*[@id=\"root\"]/form/input[3]")
    private WebElement btnLogIn;

    // FEEDBACK

    @FindBy(xpath = "//*[@id=\"root\"]/p")
    private WebElement txtFeedback;


    // CONSTRUCTORS
    public LoginPage(WebDriver driver, String initialPage) {
        this.driver = driver;
        this.url = initialPage + "/" + urlExtension;
    }

    // METHODS

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
}