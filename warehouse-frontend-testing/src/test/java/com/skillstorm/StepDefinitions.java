package com.skillstorm;

import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;
import net.bytebuddy.asm.Advice.Enter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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

public class StepDefinitions {
    
    private static WebDriver driver;
    private IPage pageObject;
    public static String initialURL = "http://ahuggins-warehousemanager-frontend.s3-website.us-east-2.amazonaws.com/";

    @BeforeAll
    public static void setup() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(initialURL);
        assertEquals(driver.getCurrentUrl(), initialURL);
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("Closing All Web Browsers");
        driver.quit();
    }

    /**
     * *********************************************************************
     *  WHEN STEP DEFINITIONS
     * *********************************************************************
     */


    @Given("I Am On {string}") //page name
    public void iAmOn(String page) {
        waitAMomentForWebDriver();
        loadPage(page);
        assertTrue(pageObject != null);
        
        try{
            pageObject.navigateToPage();
        } catch (Exception e ) {
            throw new RuntimeException(e.getMessage());
        } 
        assertEquals(driver.getCurrentUrl(), pageObject.getUrl()); 
    }

    @Given("I Am Logged {string}") //In or Out
    public void iAmLogged(String inOrOut) {
        waitAMomentForWebDriver();
        if (pageObject == null) {
            loadPage("Login");
        }
        
        if (inOrOut.equals("In")) {
            try{
                pageObject.logIn();
            } catch (Exception e ) {
                throw new RuntimeException(e.getMessage());
            } 
            
            assertTrue(pageObject.checkLoggedIn());
        } else if (inOrOut.equals("Out")) {
            pageObject.logOut();
            assertTrue(pageObject.checkLoggedOut());
        } else {
            throw new InvalidArgumentException("Expected 'In' or 'Out', instead received: " + inOrOut);
        }
    }

    @Given("I Am Performing {string}") //action
    public void iAmPerforming(String action) {
        waitAMomentForWebDriver();
        ((IObjectPage)pageObject).performAction(action);
    }

    @Given("I Enter {string} Information") //correct or incorrect
    public void iEnterCorrectInformation(String correct) {
        waitAMomentForWebDriver();
        
        IFormPage formPage = (IFormPage) pageObject;
        if (correct.equals("Correct")) {
            formPage.enterRightFormInformation();
        } else if (correct.equals("Incorrect")) {
            formPage.enterWrongFormInformation();
        } else {
            throw new IllegalArgumentException("Expected 'Correct' or 'Incorrect', but received: " + correct);
        }   
    }

    @Given("I {string} Update {string}") 
    public void iUpdateItem(String correctly, String type) {
        assertTrue(type.equals("Warehouse") || type.equals("Item") || type.equals("Account"));
        waitAMomentForWebDriver();

        IObjectPage objectPage = (IObjectPage) pageObject;
        if (correctly.equals("Correct"))
            objectPage.modifyObjectRight();
        else if (correctly.equals("Incorrect"))
            objectPage.modifyObjectWrong();
        else
            throw new IllegalArgumentException("Expected 'Correctly' or 'Incorrectly', but received: " + correctly);
    }




    
    /**
     * *********************************************************************
     *  WHEN STEP DEFINITIONS
     * *********************************************************************
     */

    @When("I Attempt To Navigate To {string}") //page name
    public void iAttemptToNavigateTo (String page) {
        loadPage(page);
        waitAMomentForWebDriver();
        driver.get(initialURL + page.toLowerCase());
    }

    @When("I Click {string} Button")
    public void iClickButton(String btnName) {
        waitAMomentForWebDriver();
        pageObject.clickButton(btnName);
    }

    @When("I Submit The Form")
    public void iSubmitTheForm() {
        waitAMomentForWebDriver();

        IFormPage formPage = (IFormPage) pageObject;
        formPage.submitForm();
    }

    
    /**
     * *********************************************************************
     *  THEN STEP DEFINITIONS
     * *********************************************************************
     */

    @Then("And I Will Be Performing {string}" ) // action
    public void iWillBePerforming(String action) {
        waitAMomentForWebDriver();
        assertTrue(((IObjectPage)pageObject).isUserPerformingAction(action));
    }

    @Then("I Am Taken To {string}") //page name
    public void iAmTakenTo(String page) {
        waitAMomentForWebDriver();
        if(page.toLowerCase().equals("landing")) assertEquals(initialURL, driver.getCurrentUrl());
        else assertEquals(initialURL + page.toLowerCase(), driver.getCurrentUrl());
    } 

    @Then("A New {string} Is Created")
    public void aNewIsCreated(String type) {
        /**
         * @param type: can be either "Warehouse" or "Items" or "Account" 
         * 
         * but since both are part of object page, string doesn't matter, so just do a quick check
         */
        assertTrue(type.equals("Warehouse") || type.equals("Item") || type.equals("Account"));
        waitAMomentForWebDriver();

        IFormPage formPage = (IFormPage) pageObject;
        assertTrue(formPage.verifySubmissionSuccess());
    }

    @Then("I See An Error Message")
    public void iSeeAnErrorMessage() {
        waitAMomentForWebDriver();

        IFormPage formPage = (IFormPage) pageObject;
        assertTrue(formPage.verifySubmissionFailure());
    }

    @Then("I {string} See {string} Information")   
    public void iSeeInformation(String canSee, String type) {
        /**
         * @param type: can be either "Warehouse" or "Items" or "Account" 
         * 
         * but since both are part of object page, string doesn't matter, so just do a quick check
         */
        
        assertTrue(type.equals("Warehouse") || type.equals("Item") || type.equals("Account"));
        waitAMomentForWebDriver();

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

    @Then("{string} Fields Have {string} Changed")
    public void fieldsHaveChanged (String type, String been) {
        /**
         * @param type: can be either "Warehouse" or "Items" or "Account" 
         * 
         * but since both are part of object page, string doesn't matter, so just do a quick check
         */
        
        assertTrue(type.equals("Warehouse") || type.equals("Item") || type.equals("Account"));
        waitAMomentForWebDriver();

        IObjectPage objectPage = (IObjectPage) pageObject;
        if (been.equals("Been") ) {
            assertTrue(objectPage.verifyObjectUpdated());
        } else if (been.equals("Not Been")) {
            assertFalse(objectPage.verifyObjectUpdated());
        } else {
            throw new IllegalArgumentException("Expected 'Been' or 'Not Been', but received: " + been);
        }
    }

    @Then("{string} No Longer Exists")
    public void noLongerExists(String type) {
        /**
         * @param type: can be either "Warehouse" or "Items" or "Account" 
         * 
         * but since both are part of object page, string doesn't matter, so just do a quick check
         */

        assertTrue(type.equals("Warehouse") || type.equals("Item") || type.equals("Account"));
        waitAMomentForWebDriver();

        IObjectPage objectPage = (IObjectPage) pageObject;
        assertFalse(objectPage.verifyObjectExistence());
    }

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
