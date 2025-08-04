package pagemodel;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class ForgotPasswordPage {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    public void clickForgotPassword() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(),'Forgot Password?')]")));
        js.executeScript("arguments[0].click();", link);
    }

    public void enterEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[formcontrolname='email']")));
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void clickSendOtp() {
        WebElement sendOtp = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(.,'Send OTP')]")));
        js.executeScript("arguments[0].click();", sendOtp);
    }

    public void enterNewPasswords(String newPass, String confirmPass) {
        WebElement newPassField = wait.until(ExpectedConditions.elementToBeClickable(By.id("mat-input-3")));
        WebElement confirmPassField = wait.until(ExpectedConditions.elementToBeClickable(By.id("mat-input-4")));
        newPassField.clear();
        newPassField.sendKeys(newPass);
        confirmPassField.clear();
        confirmPassField.sendKeys(confirmPass);
    }

    public void clearOtpFields() {
        for (int i = 0; i < 6; i++) {
            WebElement otpField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("input[id^='otp_" + i + "']")));
            otpField.clear();
        }
    }

    public void enterOtp(String otp) {
        for (int i = 0; i < otp.length(); i++) {
            WebElement otpField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("input[id^='otp_" + i + "']")));
            otpField.clear();
            otpField.sendKeys(Character.toString(otp.charAt(i)));
        }
    }

    public void clickSubmit() {
        WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class,'btn-auth-screens')]")));
        js.executeScript("arguments[0].click();", submitBtn);
    }
}
