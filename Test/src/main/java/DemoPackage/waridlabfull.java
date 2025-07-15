package DemoPackage;

import org.testng.Assert;
import org.testng.annotations.Test;

import pagemodel.LOGINPAGEE;
import pagemodel.DASHBOARDPAGE;
import pagemodel.advanceaction;

public class waridlabfull extends TestBase {

    @Test
    public void endToEndFlow() throws InterruptedException {
        test = extent.createTest("End to End Full Automation Flow");

        // 1️ Open URL
        driver.get(baseURL);

        // 2️ Login
        LOGINPAGEE login = new LOGINPAGEE(driver);
        login.login(email, password);
        test.info("Login successful");

        // 3️ Verify Dashboard widgets
        DASHBOARDPAGE dashboard = new DASHBOARDPAGE(driver);
        Assert.assertTrue(dashboard.isSalesWidgetDisplayed(), "Sales widget not displayed!");
        Assert.assertTrue(dashboard.isOrdersWidgetDisplayed(), "Orders widget not displayed!");
        test.info("Dashboard widgets verified");

        // 4 Navigate to Branches & Add Branch
        advanceaction branchAction = new advanceaction(driver);
        branchAction.navigateToBranches();
        branchAction.addBranch("Test Full Flow Branch", "Active");
        test.info("Branch added successfully");

        //  Final Pass
        test.pass("Full End-to-End Automation Flow executed successfully");
    }
}
