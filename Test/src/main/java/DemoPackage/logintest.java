package DemoPackage;

import org.testng.annotations.Test;

import pagemodel.loginpage;
import pagemodel.advanceaction;

public class logintest extends TestBase {

    @Test
    public void runAllLoginTests() throws InterruptedException {
        test = extent.createTest("Branch Add Flow");

        driver.get("https://dev-admin.wareed.com.sa/#/home/login");

        loginpage login = new loginpage(driver);
        login.login("admin.andpercent@gmail.com", "abc123");
        test.pass("Login done!");

        Thread.sleep(5000); // optional: wait for page load

        advanceaction branch = new advanceaction(driver);
        branch.navigateToBranches();
        test.pass("Navigated to Branches.");

        branch.addBranch("Test Branch", "Inactive");
        test.pass("Branch added successfully!");

        // Debug: keep browser open
        Thread.sleep(10000);
    }
}
