package pagemodel;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-input-5")));
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-input-6")));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btnLogin"))).click();
    }

    public void enterOtp(String otp) throws InterruptedException {
        for (int i = 0; i < otp.length(); i++) {
            WebElement otpField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[id^='otp_" + i + "']")));
            otpField.clear();
            otpField.sendKeys(String.valueOf(otp.charAt(i)));
        }
        Thread.sleep(1500);
    }
}
