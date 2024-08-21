package com.skillstorm.testingComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Navbar {
    // --- FIELDS ---
    private WebDriver driver;

    // LOGGED OUT

    private final String BTN_LANDING_XPATH  = "//*[@id=\"root\"]/div/a[1]";
    private WebElement btnLanding;

    private final String BTN_LOGIN_XPATH = "//*[@id=\"root\"]/div/a[1]";
    private WebElement btnLogin;

    private final String BTN_SIGNUP_XPATH = "//*[@id=\"root\"]/div/a[1]";
    private WebElement btnSignup;

    // LOGGED IN

    private final String BTN_HOME_XPATH = "//*[@id=\"root\"]/div/a[1]";
    private WebElement btnHome;

    private final String BTN_ACCOUNT_XPATH = "//*[@id=\"root\"]/div/a[1]";
    private WebElement btnAccount;

    private final String BTN_WAREHOUSES_XPATH = "//*[@id=\"root\"]/div/a[1]";
    private WebElement btnWarehouses;

    private final String BTN_LOGOUT_XPATH = "//*[@id=\"root\"]/div/a[1]";
    private WebElement btnLogOut;

    // --- CONSTRUCTORS ---

    public Navbar(WebDriver driver){
        this.driver = driver;
    }

    // --- METHODS ---

    // LOAD BUTTONS

    /**
     * Loads the buttons that should exist while logged out.
     * 
     * @throws NoSuchElementException If a button is not found.
     */
    public void loadLoggedOutButtons(){
        btnLanding = driver.findElement(By.xpath(BTN_LANDING_XPATH));
        btnLogin = driver.findElement(By.xpath(BTN_LOGIN_XPATH));
        btnSignup = driver.findElement(By.xpath(BTN_SIGNUP_XPATH));
    }

    /**
     * Loads the buttons that should exist while logged in.
     * 
     * @throws NoSuchElementException If a button is not found.
     */
    public void loadLoggedInButtons(){
        btnHome = driver.findElement(By.xpath(BTN_HOME_XPATH));
        btnAccount = driver.findElement(By.xpath(BTN_ACCOUNT_XPATH));
        btnWarehouses = driver.findElement(By.xpath(BTN_WAREHOUSES_XPATH));
        btnLogOut = driver.findElement(By.xpath(BTN_LOGOUT_XPATH));
    }

    // CLICK BUTTONS

    /**
     * Clicks the landing button.
     * 
     * @throws NoSuchElementException If any of the logged out buttons are not found on page.
     */
    public void clickBtnLanding(){
        loadLoggedOutButtons();
        btnLanding.click();
    }

    /**
     * Clicks the login button.
     * 
     * @throws NoSuchElementException If any of the logged out buttons are not found on page.
     */
    public void clickBtnLogin(){
        loadLoggedOutButtons();
        btnLogin.click();
    }

    /**
     * Clicks the signup button.
     * 
     * @throws NoSuchElementException If any of the logged out buttons are not found on page.
     */
    public void clickBtnSignup(){
        loadLoggedOutButtons();
        btnSignup.click();
    }

    /**
     * Clicks the home button.
     * 
     * @throws NoSuchElementException If any of the logged in buttons are not found on page.
     */
    public void clickBtnHome(){
        loadLoggedInButtons();
        btnHome.click();
    }

    /**
     * Clicks the account button.
     * 
     * @throws NoSuchElementException If any of the logged in buttons are not found on page.
     */
    public void clickBtnAccount(){
        loadLoggedInButtons();
        btnAccount.click();
    }

    /**
     * Clicks the warehouse button.
     * 
     * @throws NoSuchElementException If any of the logged in buttons are not found on page.
     */
    public void clickBtnWarehouses(){
        loadLoggedInButtons();
        btnWarehouses.click();
    }

    /**
     * Clicks the log out button.
     * 
     * @throws NoSuchElementException If any of the logged in buttons are not found on page.
     */
    public void clickBtnLogOut(){
        loadLoggedInButtons();
        btnLogOut.click();
    }
}