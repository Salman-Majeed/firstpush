package AppiumPackg;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URI;
import java.net.URL;
import java.time.Duration;

public class appiumreport {

    public static void main(String[] args) {
        AndroidDriver driver = null;

        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", "Android");
            caps.setCapability("automationName", "UiAutomator2");
            caps.setCapability("deviceName", "YDGIZL4PLNPNZLHI");
            caps.setCapability("appPackage", "com.wareedlab");
            caps.setCapability("appActivity", "medical.andpercent.com.deltamedicallabs.IntroActivity");
            caps.setCapability("ignoreHiddenApiPolicyError", true);
            caps.setCapability("dontStopAppOnReset", true);
            caps.setCapability("appWaitActivity", "*");

            URL appiumServer = URI.create("http://127.0.0.1:4723/wd/hub").toURL();
            driver = new AndroidDriver(appiumServer, caps);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            System.out.println(" App launched successfully!");

            //  1) Splash Skip first!
            skipIntroIfPresent(driver);

            // 2) Perform valid login
            performValidLogin(driver, "424242424", "123123");

            //  3) Run Home Visit Booking Flow
            runHomeVisitBookingFlow(driver);

            System.out.println(" Full flow executed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println(" Driver closed");
            }
        }
    }

    //  1) Skip splash screen using your provided resource-id
    public static void skipIntroIfPresent(AndroidDriver driver) {
        try {
            Thread.sleep(2000); // Wait for splash to appear
            WebElement skipBtn = driver.findElement(By.id("com.wareedlab:id/imgOnboardingSkip"));
            if (skipBtn.isDisplayed()) {
                skipBtn.click();
                System.out.println(" Splash screen skipped using ID: imgOnboardingSkip");
            }
        } catch (Exception e) {
            System.out.println(" Splash skip button not found: " + e.getMessage());
        }
    }

    //  2) Perform login
    public static void performValidLogin(AndroidDriver driver, String phone, String password) throws InterruptedException {
        System.out.println(" Performing valid login...");

        driver.findElement(By.id("com.wareedlab:id/frg_sgnin_edtedt_phone")).sendKeys(phone);
        driver.findElement(By.id("com.wareedlab:id/frg_sgnin_edt_password")).sendKeys(password);
        driver.findElement(By.id("com.wareedlab:id/frg_sgnin_btn_signin")).click();

        Thread.sleep(3000);
        System.out.println(" Login successful!");
    }

    //  3) Full Home Visit Booking Flow
    public static void runHomeVisitBookingFlow(AndroidDriver driver) throws InterruptedException {
        System.out.println("📌 Starting Home Visit Booking Flow...");

        // Step 1: Tap Home Visit Card
        driver.findElement(By.xpath("(//android.view.ViewGroup[@resource-id='com.wareedlab:id/frg_home_ll_home_visit'])[1]")).click();
        Thread.sleep(2000);

        // Step 2: View Promotions
        driver.findElement(By.id("com.wareedlab:id/frg_bookstep1_ll_view_promotions")).click();
        Thread.sleep(2000);

        // Step 3: Checkboxes + Arrows
        for (int i = 1; i <= 3; i++) {
            driver.findElement(By.xpath("(//android.widget.CheckBox[@resource-id='com.wareedlab:id/list_itm_promo_test_check_box'])[" + i + "]")).click();
            driver.findElement(By.id("com.wareedlab:id/right_arrow")).click();
            Thread.sleep(800);
        }

        // Extra arrows
        for (int i = 0; i < 4; i++) {
            driver.findElement(By.id("com.wareedlab:id/right_arrow")).click();
            Thread.sleep(800);
        }

        // Final checkboxes
        driver.findElement(By.xpath("(//android.widget.CheckBox[@resource-id='com.wareedlab:id/list_itm_promo_test_check_box'])[5]")).click();
        driver.findElement(By.xpath("(//android.widget.CheckBox[@resource-id='com.wareedlab:id/list_itm_promo_test_check_box'])[6]")).click();

        // Continue from Promotions
        driver.findElement(By.id("com.wareedlab:id/frg_viewpromotions_btn_continue")).click();
        Thread.sleep(2000);

        // Proceed to Order
        driver.findElement(By.id("com.wareedlab:id/btnProceedToOrder")).click();
        Thread.sleep(2000);

        // Select Date
        driver.findElement(By.id("com.wareedlab:id/frg_order_bookingstep2_ll_datetime_cntnr")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='11']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.RelativeLayout[@resource-id='com.wareedlab:id/frg_date_time_rl_continue']")).click();
        Thread.sleep(2000);

        // Select Gender
        driver.findElement(By.id("com.wareedlab:id/frg_bookstep2_ll_female")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.wareedlab:id/frg_order_bookingstep2_btn_continue")).click();
        Thread.sleep(2000);

        driver.findElement(By.id("com.wareedlab:id/dlg_cstalrt_btn_yes")).click();
        Thread.sleep(1500);

        // Proceed to Payment
        driver.findElement(By.id("com.wareedlab:id/frg_order_bookingstep3_btn_proceed_payment")).click();
        Thread.sleep(2000);

        driver.findElement(By.id("com.wareedlab:id/dfrg_paynow_rb_card")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.wareedlab:id/frg_paynow_btn_done")).click();

        System.out.println(" Home Visit Booking Flow Completed!");
    }
}
