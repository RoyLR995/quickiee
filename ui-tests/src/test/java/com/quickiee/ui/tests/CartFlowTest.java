package com.quickiee.ui.tests;

import com.quickiee.ui.config.UiConfig;
import com.quickiee.ui.core.BaseUiTest;
import com.quickiee.ui.pages.CartPage;
import com.quickiee.ui.pages.LoginPage;
import com.quickiee.ui.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartFlowTest extends BaseUiTest {

    @Test
    public void userCanAddItemAndSeeCart() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();
        waitForPageReady();
        loginPage.login(UiConfig.email(), UiConfig.password());

        ProductsPage productsPage = new ProductsPage(driver, wait);
        productsPage.open();
        waitForPageReady();
        productsPage.addFirstItemToCart();
        productsPage.openCart();

        CartPage cartPage = new CartPage(driver, wait);
        cartPage.waitForLoaded();
        Assert.assertFalse(cartPage.isEmptyMessageVisible(), "Cart should not be empty");
    }
}
