package DemoPackage;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
 
public class orangehrmlogintest {
 
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;
 
    @BeforeTest
    public void setupReportAndBrowser() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("OrangeHRMLoginReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        test = extent.createTest("OrangeHRM Login Functionality");
 
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        test.info("Chrome launched");
        driver.manage().window().maximize();
    }
 
    @BeforeMethod
    public void openLoginPage() throws InterruptedException {
        driver.get("https://admin-dev.bloxtech.site/#/auth/login");
        Thread.sleep(3000); // Wait for page to load
        test.info("Opened OrangeHRM login page");
    }
 
    @Test(priority = 1)
    public void testInvalidEmail() throws InterruptedException {
        WebElement username = driver.findElement(By.id("mat-input-0"));
        WebElement password = driver.findElement(By.id("mat-input-1"));
        WebElement loginBtn = driver.findElement(By.xpath("//button[@type='button']"));
        

        username.clear();
        password.clear();
        username.sendKeys("wronguser");
        password.sendKeys("y0yfskju");
        loginBtn.click();

        Thread.sleep(2000);
        if (driver.findElements(By.xpath("//p[contains(text(),'Invalid')]")).size() > 0) {
            test.pass("Invalid username test passed");
        } else {
            test.fail("Invalid username test failed");
        }
    }

    @Test(priority = 2)
    public void testInvalidPassword() throws InterruptedException {
        WebElement username = driver.findElement(By.id("mat-input-0"));
        WebElement password = driver.findElement(By.id("mat-input-1"));
        WebElement loginBtn = driver.findElement(By.xpath("//button[@type='button']"));

        username.clear();
        password.clear();
        username.sendKeys("admin.user@blox.com");
        password.sendKeys("1y0yfskju"); // wrong password
        loginBtn.click();

        Thread.sleep(2000);
        if (driver.findElements(By.xpath("//p[contains(text(),'Invalid')]")).size() > 0) {
            test.pass("Invalid password test passed");
        } else {
            test.fail("Invalid password test failed");
        }
    }

    @Test(priority = 3)
    public void testValidLogin() throws InterruptedException {
        WebElement username = driver.findElement(By.id("mat-input-0"));
        WebElement password = driver.findElement(By.id("mat-input-1"));
        WebElement loginBtn = driver.findElement(By.xpath("//button[@type='button']"));

        username.clear();
        password.clear();
        username.sendKeys("admin.user@blox.com");
        password.sendKeys("y0yfskju"); // remove extra space
        loginBtn.click();

        Thread.sleep(3000);
        if (driver.getCurrentUrl().contains("/dashboard")) {
            test.pass("Valid login test passed, redirected to dashboard");
        } else {
            test.fail("Valid login test failed");
        }
    }

    @AfterTest
    public void tearDown() {
        extent.flush();	
    }
}