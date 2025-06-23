package pagemodel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class loginpage {

    WebDriver driver;

    public loginpage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "mat-input-0")
    WebElement emailField;

    @FindBy(id = "mat-input-1")
    WebElement passwordField;

    @FindBy(xpath = "//button[@type='button']")
    WebElement loginButton;

    public void enterEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterPassword(String pass) {
        passwordField.clear();
        passwordField.sendKeys(pass);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public void loginAs(String email, String pass) {
        enterEmail(email);
        enterPassword(pass);
        clickLogin();
    }
}
