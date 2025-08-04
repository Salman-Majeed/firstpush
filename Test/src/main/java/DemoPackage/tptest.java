package DemoPackage;

import org.testng.annotations.*;
import pagemodel.tppage;

public class tptest extends TestBase {

    tppage page;

    @BeforeClass
    public void setup() {
        initializeBrowser();
        page = new tppage(driver);
    }

    @Test(priority = 1)
    public void forgotPasswordFlow() throws InterruptedException {
        driver.get("http://employee-staging.thriftplan.com.sa/#/auth/login");
        page.clickForgotPassword();                      // Step 1: Click on Forgot Password
        page.enterEmailForReset("fatima@salman.com");    // Step 2: Enter email
        page.clickSendOTP();                             // Step 3: Click Send OTP
    }

    @Test(priority = 2)
    public void loginFlow() throws InterruptedException {
        driver.get("http://employee-staging.thriftplan.com.sa/#/auth/login");
        page.login("fatima@salman.com", "123123");       // Step 4: Login
    }

    @AfterClass
    public void tearDown() {
        // driver.quit(); // Optional: Enable for cleanup
    }
}
