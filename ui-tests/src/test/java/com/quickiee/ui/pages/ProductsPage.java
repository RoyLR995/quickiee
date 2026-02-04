package com.quickiee.ui.pages;

import com.quickiee.ui.config.UiConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public ProductsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void open() {
        driver.get(UiConfig.baseUrl() + "/products");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[normalize-space()='Products']")
        ));
    }

    public void addFirstItemToCart() {
        By addToCart = By.xpath("//button[normalize-space()='Add to Cart']");
        if (driver.findElements(addToCart).isEmpty()) {
            driver.findElement(By.xpath("(//button[normalize-space()='+'])[1]")).click();
        } else {
            driver.findElement(addToCart).click();
            try {
                wait.until(ExpectedConditions.alertIsPresent()).accept();
            } catch (org.openqa.selenium.TimeoutException ignored) {
            }
        }
    }

    public void openCart() {
        driver.findElement(By.xpath("//a[normalize-space()='Cart']")).click();
    }
}
