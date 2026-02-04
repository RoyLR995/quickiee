package com.quickiee.backend.api;

import com.quickiee.backend.testsupport.ApiTestBase;
import com.quickiee.backend.testsupport.AuthClient;
import com.quickiee.backend.testsupport.CartClient;
import com.quickiee.backend.testsupport.JsonHelper;
import com.quickiee.backend.testsupport.OrderClient;
import com.quickiee.backend.testsupport.ProductClient;
import com.quickiee.backend.testsupport.TestUser;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ContractSchemaTest extends ApiTestBase {

    @Test
    void publicEndpointsMatchSchemas() {
        ProductClient.getAll()
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/product-list.json"));

        ProductClient.getCategories()
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/categories.json"));
    }

    @Test
    void authenticatedEndpointsMatchSchemas() {
        TestUser user = AuthClient.createAndLoginUser();
        Response products = ProductClient.getAll();
        int productId = JsonHelper.integer(products, "[0].id");

        CartClient.addToCart(user.getToken(), productId)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/cart-item.json"));

        CartClient.getCart(user.getToken())
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/cart-list.json"));

        OrderClient.placeOrder(user.getToken())
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/order-response.json"));
    }
}
