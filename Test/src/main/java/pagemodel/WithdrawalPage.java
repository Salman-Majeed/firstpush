package pagemodel;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class WithdrawalPage {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public WithdrawalPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    public void navigateToWithdrawal() throws InterruptedException {
        WebElement manageBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//h4[normalize-space()='Manage Withdrawal']")));
        js.executeScript("arguments[0].click();", manageBtn);
    }

    public void clickWithdraw() throws InterruptedException {
        WebElement withdrawBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-table-action")));
        js.executeScript("arguments[0].click();", withdrawBtn);
    }

    public void clickDropdownAndEdit() throws InterruptedException {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.show")));
        js.executeScript("arguments[0].click();", dropdown);
    }

    public void enterAmount(String amount) throws InterruptedException {
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.BACK_SPACE).pause(200)
                .sendKeys(Keys.BACK_SPACE).pause(200)
                .sendKeys(Keys.BACK_SPACE).pause(200)
                .sendKeys(amount).perform();
        Thread.sleep(1500);
    }

    public void clickRequestAmount() throws InterruptedException {
        WebElement reqBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(),'Request Amount')]")));
        js.executeScript("arguments[0].click();", reqBtn);
    }

    public void clickConfirm() throws InterruptedException {
        WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(),'Confirm')]")));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", confirmBtn);
        Thread.sleep(300);
        js.executeScript("arguments[0].click();", confirmBtn);
    }
}
