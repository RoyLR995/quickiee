package com.quickiee.ui.core;

import com.quickiee.ui.config.UiConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public abstract class BaseUiTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        if (UiConfig.headless()) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1280,900");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void waitForPageReady() {
        wait.until(d -> {
            if (!(driver instanceof JavascriptExecutor)) {
                return true;
            }
            Object state = ((JavascriptExecutor) driver).executeScript("return document.readyState");
            return "complete".equals(state) || "interactive".equals(state);
        });
    }
}
