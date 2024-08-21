package com.skillstorm;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import net.bytebuddy.asm.Advice.Enter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.testingComponents.pages.FormPage;
import com.skillstorm.testingComponents.pages.ObjectPage;
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
        try{
            pageObject.navigateToPage();
        } catch (Exception e ) {
            throw new RuntimeException("Navigation to Page " + page + "failed");
        } 
        assertEquals(driver.getCurrentUrl(), pageObject.getURL()); 
    }

    @Given("I Am Logged {string}") //In or Out
    public void iAmLogged(String inOrOut) {
        waitAMomentForWebDriver();
        if (inOrOut == "In") {
            try{
                pageObject.logIn();
            } catch (Exception e ) {
                throw new RuntimeException("log in failed");
            } 
            
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
    public void iClickButton(String btnName) {
        waitAMomentForWebDriver();
        pageObject.clickButton(btnName);
    }

    @Then("And I Will Be Performing {string}" ) // action
    public void iWillBePerforming(String action) {
        waitAMomentForWebDriver();
        assertTrue(pageObject.isUserPerformingAction(action));
    }

    /**
     * *********************************************************************
     *  CRUD STEP DEFINITIONS
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

    @Then("I {string} See {string} Information")   
    public void iSeeInformation(String canSee, String type) {
        /** 
         * @param canSee: whethere we want to see if items/warehouses show up on page
         * @param type: can be either "Warehouse" or "Items" 
         * 
         * but since both are part of object page, string doesn't matter, so just do a quick check
         */
        
        assertTrue(type.equals("Warehouse") || type.equals("Item") || type.equals("Account"));
        waitAMomentForWebDriver();

        ObjectPage objectPage = (ObjectPage) pageObject;
        if (canSee == "Can") {
            assertTrue(objectPage.verifyObjectExistence());
        } else if (canSee == "Can Not") {
            assertFalse(objectPage.verifyObjectExistence());
        }
        
    }

    @Given("I {string} Update {string}") 
    public void iUpdateItem(String correctly, String type) {
        assertTrue(type.equals("Warehouse") || type.equals("Item") || type.equals("Account"));
        waitAMomentForWebDriver();

        ObjectPage objectPage = (ObjectPage) pageObject;
        if (correctly == "Correctly")
            objectPage.modifyObjectRight();
        else if (correctly == "Incorrectly")
            objectPage.modifyObjectWrong();
        else
            throw new IllegalArgumentException("Expected 'Correctly' or 'Incorrectly', but received: " + correctly);
    }

    @Then("{string} Fields Have {string} Changed")
    public void fieldsHaveChanged (String type, String been) {
        /**
         * @param type: can be either "Warehouse" or "Items" 
         * 
         * but since both are part of object page, string doesn't matter, so just do a quick check
         */
        
         assertTrue(type.equals("Warehouse") || type.equals("Item") || type.equals("Account"));
        waitAMomentForWebDriver();

        ObjectPage objectPage = (ObjectPage) pageObject;
        if (been == "Been" ) {
            assertTrue(objectPage.verifyObjectUpdated());
        } else if (been == "Not Been") {
            assertFalse(objectPage.verifyObjectUpdated());
        } else {
            throw new IllegalArgumentException("Expected 'Been' or 'Not Been', but received: " + been);
        }
    }

    @Then("{string} No Longer Exists")
    public void noLongerExists(String type) {
        assertTrue(type == "Item" || type == "Warehouse");
        waitAMomentForWebDriver();

        ObjectPage objectPage = (ObjectPage) pageObject;
        assertFalse(objectPage.verifyObjectExistence());
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
