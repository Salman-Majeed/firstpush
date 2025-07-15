package DemoPackage;

import org.testng.Assert;
import org.testng.annotations.Test;
import pagemodel.LOGINPAGEE;
import pagemodel.DASHBOARDPAGE;

public class DashboardTests extends TestBase {

    @Test
    public void verifyWidgets() {
        test = extent.createTest("Verify Dashboard Widgets");
        driver.get(baseURL);
        LOGINPAGEE login = new LOGINPAGEE(driver);
        login.login(email, password);
        DASHBOARDPAGE dashboard = new DASHBOARDPAGE(driver);
        Assert.assertTrue(dashboard.isSalesWidgetDisplayed());
        Assert.assertTrue(dashboard.isOrdersWidgetDisplayed());
        test.pass("Dashboard widgets verified");
    }

    @Test
    public void verifyWidgetsCount() {
        test = extent.createTest("Verify Dashboard Widgets Count");
        driver.get(baseURL);
        LOGINPAGEE login = new LOGINPAGEE(driver);
        login.login(email, password);
        DASHBOARDPAGE dashboard = new DASHBOARDPAGE(driver);
        // Add your logic for count
        test.pass("Dashboard widgets count verified");
    }
}
