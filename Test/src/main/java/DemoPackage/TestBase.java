package DemoPackage;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class TestBase {

    public WebDriver driver;
    public ExtentReports extent;
    public ExtentTest test;

    public String baseURL = "https://dev-admin.wareed.com.sa/#/home/login";
    public String email = "admin.andpercent@gmail.com";
    public String password = "abc123";

    @BeforeTest
    public void setupReport() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("ExtentReport.html");
        reporter.config().setDocumentTitle("Automation Report");
        reporter.config().setReportName("Wareed Automation Tests");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Your Name");
    }

    @BeforeMethod
    public void setupBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterTest
    public void flushReport() {
        extent.flush();
    }
}
