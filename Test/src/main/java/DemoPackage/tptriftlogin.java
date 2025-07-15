package DemoPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import pagemodel.tplogin;
import pagemodel.advanceaction;   // ✅ Capital A — import correct class

import io.qameta.allure.testng.AllureTestNg;

@Listeners({AllureTestNg.class})
public class tptriftlogin extends TestBase {

    String[] arrtOTP_CoDash = {"0", "1", "0", "1", "0", "1"};
    ExtentReports extent;
    ExtentTest test;
    tplogin loginPage;
    advanceaction actions;   // ✅ Correct class name (PascalCase)

    @BeforeTest
    public void setup() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("tpthriftLoginReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);

        test = extent.createTest("ThriftPlan Company Dashboard Login Test");

        initializeBrowser(); // Browser open
        loginPage = new tplogin(driver); // Page Object
        actions = new advanceaction(driver); // ✅ Correct constructor
    }

    @Test
    public void runLoginScenarios() throws InterruptedException {
        String[][] testData = {
            {"muhammad.shakeel1@abc.com", "123123", "Invalid Email"},
            {"muhammad.shakeel@abc.com", "123321", "Invalid Password"},
            {"muhammad.shakeel@abc.com", "123123", "Valid Login"}
        };

        for (int i = 0; i < testData.length; i++) {
            String email = testData[i][0];
            String password = testData[i][1];
            String caseType = testData[i][2];

            loginPage.enterEmail(email);
            loginPage.enterPassword(password);

            if (caseType.equals("Valid Login")) {
                try {
                    WebElement eyeIcon = driver.findElement(By.xpath("//mat-icon[contains(text(),'visibility')]"));

                    actions.mouseHover(eyeIcon);
                    eyeIcon.click();

                    String fieldType = driver.findElement(By.xpath("//input[@type='password' or @type='text']")).getAttribute("type");

                    if (fieldType.equals("text")) {
                        test.pass("Password is visible after hovering and clicking eye icon.");
                    } else {
                        test.fail("Password visibility toggle failed.");
                    }
                } catch (Exception e) {
                    test.warning("Eye icon issue: " + e.getMessage());
                }
            }

            loginPage.clickLoginButton();
            Thread.sleep(2000);
            String currentURL = driver.getCurrentUrl();

            if (!caseType.equals("Valid Login")) {
                if (loginPage.getErrorMessage().equals("You have entered invalid credentials")) {
                    test.pass(caseType + " test passed");
                } else {
                    test.fail(caseType + " test failed");
                }
                loginPage.clickOkButton();
            } else {
                if (currentURL.contains("auth/login")) {
                    for (int j = 1; j <= 6; j++) {
                        WebElement otpBox = driver.findElement(By.xpath("//ng-otp-input/div/input[" + j + "]"));
                        otpBox.sendKeys(arrtOTP_CoDash[j - 1]);
                    }
                    test.pass("OTP entered successfully");

                    Thread.sleep(3000);
                    if (!driver.getCurrentUrl().contains("auth/login")) {
                        test.pass("Redirected to dashboard after OTP");
                    } else {
                        test.fail("OTP accepted but not redirected");
                    }
                } else {
                    test.fail("Did not reach OTP screen");
                }
            }
        }
        extent.flush();
    }
}
