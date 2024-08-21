package com.skillstorm.testingComponents.pages;

public interface Page {
    void navigateToPage() throws Exception;

    String getURL();
    
    void logIn() throws Exception;
    void logOut();

    boolean checkLoggedIn();
    boolean checkLoggedOut();
}
