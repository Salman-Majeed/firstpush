package DemoPackage;

import pagemodel.branchespage;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;

public class e2efullflow extends TestBase {

    ExtentReports extent;
    ExtentTest test;
    branchespage branchPage;

    @BeforeClass
    public void setup() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("FinalE2EReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        test = extent.createTest("Full Login + Branch Test Flow");
        initializeBrowser();
        branchPage = new branchespage(driver, wait);
    }

    @Test(priority = 1)
    public void loginTests() {
        test.info(" Running 3 login test cases...");

        // ❌ Invalid Password
        login("admin.andpercent@gmail.com", "wrongpass", " Invalid Password");

        //  Blank Password
        login("admin.andpercent@gmail.com", "", " Blank Password");

        //  Correct Credentials
        login("admin.andpercent@gmail.com", "abc123", " Valid Login");
    }

    public void login(String email, String pass, String label) {
        ExtentTest loginStep = test.createNode(label);
        try {
            driver.get("https://dev-admin.wareed.com.sa/#/auth/login");
            Thread.sleep(1000);

            branchPage.emailField().clear();
            branchPage.emailField().sendKeys(email);
            Thread.sleep(1000);

            branchPage.passwordField().clear();
            branchPage.passwordField().sendKeys(pass);
            Thread.sleep(1000);

            branchPage.loginButton().click();
            Thread.sleep(2000);

            if (driver.getCurrentUrl().contains("dashboard")) {
                loginStep.pass(" Login successful");
            } else {
                loginStep.fail(" Login failed - Not redirected to dashboard");
            }
        } catch (Exception e) {
            loginStep.fail(" Exception during login: " + e.getMessage());
        }
    }

    @Test(priority = 2, dependsOnMethods = {"loginTests"})
    public void branchTests() {
        test.info(" Running 4 branch test cases after login...");

        try {
            branchPage.branchesLink().click();
            Thread.sleep(1000);
            branchPage.addNewButton().click();
            Thread.sleep(1000);

            //  Invalid 1: Missing Name
            runBranchTest("", "Islamabad-I10", "inactive", " Missing Name");

            //  Invalid 2: Missing Location
            runBranchTest("", "", "", " empty fome");

            //  Invalid 3: Invalid Status (Handled by defaulting)
            runBranchTest("BranchThree", "Karachi", "", " Missing Status");

            //  Valid Branch
            runBranchTest("ValidBranch", "Lahore", "active", " Valid Branch");

        } catch (Exception e) {
            test.fail(" Error during branch test execution: " + e.getMessage());
        }
    }

    public void runBranchTest(String name, String location, String status, String label) {
        ExtentTest step = test.createNode(label);
        try {
            Thread.sleep(1000);
            branchPage.nameInputField().clear();
            branchPage.nameInputField().sendKeys(name);

            Thread.sleep(1000);
            branchPage.locationInputField().clear();
            branchPage.locationInputField().sendKeys(location);

            Thread.sleep(1000);
            if (!status.isEmpty()) {
                new Select(branchPage.statusDropdown()).selectByValue(status);
            }

            Thread.sleep(1000);
            branchPage.saveButton().click();
            step.pass(" Branch submitted");
        } catch (Exception e) {
            step.fail(" Branch test failed: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        extent.flush();
        driver.quit();
    }
}
