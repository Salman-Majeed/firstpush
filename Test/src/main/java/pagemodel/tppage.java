package pagemodel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class tppage {

    WebDriver driver;

    // Constructor
    public tppage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    By forgotPasswordLink = By.xpath("//span[normalize-space()='Forgot Password?']");
    By emailInput = By.id("mat-input-36");
    By sendOTPButton = By.xpath("//button[contains(text(),'Send OTP')]");

    By emailLoginField = By.id("mat-input-30");  // Update if changed
    By passwordField = By.id("mat-input-31");    // Update if changed
    By loginButton = By.xpath("//button[contains(text(),'Login')]");

    // Actions
    public void clickForgotPassword() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(forgotPasswordLink).click();
    }

    public void enterEmailForReset(String email) throws InterruptedException {
        Thread.sleep(1000);
        WebElement emailField = driver.findElement(emailInput);
        emailField.click();
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void clickSendOTP() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(sendOTPButton).click();
    }

    public void login(String email, String password) throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(emailLoginField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        Thread.sleep(1000);
        driver.findElement(loginButton).click();
    }
}
