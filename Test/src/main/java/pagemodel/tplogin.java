package pagemodel;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
 
public class tplogin {
 
    WebDriver driver;
 
    @FindBy(xpath = "//a[contains(text(),'Forget password')]")
    WebElement forgotPasswordLink;

    @FindBy(xpath = "//input[@placeholder='Enter your email']")
    WebElement forgotEmailInput;

    @FindBy(xpath = "//span[contains(text(),'Send OTP')]")
    WebElement sendOtpBtn;

    @FindBy(xpath = "//input[@placeholder='Enter new password']")
    WebElement newPasswordInput;

    @FindBy(xpath = "//input[@placeholder='Confirm new password']")
    WebElement confirmPasswordInput;

    @FindBy(xpath = "//button//span[contains(text(),'Submit')]")
    WebElement submitBtn;

    public void clickForgotPasswordLink() {
        forgotPasswordLink.click();
    }

    public void enterForgotEmail(String email) {
        forgotEmailInput.clear();
        forgotEmailInput.sendKeys(email);
    }

    public void clickSendOtp() {
        sendOtpBtn.click();
    }

    public void enterNewPassword(String pass) {
        newPasswordInput.clear();
        newPasswordInput.sendKeys(pass);
    }

    public void enterConfirmPassword(String pass) {
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(pass);
    }

    public void clickSubmitBtn() {
        submitBtn.click();
    }

    public void enterForgotOTP(String[] otp) {
        for (int i = 1; i <= 6; i++) {
            WebElement otpInput = driver.findElement(By.xpath("//ng-otp-input/div/input[" + i + "]"));
            otpInput.sendKeys(otp[i - 1]);
        }
    }

	public void clickLoginButton() {
		// TODO Auto-generated method stub
		
	}
}
