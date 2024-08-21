package com.skillstorm;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.testingComponents.pages.Page;
import com.skillstorm.testingComponents.pages.concretePages.AccountPage;
import com.skillstorm.testingComponents.pages.concretePages.HomePage;
import com.skillstorm.testingComponents.pages.concretePages.ItemsPage;
import com.skillstorm.testingComponents.pages.concretePages.LandingPage;
import com.skillstorm.testingComponents.pages.concretePages.LoginPage;
import com.skillstorm.testingComponents.pages.concretePages.SignupPage;
import com.skillstorm.testingComponents.pages.concretePages.WarehousesPage;

public class StepDefinitions {
    
    private WebDriver driver;
    private Page pageObject;
    private String initialURL = "http://ahuggins-warehousemanager-frontend.s3-website.us-east-2.amazonaws.com/";

    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(initialURL);
        assertEquals(driver.getCurrentUrl(), initialURL);
    }


    @Given("I Am On {string}") //page name
    public void iAmOn(String page) {
       switch (page) {
        case "Home":
            pageObject = new HomePage(driver, initialURL);
            break;
        case "Account":
            pageObject = new AccountPage(driver, initialURL);
            break;
        case "Warehouses":
            pageObject = new WarehousesPage(driver, initialURL); 
            break;
        case "Item":
            pageObject = new ItemsPage(driver, initialURL); 
            break;
        case "Landing":
            pageObject = new LandingPage(driver, initialURL); 
            break;
        case "Signup":
            pageObject = new SignupPage(driver, initialURL); 
            break;
        case "Login":
            pageObject = new LoginPage(driver, initialURL);
            break;
        default:
            throw new IllegalArgumentException("Incorrect Page Name");
       }
       assertTrue(pageObject != null);
       pageObject.navigateToPage();
       assertEquals(driver.getCurrentUrl(), pageObject.getURL()); 
    }


    /**
     * *********************************************************************
     *  DIRECT NAVIGATION STEP DEFINITIONS
     * *********************************************************************
     */

    @Given("I Am Logged {string}") //In or Out
    public void iAmLogged(String inOrOut) {
        //TODO: Find Elements in Naviation Bar 
        //WAITING FOR NaVigateBar CLASS TO BE MADE

        //TODO: Assert that Elements Match Logged In/Out Status
    }

    @When("I Attempt To Navigate To {string}") //page name
    public void iAttemptToNavigateTo (String page) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.get(initialURL + "/" + page.toLowerCase());
    }

    @Then("I Am Taken To {string}") //page name
    public void iAmTakenTo(String page) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(driver.getCurrentUrl(), initialURL + "/" + page.toLowerCase());;
    } 

    
    /**
     * *********************************************************************
     *  USABILITY STEP DEFINITIONS
     * *********************************************************************
     */

    // @Given("I Am Logged {string}") is in "DIRECT NAVIGATION" Step Definitions

    @When("I Click {string} Button")
    public void iClickButton(String buttonName) {
        pageObject.clickButton(buttonName);
    }

    // @Then("I Am Taken To {string}") is in "DIRECT NAVIGATION" Step Definitions

    @Given("I am Performing {string}") //action
    public void iAmPerforming(String action) {
        pageObject.performAction(action);
    }




     



}
