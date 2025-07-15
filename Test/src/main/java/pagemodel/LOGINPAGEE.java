package pagemodel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LOGINPAGEE {
    WebDriver driver;

    By emailField = By.name("username");
    By passwordField = By.name("password");
    By loginBtn = By.xpath("//button[@type='submit']");

    public LOGINPAGEE(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginBtn).click();
    }
}
