package com.skillstorm.testingComponents.pages;

import java.util.NoSuchElementException;
import java.util.function.BooleanSupplier;

public interface Page {
    void navigateToPage() throws Exception;

    String getURL();
    
    void logIn() throws Exception;
    void logOut();

    boolean checkLoggedIn() throws NoSuchElementException;
    boolean checkLoggedOut();

    void clickButton(String btnName);
}
