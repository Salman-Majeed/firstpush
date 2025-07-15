package DemoPackage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DemoClass {

    WebDriver driver;

    @BeforeTest
    public void LaunchBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.w3schools.com/");
        driver.manage().window().maximize();
        driver.manage().window().minimize();
    }

    @Test
    public void checkTitle() {
        String title = driver.getTitle();
        System.out.println("Page title is: " + title);
    }

    //@AfterTest
   // public void closeBrowser() {
     //   driver.quit();
    }
    
//}
