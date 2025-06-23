package DemoPackage;

import org.testng.annotations.*;
import pagemodel.TestBase;
import pagemodel.loginpage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;

@Listeners({AllureTestNg.class})
public class logintest extends TestBase {

    loginpage loginPage;
    ExtentReports extent;
    ExtentTest test;

    String[][] testData = {
        {"wronguser", "y0yfskju", "Invalid Email"},
        {"admin.user@blox.com", "wrongpass", "Invalid Password"},
        {"admin.user@blox.com", "y0yfskju", "Valid Login"}
    };

    @BeforeClass
    public void setup() {
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("BloxLoginReport.html");
        extent.attachReporter(spark);
        loginPage = new loginpage(driver);
    }

    @Test
    public void runAllLoginTests() throws InterruptedException {
        for (int i = 0; i < testData.length; i++) {
            String email = testData[i][0];
            String password = testData[i][1];
            String caseType = testData[i][2];

            ExtentTest testCase = extent.createTest(caseType + " Test");

            driver.get("https://admin-dev.bloxtech.site/#/auth/login");
            Thread.sleep(2000);

            if (caseType.equals("Valid Login")) {
                try {
                    WebElement eye = driver.findElement(By.xpath("//img[contains(@src,'eye-off.svg')]"));
                    eye.click();
                    testCase.pass("Password eye icon clicked");
                } catch (Exception e) {
                    testCase.warning("Eye icon not found");
                }
            }

            loginPage.loginAs(email, password);
            Thread.sleep(3000);

            boolean isValidLogin = driver.getCurrentUrl().contains("/dashboard");
            boolean errorShown = driver.findElements(By.xpath("//div[contains(text(),'Error') and contains(@class,'ff-inter-semibold')]")).size() > 0;

            // ✅ DYNAMIC check: 
            if (caseType.equals("Valid Login") && isValidLogin) {
                testCase.pass("Valid login success - dashboard opened");
            } else if (!caseType.equals("Valid Login") && errorShown) {
                testCase.pass("Expected error shown for invalid case: " + caseType);
            } else {
                testCase.fail("Unexpected result for: " + caseType);
            }
        }
    }

    @AfterClass
    public void teardown() {
        extent.flush();
    }
}
