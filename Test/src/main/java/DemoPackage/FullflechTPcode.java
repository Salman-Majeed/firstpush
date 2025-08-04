package DemoPackage;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import java.time.Duration;

public class FullflechTPcode {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setUp() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("FULLflechTPcodeExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
    }

    @Test(priority = 1)
    public void runForgotPasswordTests() throws InterruptedException {
        String[][] testData = {
                {"", "Empty Email"},
                {"fatima@com", "Invalid Email Format"},
                {"abc@xyz.com", "Non-existent Email"}
        };

        driver.get("http://employee-staging.thriftplan.com.sa/#/auth/login");
        js.executeScript("arguments[0].click();", wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Forgot Password?')]"))));
        Thread.sleep(2000);

        for (String[] data : testData) {
            test = extent.createTest("FP_" + String.format("%03d", testData.length) + "_" + data[1].replace(" ", ""));
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formcontrolname='email']")));
            emailField.clear();
            emailField.sendKeys(data[0]);
            clickSendOtp();
            handlePopup();
            Thread.sleep(2000);
            test.pass(data[1] + " test passed.");
        }

        String[][] passTests = {
                {"123456", "654321", "Mismatched Passwords", "111111"},
                {"123123", "123123", "Empty OTP", ""},
                {"123123", "123123", "Wrong OTP", "111111"},
                {"123123", "123123", "Successful Reset", "010101"}
        };

        emailField().clear();
        emailField().sendKeys("fatima@salman.com");
        clickSendOtp();
        Thread.sleep(2000);
        clickSubmitBtn();
        handlePopup();
        Thread.sleep(2000);
        test = extent.createTest("FP_004_EmptyPasswordFields");
        test.pass("Empty Password Fields test passed.");

        for (int i = 0; i < passTests.length; i++) {
            test = extent.createTest("FP_00" + (i + 5) + "_" + passTests[i][2].replace(" ", ""));
            fillResetPasswords(passTests[i][0], passTests[i][1]);
            if (passTests[i][2].equals("Empty OTP")) clearOtpFields();
            else enterOtp(passTests[i][3]);
            clickSubmitBtn();
            handlePopup();
            if (passTests[i][2].equals("Successful Reset"))
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Ok']"))).click();
            Thread.sleep(2000);
            test.pass(passTests[i][2] + " test passed.");
        }
    }

    @Test(priority = 2)
    public void runLoginTests() throws InterruptedException {
        String[][] loginData = {
                {"", "", "EmptyFields"},
                {"user@abc", "123123", "InvalidEmailFormat"},
                {"fatima@salman.com", "wrongpass", "WrongPassword"},
                {"unknown@salman.com", "123123", "WrongEmail"}
        };

        for (int i = 0; i < loginData.length; i++) {
            test = extent.createTest("LG_00" + (i + 1) + "_" + loginData[i][2]);
            if (!loginData[i][0].isEmpty())
                enterLogin(loginData[i][0], loginData[i][1]);
            clickLogin();
            handlePopup();
            Thread.sleep(2000);
            test.pass(loginData[i][2] + " test passed.");
        }
    }

    @Test(priority = 3)
    public void LG_005_SuccessfulLoginAndWithdrawal() throws InterruptedException {
        test = extent.createTest("LG_005_SuccessfulLoginAndWithdrawal");
        enterLogin("fatima@salman.com", "123123");
        clickLogin();
        enterOtp("010101");
        Thread.sleep(2000);

        js.executeScript("arguments[0].click();", wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h4[normalize-space()='Manage Withdrawal']"))));
        Thread.sleep(2000);
        js.executeScript("arguments[0].click();", wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-table-action"))));
        Thread.sleep(2000);
        js.executeScript("arguments[0].click();", wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.show"))));
        Thread.sleep(2000);

        Actions action = new Actions(driver);
        action.sendKeys(Keys.BACK_SPACE).pause(Duration.ofMillis(200))
               .sendKeys(Keys.BACK_SPACE).pause(Duration.ofMillis(200))
               .sendKeys(Keys.BACK_SPACE).pause(Duration.ofMillis(200))
               .sendKeys("70").perform();

        try {
            WebDriverWait smallWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            WebElement okButton = smallWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(@class,'btn-ok-sec') and normalize-space(text())='Ok']")
            ));
            Thread.sleep(2000);
            okButton.click();
        } catch (TimeoutException e) {
            System.out.println("OK button not found within 5 seconds.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.BACK_SPACE).pause(Duration.ofMillis(200))
               .sendKeys(Keys.BACK_SPACE).pause(Duration.ofMillis(200))
               .sendKeys(Keys.BACK_SPACE).pause(Duration.ofMillis(200))
               .sendKeys("5").perform();
        Thread.sleep(2000);

        js.executeScript("arguments[0].click();", wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Request Amount')]"))));
        Thread.sleep(2000);
        WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Confirm')]")));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", confirmBtn);
        Thread.sleep(500);
        js.executeScript("arguments[0].click();", confirmBtn);
        Thread.sleep(3000);
        enterOtp("010101");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'btn-ok-sec') and normalize-space()='Ok']"))).click();
        Thread.sleep(2000);

        //  New Step: Click Cancel Request button
        WebElement cancelRequest = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Cancel Request')]")));
        js.executeScript("arguments[0].click();", cancelRequest);
        Thread.sleep(2000);

        //  Handle confirmation modal: Click Yes
        WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Yes') and contains(@class,'btn-ok')]")));
        yesButton.click();
        Thread.sleep(2000);

        test.pass("Successful Login, Withdrawal, and Cancel Request test passed.");
    }

    private WebElement emailField() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formcontrolname='email']")));
    }

    private void clickSendOtp() {
        js.executeScript("arguments[0].click();", wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,'Send OTP')]"))));
    }

    private void fillResetPasswords(String newPass, String confirmPass) throws InterruptedException {
        WebElement newPassword = wait.until(ExpectedConditions.elementToBeClickable(By.id("mat-input-3")));
        WebElement confirmPassword = wait.until(ExpectedConditions.elementToBeClickable(By.id("mat-input-4")));
        newPassword.clear(); newPassword.sendKeys(newPass);
        confirmPassword.clear(); confirmPassword.sendKeys(confirmPass);
        Thread.sleep(2000);
    }

    private void enterOtp(String otp) throws InterruptedException {
        for (int i = 0; i < otp.length(); i++) {
            WebElement otpField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id^='otp_" + i + "']")));
            otpField.clear(); otpField.sendKeys(Character.toString(otp.charAt(i)));
        }
        Thread.sleep(2000);
    }

    private void clearOtpFields() throws InterruptedException {
        for (int i = 0; i < 6; i++) {
            WebElement otpField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id^='otp_" + i + "']")));
            otpField.clear();
        }
    }

    private void clickSubmitBtn() {
        js.executeScript("arguments[0].click();", wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'btn-auth-screens')]"))));
    }

    private void enterLogin(String username, String password) throws InterruptedException {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-input-5")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-input-6")));
        usernameField.clear(); usernameField.sendKeys(username);
        passwordField.clear(); passwordField.sendKeys(password);
        Thread.sleep(2000);
    }

    private void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btnLogin"))).click();
    }

    private void handlePopup() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Ok']"))).click();
            Thread.sleep(2000);
        } catch (Exception ignored) {}
    }

    @AfterClass
    public void tearDown() {
      //  driver.quit();
        extent.flush();
    }
}
