package DemoPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import pagemodel.tplogin;

public class tptriftlogin extends TestBase {

    String[] arrtOTP = {"0", "1", "0", "1", "0", "1"};
    ExtentReports extent;
    ExtentTest test;
    tplogin loginPage;

    @BeforeTest
    public void setup() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("ThriftLoginFlowReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);

        test = extent.createTest("Forgot Password + Login Flow");
        initializeBrowser(); // Opens Chrome and URL
        loginPage = new tplogin(); // Page object created
    }

    @Test
    public void forgotPasswordThenLoginFlow() throws InterruptedException {
        String userEmail = "fatima@salman.com";
        String newPassword = "123123";

        // Step 1: Click on Forget Password
        loginPage.clickForgotPasswordLink();
        test.info("Clicked on Forgot Password link");
        Thread.sleep(1000);

        // Step 2: Enter email
        loginPage.enterForgotEmail(userEmail);
        test.info("Entered email for password reset");
        Thread.sleep(1000);

        // Step 3: Click Send OTP
        loginPage.clickSendOtp();
        test.info("Clicked on Send OTP");
        Thread.sleep(2000);

        // Step 4: Enter new password and confirm password
        loginPage.enterNewPassword(newPassword);
        loginPage.enterConfirmPassword(newPassword);
        test.info("Entered new password and confirmed it");

        // Step 5: Enter OTP
        loginPage.enterForgotOTP(arrtOTP);
        test.info("Entered OTP for password reset");
        Thread.sleep(1000);

        // Step 6: Submit
        loginPage.clickSubmitBtn();
        test.info("Clicked on Submit button");
        Thread.sleep(2000);

        // Step 7: Handle Popup
        try {
            loginPage.clickLoginButton();
            test.pass("Password reset successful and popup handled.");
        } catch (Exception e) {
            test.warning("Popup OK button not found or skipped: " + e.getMessage());
        }

        // Step 8: Now login with new credentials
        loginPage.enterForgotEmail(userEmail);
        loginPage.enterConfirmPassword(newPassword);
        test.info("Entered login credentials");
        Thread.sleep(1000);
        loginPage.clickLoginButton();
        test.info("Clicked login button");
        Thread.sleep(2000);

        // Step 9: OTP Entry after Login
        for (int i = 1; i <= 6; i++) {
            WebElement otpInput = driver.findElement(By.xpath("//ng-otp-input/div/input[" + i + "]"));
            otpInput.sendKeys(arrtOTP[i - 1]);
        }
        test.pass("Login OTP entered successfully");

        // Step 10: Verify login success via URL
        Thread.sleep(5000);
        String currentURL = driver.getCurrentUrl();
        if (!currentURL.contains("auth/login")) {
            test.pass("Login successful after reset flow");
        } else {
            test.fail("Login failed after password reset");
        }

        extent.flush();
    }
}
