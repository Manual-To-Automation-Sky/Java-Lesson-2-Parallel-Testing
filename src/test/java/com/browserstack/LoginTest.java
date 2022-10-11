package com.browserstack;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;

public class LoginTest {

    public WebDriver driver;
    
    public String url = "https://practicetestautomation.com/practice-test-login/";
    public String loggedInUrlSnippet = "logged-in-successfully";
    public String username = System.getenv("TEST_LOGIN");
    public String password = System.getenv("TEST_PASSWORD");
    public String wrongUsername = System.getenv("INCORRECT_TEST_LOGIN");
    public String wrongPassword = System.getenv("INCORRECT_TEST_PASSWORD");
    public String invalidUserMessage = "Your username is invalid!";
    public String invalidPasswordMessage = "Your password is invalid!";

    @BeforeMethod(alwaysRun = true)
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        driver = new RemoteWebDriver(
                new URL("https://hub.browserstack.com/wd/hub"), capabilities);
    }

    @Test
    public void loginSuccessfully() throws Exception {
        driver.get(url);

        WebElement usernameField = driver.findElement(By.cssSelector("#username"));
        usernameField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.cssSelector("#password"));
        passwordField.sendKeys(password);

        WebElement submitButton = driver.findElement(By.cssSelector("#submit"));
        submitButton.click();

        Assert.assertTrue(driver.getCurrentUrl().contains(loggedInUrlSnippet));

    }

    @Test
    public void wrongUsernameEntered() throws Exception {
        driver.get(url);

        WebElement usernameField = driver.findElement(By.cssSelector("#username"));
        usernameField.sendKeys(wrongUsername);

        WebElement passwordField = driver.findElement(By.cssSelector("#password"));
        passwordField.sendKeys(password);

        WebElement submitButton = driver.findElement(By.cssSelector("#submit"));
        submitButton.click();

        WebElement invalidUsernameElement = driver.findElement(By.cssSelector("#error"));
        Assert.assertTrue(invalidUsernameElement.isDisplayed());
        Assert.assertEquals(invalidUsernameElement.getText(), invalidUserMessage);
    }

    @Test
    public void wrongPasswordEntered() throws Exception {
        driver.get(url);

        WebElement usernameField = driver.findElement(By.cssSelector("#username"));
        usernameField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.cssSelector("#password"));
        passwordField.sendKeys(wrongPassword);

        WebElement submitButton = driver.findElement(By.cssSelector("#submit"));
        submitButton.click();

        WebElement invalidUsernameElement = driver.findElement(By.cssSelector("#error"));
        Assert.assertTrue(invalidUsernameElement.isDisplayed());
        Assert.assertEquals(invalidUsernameElement.getText(), invalidPasswordMessage);
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }
}
