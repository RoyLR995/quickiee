package com.quickiee.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public CartPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void waitForLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[normalize-space()='Your Cart']")
        ));
    }

    public boolean isEmptyMessageVisible() {
        return !driver.findElements(By.xpath("//*[contains(text(),'Cart is empty')]")).isEmpty();
    }
}
