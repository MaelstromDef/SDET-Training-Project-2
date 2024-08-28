package com.skillstorm.testingComponents.pages;

import java.util.NoSuchElementException;
import java.util.function.BooleanSupplier;

public interface IPage {
    void navigateToPage();
    void loadElements();

    String getUrl();
    void awaitValidUrl();
    
    void logIn();
    void logOut();

    boolean checkLoggedIn();
    boolean checkLoggedOut();

    void clickButton(String btnName);
}
