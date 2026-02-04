package com.quickiee.ui.pages;

import com.quickiee.ui.config.UiConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void open() {
        driver.get(UiConfig.baseUrl() + "/login");
        wait.until(ExpectedConditions.urlContains("/login"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[placeholder='Email']")
        ));
    }

    public void login(String email, String password) {
        driver.findElement(By.cssSelector("input[placeholder='Email']")).sendKeys(email);
        driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();

        try {
            wait.until(ExpectedConditions.alertIsPresent()).accept();
        } catch (org.openqa.selenium.TimeoutException ignored) {
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[normalize-space()='Logout']")
        ));
    }
}
