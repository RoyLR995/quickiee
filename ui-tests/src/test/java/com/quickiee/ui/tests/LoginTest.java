package com.quickiee.ui.tests;

import com.quickiee.ui.config.UiConfig;
import com.quickiee.ui.core.BaseUiTest;
import com.quickiee.ui.pages.LoginPage;
import org.testng.annotations.Test;

public class LoginTest extends BaseUiTest {

    @Test
    public void userCanLoginFromUi() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();
        waitForPageReady();
        loginPage.login(UiConfig.email(), UiConfig.password());
    }
}
