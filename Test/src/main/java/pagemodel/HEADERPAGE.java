package pagemodel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HEADERPAGE{
    WebDriver driver;

    By profileIcon = By.id("profile-icon");
    By logoutBtn = By.xpath("//a[text()='Logout']");

    public HEADERPAGE(WebDriver driver) {
        this.driver = driver;
    }

    public void logout() {
        driver.findElement(profileIcon).click();
        driver.findElement(logoutBtn).click();
    }
}
