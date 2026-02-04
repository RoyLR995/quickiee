package com.quickiee.ui.tests;

import com.quickiee.ui.config.UiConfig;
import com.quickiee.ui.core.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GuestAccessTest extends BaseUiTest {

    @Test
    public void guestIsRedirectedToLoginForCart() {
        driver.get(UiConfig.baseUrl() + "/cart");
        waitForPageReady();
        wait.until(ExpectedConditions.urlContains("/login"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[placeholder='Email']")
        ));
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
    }
}
