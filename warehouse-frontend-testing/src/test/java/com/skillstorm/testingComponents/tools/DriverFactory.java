package com.skillstorm.testingComponents.tools;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    /**
     * Generates a web driver. Only able to generate a ChromeDriver
     * @return A Chrome web driver.
     */
    public static WebDriver getDriver(){
        WebDriver driver;
        ChromeOptions options = new ChromeOptions();

        // Get system information
        String sysHeadless = System.getProperty("headless", "false");
        Boolean headless = Boolean.parseBoolean(sysHeadless);

        // Handle headless mode.
        if(headless){
            
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--remote-debugging-pipe");
        }
        
        // Create and return driver.
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(100));
        return driver;
    }
}
