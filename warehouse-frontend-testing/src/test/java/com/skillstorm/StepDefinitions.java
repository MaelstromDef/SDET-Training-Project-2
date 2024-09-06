package com.skillstorm;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.skillstorm.testingComponents.pages.IFormPage;
import com.skillstorm.testingComponents.pages.IObjectPage;
import com.skillstorm.testingComponents.pages.IPage;
import com.skillstorm.testingComponents.pages.concretePages.AccountPage;
import com.skillstorm.testingComponents.pages.concretePages.HomePage;
import com.skillstorm.testingComponents.pages.concretePages.ItemsPage;
import com.skillstorm.testingComponents.pages.concretePages.LandingPage;
import com.skillstorm.testingComponents.pages.concretePages.LoginPage;
import com.skillstorm.testingComponents.pages.concretePages.SignupPage;
import com.skillstorm.testingComponents.pages.concretePages.WarehousesPage;
import com.skillstorm.testingComponents.tools.DriverFactory;

public class StepDefinitions {
    
    private static WebDriver driver;
    private IPage pageObject;
    private static Wait<WebDriver> wait;
    public static String initialURL = "http://ahuggins-warehousemanager-frontend.s3-website.us-east-2.amazonaws.com/";

    @BeforeAll
    public static void setup() {  
        //Setup Chrome Driver
        //Driver Factory will produce headless driver if command argument -Dheadless=true is given        
        driver = DriverFactory.getDriver();
        //This will be used for allowing Thread to wait the least amount of time before next action is taken
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //Navigate to Landing URL
        driver.get(initialURL);

        waitForPageToLoad(initialURL, true);        
        assertEquals(driver.getCurrentUrl(), initialURL);
    }

    @Before
    public void loadStartPage(){
        //Before each Scenerio we should reset all navigation that has happened to make tracing problems easier
        driver.get(initialURL);
        waitForPageToLoad(initialURL, true);
    }

    @After
    public void cleanUp(){
        //After each Scenerio we should clean up the page object to make tracing problems easier
        pageObject = null;
    }

    @AfterAll
    public static void tearDown() {
        // Remove Testing account before stopping all processes
        try {
            AccountPage page = new AccountPage(driver, initialURL);
            page.navigateToPage();
            waitForPageToLoad(initialURL+"account", true);
            page.deleteObject();

            if(page.verifyObjectExistence()) throw new RuntimeException("Account was not deleted.");
        } catch (Exception e) {
            throw e;
        }
        

        System.out.println("Closing All Web Browsers");
        driver.quit();
    }

    /**
     * *********************************************************************
     *  WHEN STEP DEFINITIONS
     * *********************************************************************
     */

    /**
     * Test to navigate to a certain page
     * @param page
     */
    @Given("I Am On {string}") //page name
    public void iAmOn(String page) {
        loadPage(page);
        assertTrue(pageObject != null);
        
        pageObject.navigateToPage();
        //wait for page to change from initial page unless pageObject is LandingPage
        waitForPageToLoad(initialURL, pageObject.getClass()==LandingPage.class);
        assertEquals(pageObject.getUrl(), driver.getCurrentUrl()); 
    }

    /**
     * Test to check if Logged in or Out
     * @param inOrOut
     */
    @Given("I Am Logged {string}") //In or Out
    public void iAmLogged(String inOrOut) {
        
        if(pageObject == null){
            loadPage("Login");
        }
        
        if (inOrOut.equals("In")) {
            pageObject.logIn();
            
            assertTrue(pageObject.checkLoggedIn());
        } else if (inOrOut.equals("Out")) {
            pageObject.logOut();
            assertTrue(pageObject.checkLoggedOut());
        } else {
            throw new InvalidArgumentException("Expected 'In' or 'Out', instead received: " + inOrOut);
        }
    }

    /**
     * Test to make sure "User" is performing a given action
     * @param action - String repsresenting what action should be happening
     */
    @Given("I Am Performing {string}") //action
    public void iAmPerforming(String action) {
        ((IObjectPage)pageObject).performAction(action);
    }

    /**
     * Test to perform action of filling out a form depending on the page
     * @param correct - denotes wether we are filling out a form the right or wrong way
     */
    @Given("I Enter {string} Information") //correct or incorrect
    public void iEnterCorrectInformation(String correct) {
        
        IFormPage formPage = (IFormPage) pageObject;
        if (correct.equals("Correct")) {
            formPage.enterRightFormInformation();
        } else if (correct.equals("Incorrect")) {
            formPage.enterWrongFormInformation();
        } else {
            throw new IllegalArgumentException("Expected 'Correct' or 'Incorrect', but received: " + correct);
        }   
    }

    /**
     * Test to setup up taking the correct/incorrect actions to update and object
     * @param correctly - String
     * @param type - String representing object that should be updated
     *      options:
     *          Warehoue, Item, or Account
     */
    @Given("I {string} Update {string}") 
    public void iUpdateItem(String correctly, String type) {
        assertTrue(type.equals("Warehouse") || type.equals("Item") || type.equals("Account"));

        IObjectPage objectPage = (IObjectPage) pageObject;
        if (correctly.equals("Correctly"))
            objectPage.modifyObjectRight();
        else if (correctly.equals("Incorrectly"))
            objectPage.modifyObjectWrong();
        else
            throw new IllegalArgumentException("Expected 'Correctly' or 'Incorrectly', but received: " + correctly);
    }




    
    /**
     * *********************************************************************
     *  WHEN STEP DEFINITIONS
     * *********************************************************************
     */

    /**
     * This is using the url to try and navigate to different places
    */
    @When("I Attempt To Directly Navigate To {string}") //page name
    public void iAttemptToDirectlyNavigateTo (String page) {
        loadPage(page);
        driver.get(initialURL + page.toLowerCase());
    }

    /*
     * This is using the page objects such as buttons to navigate to other pages
     */
    @When("I Attempt To Navigate To {string}") //page name
    public void iAttemptToNavigateTo (String page) {
        loadPage(page);
        pageObject.navigateToPage();
        //wait for page to change from initial page unless pageObject is LandingPage
        waitForPageToLoad(initialURL, pageObject.getClass()==LandingPage.class);
    }

    /*
     * Action for clicking a button
     */
    @When("I Click {string}")
    public void iClick(String btnName) {
        pageObject.clickButton(btnName);
    }

    /*
     * Action for submitting a form after correctly/incorrectly filling it out
     */
    @When("I Submit The Form")
    public void iSubmitTheForm() {
        IFormPage formPage = (IFormPage) pageObject;
        formPage.submitForm();
    }

    
    /**
     * *********************************************************************
     *  THEN STEP DEFINITIONS
     * *********************************************************************
     */

    /**
     * This test that after certain actions are taken by the user, the POM is in a given state
     * @param action - String representing state of POM that represents user taking and action
     */
    @Then("I Will Be Performing {string}" ) // action
    public void iWillBePerforming(String action) {
        assertTrue(((IObjectPage)pageObject).isUserPerformingAction(action));
    }

    /**
     * Tes that assures user is taken to a specific page
     * @param page - String representing name of page
     */
    @Then("I Am Taken To {string}") //page name
    public void iAmTakenTo(String page) {
        if(page.toLowerCase().equals("landing")) assertEquals(initialURL, driver.getCurrentUrl());
        else if(page.toLowerCase().equals("item")) assertEquals(initialURL + "items", driver.getCurrentUrl());
        else assertEquals(initialURL + page.toLowerCase(), driver.getCurrentUrl());
    } 


    /**
     * Test to see if a new object was created on the POM
     * @param type - String representing object that should have been created
     *      options:
     *          Warehoue, Item, or Account
     */
    @Then("A New {string} Is Created")
    public void aNewIsCreated(String type) {

        assertTrue(type.equals("Warehouse") || type.equals("Item") || type.equals("Account"));
        IFormPage formPage = (IFormPage) pageObject;
        assertTrue(formPage.verifySubmissionSuccess());
    }

    /**
     * Test to make sure that a form gives and error message after user doesn't fill out form correctly
     */
    @Then("I See An Error Message")
    public void iSeeAnErrorMessage() {
        IFormPage formPage = (IFormPage) pageObject;
        assertTrue(formPage.verifySubmissionFailure());
    }

    
    /**
     * Test to assure that new information show up on the DOM
     * @param canSee - String representing boolean of where data should appear on DOM or not
     * @param type - String representing object that should be seen
     *      options:
     *          Warehoue, Item, or Account
     */
    @Then("I {string} See {string} Information")   
    public void iSeeInformation(String canSee, String type) {
        
        assertTrue(type.equals("Warehouse") || type.equals("Item") || type.equals("Account"));

        IObjectPage objectPage = (IObjectPage) pageObject;
        objectPage.loadElements();
        waitAMomentForWebDriver();

        if (canSee.equals("Can")) {
            assertTrue(objectPage.verifyObjectExistence());
        } else if (canSee.equals("Can Not")) {
            assertFalse(objectPage.verifyObjectExistence());
        } else {
            throw new IllegalArgumentException("Expected 'Can' or 'Can Not', but received: " + canSee);
        }
        
    }

    /**
     * Test to assure that the DOM had updated information
     * @param been - String representing boolean of whether DOM should have changed or not
     * @param type - String representing object that should be changed or not
     *      options:
     *          Warehoue, Item, or Account
     */
    @Then("{string} Fields Have {string} Changed")
    public void fieldsHaveChanged (String type, String been) {
        /**
         * @param type: can be either "Warehouse" or "Items" or "Account" 
         * 
         * but since both are part of object page, string doesn't matter, so just do a quick check
         */
        
        assertTrue(type.equals("Warehouse") || type.equals("Item") || type.equals("Account"));

        IObjectPage objectPage = (IObjectPage) pageObject;
        if (been.equals("Been") ) {
            assertTrue(objectPage.verifyObjectUpdated());
        } else if (been.equals("Not Been")) {
            assertFalse(objectPage.verifyObjectUpdated());
        } else {
            throw new IllegalArgumentException("Expected 'Been' or 'Not Been', but received: " + been);
        }
    }

    /**
     * Test to make sure object has been deleted from database (represented in DOM)
     * @param type - String representing object that should be changed or not
     *      options:
     *          Warehoue, Item, or Account
     */
    @Then("{string} No Longer Exists")
    public void noLongerExists(String type) {

        assertTrue(type.equals("Warehouse") || type.equals("Item") || type.equals("Account"));

        IObjectPage objectPage = (IObjectPage) pageObject;
        assertFalse(objectPage.verifyObjectExistence());
    }

    /**
     * Test to make sure a login was sucessful or not
     * @param am - String representing boolean of whether logged in or not
     */
    @Then("I {string} Logged In")
    public void iLoggedIn(String am) {
        
        if (am.equals("Am")) {          
            assertTrue(pageObject.checkLoggedIn());
        } else if (am.equals("Am Not")) {
            assertTrue(pageObject.checkLoggedOut());
        } else {
            throw new InvalidArgumentException("Expected 'In' or 'Out', instead received: " + am);
        }
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

    static void waitForPageToLoad(String url, boolean equals) {
        wait.until(d -> {return driver.getCurrentUrl().equals(url) == equals;});
    }

    void loadPage(String page) {
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
    }
}
