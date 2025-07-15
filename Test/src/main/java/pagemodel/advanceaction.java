package pagemodel;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class advanceaction {
    WebDriver driver;
    WebDriverWait wait;

    By branchesMenu = By.xpath("//a[@routerlink='/super-admin/branches']");
    By addNewBtn = By.xpath("//a[@data-toggle='modal']");
    By branchName = By.name("name");
    By locationSearchBox = By.id("search-box-add");
    By suggestion = By.xpath("//div[contains(@class,'pac-item') and contains(.,'I-10')]");
    By statusDropdown = By.xpath("(//select)[1]");
    By inactiveOption = By.xpath("//option[text()='Inactive']");
    By activeOption = By.xpath("//option[text()='Active']");
    By saveBtn = By.xpath("//button[@type='submit' and text()='Save']");

    public advanceaction(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void navigateToBranches() {
        wait.until(ExpectedConditions.elementToBeClickable(branchesMenu)).click();
    }

    public void addBranch(String name, String status) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(addNewBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(branchName)).sendKeys(name);

        driver.findElement(locationSearchBox).click();
        driver.findElement(locationSearchBox).sendKeys("I-10, Islamabad, Pakistan");
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(suggestion)).click();

        WebElement dropdown = driver.findElement(statusDropdown);
        dropdown.click();
        if (status.equalsIgnoreCase("Active")) {
            wait.until(ExpectedConditions.elementToBeClickable(activeOption)).click();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(inactiveOption)).click();
        }

        driver.findElement(saveBtn).click();
    }
}
