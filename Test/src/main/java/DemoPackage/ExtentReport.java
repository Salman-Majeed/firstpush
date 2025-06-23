package DemoPackage;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ExtentReport {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    public void setupExtentReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("w3school.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);

        test = extent.createTest("W3Schools Search Test");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // Implicit wait
    }

    @Test
    public void searchW3Schools() {
        try {
            // Step 1: Launch browser
            test.info("Launching browser...");

            // Step 2: Open W3Schools
            driver.get("https://www.w3schools.com/");
            test.pass("Opened w3school homepage");

            // Step 3: Maximize window
            driver.manage().window().maximize();
            test.pass("Maximized browser window");

            // Step 5: Search
            String keyword = "PHP";
            driver.findElement(By.id("tnb-google-search-input")).sendKeys(keyword + Keys.ENTER);
            test.pass("Searched for keyword: '" + keyword + "'");

            // Step 6: Capture title
            String actualTitle = driver.getTitle();
            test.pass("Page title: " + actualTitle);

            // Step 7: Assertions
            Assert.assertTrue(actualTitle.toLowerCase().contains("php"), "Page title should contain 'php'");
            test.pass("Assertion Passed: Page title contains 'php'");

            String expectedTitle = "W3Schools Online Web Tutorials";
            Assert.assertNotEquals(actualTitle, expectedTitle, "Page title should NOT match homepage");
            test.pass("Assertion Passed: Title is different from homepage");

        } catch (Exception e) {
            test.fail("Test failed due to exception: " + e.getMessage());
        } finally {
            // Step 8: Close browser and flush report
            if (driver != null) {
                driver.quit();
                test.info("Closed browser");
            }
            extent.flush();
        }
    }
}
