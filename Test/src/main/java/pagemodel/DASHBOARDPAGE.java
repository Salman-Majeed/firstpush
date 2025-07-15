package pagemodel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DASHBOARDPAGE {
    WebDriver driver;

    By salesWidget = By.xpath("//div[contains(@class,'sales-widget')]");
    By ordersWidget = By.xpath("//div[contains(@class,'orders-widget')]");

    public DASHBOARDPAGE(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isSalesWidgetDisplayed() {
        return driver.findElement(salesWidget).isDisplayed();
    }

    public boolean isOrdersWidgetDisplayed() {
        return driver.findElement(ordersWidget).isDisplayed();
    }
}
