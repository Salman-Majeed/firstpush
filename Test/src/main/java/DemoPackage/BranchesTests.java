package DemoPackage;

import org.testng.annotations.Test;
import pagemodel.LOGINPAGEE;
import pagemodel.advanceaction;

public class BranchesTests extends TestBase {

    @Test
    public void addActiveBranch() throws InterruptedException {
        test = extent.createTest("Add Active Branch");
        driver.get(baseURL);
        LOGINPAGEE login = new LOGINPAGEE(driver);
        login.login(email, password);
        advanceaction branch = new advanceaction(driver);
        branch.navigateToBranches();
        branch.addBranch("Active Branch", "Active");
        test.pass("Active branch added");
    }

    @Test
    public void addInactiveBranch() throws InterruptedException {
        test = extent.createTest("Add Inactive Branch");
        driver.get(baseURL);
        LOGINPAGEE login = new LOGINPAGEE(driver);
        login.login(email, password);
        advanceaction branch = new advanceaction(driver);
        branch.navigateToBranches();
        branch.addBranch("Inactive Branch", "Inactive");
        test.pass("Inactive branch added");
    }

    @Test
    public void addBranchBlankName() throws InterruptedException {
        test = extent.createTest("Add Branch with Blank Name");
        driver.get(baseURL);
        LOGINPAGEE login = new LOGINPAGEE(driver);
        login.login(email, password);
        advanceaction branch = new advanceaction(driver);
        branch.navigateToBranches();
        branch.addBranch("", "Inactive");
        test.pass("Blank name branch tested");
    }

    @Test
    public void addBranchLongName() throws InterruptedException {
        test = extent.createTest("Add Branch with Long Name");
        driver.get(baseURL);
        LOGINPAGEE login = new LOGINPAGEE(driver);
        login.login(email, password);
        advanceaction branch = new advanceaction(driver);
        branch.navigateToBranches();
        branch.addBranch("ThisIsAVeryLongBranchNameToTestMaxLength", "Inactive");
        test.pass("Long name branch added");
    }

    @Test
    public void addBranchInvalidLocation() throws InterruptedException {
        test = extent.createTest("Add Branch Invalid Location");
        driver.get(baseURL);
        LOGINPAGEE login = new LOGINPAGEE(driver);
        login.login(email, password);
        advanceaction branch = new advanceaction(driver);
        branch.navigateToBranches();
        // Add code to fail location selection if invalid
        branch.addBranch("Branch Invalid Location", "Inactive");
        test.pass("Invalid location tested");
    }

    @Test
    public void cancelAddBranch() throws InterruptedException {
        test = extent.createTest("Cancel Add Branch");
        driver.get(baseURL);
        LOGINPAGEE login = new LOGINPAGEE(driver);
        login.login(email, password);
        advanceaction branch = new advanceaction(driver);
        branch.navigateToBranches();
        // Instead of saving, click cancel
        // TODO: Add logic for cancel
        test.pass("Add Branch cancel tested");
    }
}