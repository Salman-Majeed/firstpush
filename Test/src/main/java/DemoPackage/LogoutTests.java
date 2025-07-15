package DemoPackage;

import org.testng.annotations.Test;
import pagemodel.LOGINPAGEE;
import pagemodel.HEADERPAGE;

public class LogoutTests extends TestBase {

    @Test
    public void logoutTest() {
        test = extent.createTest("Logout Test");
        driver.get(baseURL);
        LOGINPAGEE login = new LOGINPAGEE(driver);
        login.login(email, password);
        HEADERPAGE header = new HEADERPAGE(driver);
        header.logout();
        test.pass("Logout done successfully");
    }
}
