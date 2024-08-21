package com.skillstorm.testingComponents.pages;

public interface Page {

    void navigateToPage();

    Object getURL();
    
    void logIn();
    void logOut();

    boolean checkLoggedIn();
    boolean checkLoggedOut();
}
