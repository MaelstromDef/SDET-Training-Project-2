package com.skillstorm;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import net.bytebuddy.asm.Advice.Enter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.testingComponents.pages.FormPage;
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
    public static String initialURL = "http://ahuggins-warehousemanager-frontend.s3-website.us-east-2.amazonaws.com/";

    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(initialURL);
        assertEquals(driver.getCurrentUrl(), initialURL);
    }

    /**
     * *********************************************************************
     *  DIRECT NAVIGATION STEP DEFINITIONS
     * *********************************************************************
     */


    @Given("I Am On {string}") //page name
    public void iAmOn(String page) {
        waitAMomentForWebDriver();
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

    @Given("I Am Logged {string}") //In or Out
    public void iAmLogged(String inOrOut) {
        waitAMomentForWebDriver();
        if (inOrOut == "In") {
            pageObject.logIn();
            assertTrue(pageObject.checkLoggedIn());
        } else if (inOrOut == "Out") {
            pageObject.logOut();
            assertTrue(pageObject.checkLoggedOut());
        } else {
            throw new InvalidArgumentException("Expected 'In' or 'Out', instead received: " + inOrOut);
        }
    }

    @When("I Attempt To Navigate To {string}") //page name
    public void iAttemptToNavigateTo (String page) {
        waitAMomentForWebDriver();
        driver.get(initialURL + "/" + page.toLowerCase());
    }

    @Then("I Am Taken To {string}") //page name
    public void iAmTakenTo(String page) {
        waitAMomentForWebDriver();
        assertEquals(driver.getCurrentUrl(), initialURL + "/" + page.toLowerCase());;
    } 

    
    /**
     * *********************************************************************
     *  USABILITY STEP DEFINITIONS
     * *********************************************************************
     */

    // @Given("I Am On {page}") is in "DIRECT NAVIGATION" Step Definitions
    // @Then("I Am Taken To {page}") is in "DIRECT NAVIGATION" Step Definitions

    @Given("I Am Performing {string}") //action
    public void iAmPerforming(String action) {
        waitAMomentForWebDriver();
        pageObject.performAction(action);
    }

    @When("I Click {string} Button")
    public void iClickButton(String buttonName) {
        waitAMomentForWebDriver();
        pageObject.clickButton(buttonName);
    }

    @Then("And I Will Be Performing {string}" ) // action
    public void iWillBePerforming(String action) {
        waitAMomentForWebDriver();
        assertTrue(pageObject.isUserPerformingAction(action));
    }

    /**
     * *********************************************************************
     *  INVENTORY CRUD STEP DEFINITIONS
     * *********************************************************************
     */

    // @Given("I Am On {page}") is in "DIRECT NAVIGATION" Step Definitions
    // @When("I Click {btnName} Button"} is in "USABILITY" Step Definitions

    @Given("I Enter Correct Information")
    public void iEnterCorrectInformation() {
        waitAMomentForWebDriver();
        
        FormPage formPage = (FormPage) pageObject;
        formPage.enterRightFormInformation();
    }

    @Given("I Enter Incorrect Information") 
    public void iEnterIncorrectInformation() {
        waitAMomentForWebDriver();

        FormPage formPage = (FormPage) pageObject;
        formPage.enterWrongFormInformation();
    }

    @When("I Submit the Form")
    public void iSubmitTheForm() {
        waitAMomentForWebDriver();

        FormPage formPage = (FormPage) pageObject;
        formPage.submitForm();
    }

    @Then("Item Is Added To Warehouse")
    public void itemIsAddedToWarehouse() {
        waitAMomentForWebDriver();

        FormPage formPage = (FormPage) pageObject;
        assertTrue(formPage.verifySubmissionSuccess());
    }

    @Then("I See An Error Message")
    public void iSeeAnErrorMessage() {
        waitAMomentForWebDriver();

        FormPage formPage = (FormPage) pageObject;
        assertTrue(formPage.verifySubmissionFailure());
    }

    @Then("I Can See {string} Information")  
    public void iCanSeeInformation(String type) {
        /** 
         * @param type: options
         *      Specific Items
         *      Item
         *      Warehouse
         */
    }



    ////////////////////////////////////////////////// 
    // EXTRA FUNCTIONS TO REDUCE CODE REDUDANCY
    //////////////////////////////////////////////////
    void waitAMomentForWebDriver() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    

}
