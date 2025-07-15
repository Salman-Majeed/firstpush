package DemoPackage;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.Select;

public class actionclass {
    public static void main(String[] args) throws InterruptedException {

        // Setup Chrome
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        // ✅ Open Demo Page
        driver.get("https://demoqa.com");

        Actions actions = new Actions(driver);

        // ✅ 1. Mouse Hover Example
        driver.get("https://demoqa.com/menu/");
        WebElement mainItem = driver.findElement(By.xpath("//a[text()='Main Item 2']"));
        actions.moveToElement(mainItem).perform();
        Thread.sleep(2000);

        // ✅ 2. Right Click (Context Click)
        driver.get("https://demo.guru99.com/test/simple_context_menu.html");
        WebElement rightClickBtn = driver.findElement(By.xpath("//span[text()='right click me']"));
        actions.contextClick(rightClickBtn).perform();
        Thread.sleep(2000);

        // ✅ 3. Double Click
        WebElement doubleClickBtn = driver.findElement(By.xpath("//button[text()='Double-Click Me To See Alert']"));
        actions.doubleClick(doubleClickBtn).perform();
        Thread.sleep(2000);

        // Accept alert
        driver.switchTo().alert().accept();

        // ✅ 4. Drag and Drop
        driver.get("https://demoqa.com/droppable");
        WebElement source = driver.findElement(By.id("draggable"));
        WebElement target = driver.findElement(By.id("droppable"));
        actions.dragAndDrop(source, target).perform();
        Thread.sleep(2000);

        // ✅ 5. Dropdown Selection
        driver.get("https://demoqa.com/select-menu");
        WebElement dropdown = driver.findElement(By.id("oldSelectMenu"));
        Select select = new Select(dropdown);
        select.selectByVisibleText("Yellow");
        Thread.sleep(2000);

       // driver.quit();
    }
}
