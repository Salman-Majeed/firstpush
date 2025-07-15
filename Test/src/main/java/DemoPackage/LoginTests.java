package DemoPackage;

import org.testng.Assert;
import org.testng.annotations.Test;
import pagemodel.LOGINPAGEE;

public class LoginTests extends TestBase {

    @Test
    public void validLogin() {
        test = extent.createTest("Valid Login");
        driver.get(baseURL);
        LOGINPAGEE login = new LOGINPAGEE(driver);
        login.login(email, password);
        Assert.assertTrue(driver.getCurrentUrl().contains("/home"), "Login failed");
        test.pass("Valid login passed");
    }

    @Test
    public void invalidPassword() {
        test = extent.createTest("Invalid Password");
        driver.get(baseURL);
        LOGINPAGEE login = new LOGINPAGEE(driver);
        login.login(email, "wrongpass");
        // TODO: Add assertion for error message
        test.pass("Invalid password tested");
    }

    @Test
    public void invalidEmail() {
        test = extent.createTest("Invalid Email");
        driver.get(baseURL);
        LOGINPAGEE login = new LOGINPAGEE(driver);
        login.login("wrong@example.com", password);
        // TODO: Add assertion for error message
        test.pass("Invalid email tested");
    }

    @Test
    public void emptyEmail() {
        test = extent.createTest("Empty Email");
        driver.get(baseURL);
        LOGINPAGEE login = new LOGINPAGEE(driver);
        login.login("", password);
        // TODO: Add assertion for validation
        test.pass("Empty email tested");
    }

    @Test
    public void emptyPassword() {
        test = extent.createTest("Empty Password");
        driver.get(baseURL);
        LOGINPAGEE login = new LOGINPAGEE(driver);
        login.login(email, "");
        // TODO: Add assertion for validation
        test.pass("Empty password tested");
    }

    @Test
    public void sqlInjectionLogin() {
        test = extent.createTest("SQL Injection Login");
        driver.get(baseURL);
        LOGINPAGEE login = new LOGINPAGEE(driver);
        login.login("' OR '1'='1", "' OR '1'='1");
        // TODO: Add assertion for error handling
        test.pass("SQL Injection attempted");
    }
}
