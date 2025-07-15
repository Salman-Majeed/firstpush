package pagemodel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class loginpage {
    WebDriver driver;

    // Correct locators based on HTML
    By emailField = By.name("username");     // <input name="username" ...>
    By passwordField = By.name("password");  // <input name="password" ...>
    By loginBtn = By.xpath("//button[@type='submit']"); // <button type="submit">Login</button>

    public loginpage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginBtn).click();
    }
}
