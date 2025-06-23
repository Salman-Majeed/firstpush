package DemoPackage;

import org.testng.annotations.*;
import pagemodel.TestBase;
import pagemodel.loginpage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;

@Listeners({AllureTestNg.class})
@Epic("Login Module")
@Feature("Login Functionality Testing")
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

    @Test(description = "Runs login test cases with valid and invalid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login Attempts using multiple data sets")
    @Description("Loop through test data for login and verify if success or error is shown based on input.")
    public void runAllLoginTests() throws InterruptedException {

        for (int i = 0; i < testData.length; i++) {

            String email = testData[i][0];
            String password = testData[i][1];
            String caseType = testData[i][2];

            ExtentTest testCase = extent.createTest(caseType + " Test");

            Allure.step("Navigating to Login Page");
            driver.get("https://admin-dev.bloxtech.site/#/auth/login");
            Thread.sleep(2000);

            if (caseType.equals("Valid Login")) {
                try {
                    WebElement eye = driver.findElement(By.xpath("//img[contains(@src,'eye-off.svg')]"));
                    eye.click();
                    Allure.step("Clicked password eye icon");
                    testCase.pass("Password eye icon clicked");
                } catch (Exception e) {
                    Allure.step("Password eye icon not found");
                    testCase.warning("Eye icon not found");
                }
            }

            Allure.step("Entering email and password: " + email + " / " + password);
            loginPage.loginAs(email, password);
            Thread.sleep(3000);

            boolean isValidLogin = driver.getCurrentUrl().contains("/dashboard");
            boolean errorShown = driver.findElements(By.xpath("//div[contains(text(),'Error') and contains(@class,'ff-inter-semibold')]")).size() > 0;

            if (caseType.equals("Valid Login") && isValidLogin) {
                Allure.step("Valid login succeeded");
                testCase.pass("Valid login success - dashboard opened");
            } else if (!caseType.equals("Valid Login") && errorShown) {
                Allure.step("Invalid login correctly showed error message");
                testCase.pass("Expected error shown for invalid case: " + caseType);
            } else {
                Allure.step("Unexpected behavior");
                testCase.fail("Unexpected result for: " + caseType);
            }
        }
    }

    @AfterClass
    public void teardown() {
        extent.flush();
    }
}
