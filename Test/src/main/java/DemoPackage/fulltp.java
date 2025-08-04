package DemoPackage;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import pagemodel.*;

import java.time.Duration;

public class fulltp {
    WebDriver driver;
    WebDriverWait wait;
    ExtentReports extent;
    ExtentTest test;

    LoginPage loginPage;
    ForgotPasswordPage forgotPage;
    WithdrawalPage withdrawalPage;

    @BeforeClass
    public void setUp() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage = new LoginPage(driver);
        forgotPage = new ForgotPasswordPage(driver);
        withdrawalPage = new WithdrawalPage(driver);
    }

    @Test(priority = 1)
    public void forgotPasswordTests() throws InterruptedException {
        driver.get("http://employee-staging.thriftplan.com.sa/#/auth/login");
        forgotPage.clickForgotPassword();

        String[][] testData = {
            {"", "Empty Email"},
            {"fatima@com", "Invalid Email Format"},
            {"abc@xyz.com", "Non-existent Email"}
        };

        for (int i = 0; i < testData.length; i++) {
            test = extent.createTest("FP_00" + (i+1) + "_" + testData[i][1].replace(" ", ""));
            forgotPage.enterEmail(testData[i][0]);
            forgotPage.clickSendOtp();
            handlePopup();
            test.pass(testData[i][1] + " test passed.");
        }

        // Reset password flow
        String[][] passTests = {
            {"123456", "654321", "Mismatched Passwords", "111111"},
            {"123123", "123123", "Empty OTP", ""},
            {"123123", "123123", "Wrong OTP", "111111"},
            {"123123", "123123", "Successful Reset", "010101"}
        };

        forgotPage.enterEmail("fatima@salman.com");
        forgotPage.clickSendOtp();
        Thread.sleep(1500);
        forgotPage.clickSubmit();
        handlePopup();

        test = extent.createTest("FP_004_EmptyPasswordFields");
        test.pass("Empty Password Fields test passed.");

        for (int i = 0; i < passTests.length; i++) {
            test = extent.createTest("FP_00" + (i + 5) + "_" + passTests[i][2].replace(" ", ""));
            forgotPage.enterNewPasswords(passTests[i][0], passTests[i][1]);
            if (passTests[i][2].equals("Empty OTP")) forgotPage.clearOtpFields();
            else forgotPage.enterOtp(passTests[i][3]);
            forgotPage.clickSubmit();
            handlePopup();
            test.pass(passTests[i][2] + " test passed.");
        }
    }

    @Test(priority = 2)
    public void loginNegativeTests() throws InterruptedException {
        String[][] loginData = {
            {"", "", "EmptyFields"},
            {"user@abc", "123123", "InvalidEmailFormat"},
            {"fatima@salman.com", "wrongpass", "WrongPassword"},
            {"unknown@salman.com", "123123", "WrongEmail"}
        };

        for (int i = 0; i < loginData.length; i++) {
            test = extent.createTest("LG_00" + (i + 1) + "_" + loginData[i][2]);
            loginPage.enterEmail(loginData[i][0]);
            loginPage.enterPassword(loginData[i][1]);
            loginPage.clickLogin();
            handlePopup();
            test.pass(loginData[i][2] + " test passed.");
        }
    }

    @Test(priority = 3)
    public void successfulLoginAndWithdrawal() throws InterruptedException {
        test = extent.createTest("LG_005_SuccessfulLoginAndWithdrawal");

        loginPage.enterEmail("fatima@salman.com");
        loginPage.enterPassword("123123");
        loginPage.clickLogin();
        loginPage.enterOtp("010101");

        withdrawalPage.navigateToWithdrawal();
        withdrawalPage.clickWithdraw();
        withdrawalPage.clickDropdownAndEdit();
        withdrawalPage.enterAmount("70");
        handlePopup();
        withdrawalPage.enterAmount("5");
        withdrawalPage.clickRequestAmount();
        withdrawalPage.clickConfirm();
        loginPage.enterOtp("010101");
        handlePopup();

        test.pass("Successful Login & Withdrawal test passed.");
    }

    private void handlePopup() {
        try {
            WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class,'btn-ok-sec') and normalize-space()='Ok']")));
            okBtn.click();
            Thread.sleep(1000);
        } catch (Exception ignored) {}
    }

    @AfterClass
    public void tearDown() {
        // driver.quit(); // Uncomment when needed
        extent.flush();
    }
}
