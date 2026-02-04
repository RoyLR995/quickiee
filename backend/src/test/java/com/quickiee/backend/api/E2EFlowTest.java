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
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class E2EFlowTest extends ApiTestBase {

    @Test
    void userCanBrowseAddToCartPlaceAndViewOrder() {
        TestUser user = AuthClient.createAndLoginUser();

        Response products = ProductClient.getAll();
        int productId = JsonHelper.integer(products, "[0].id");

        CartClient.addToCart(user.getToken(), productId)
                .then()
                .statusCode(200);

        Response order = OrderClient.placeOrder(user.getToken());
        long orderId = JsonHelper.longValue(order, "id");

        OrderClient.getOrder(user.getToken(), orderId)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/order-response.json"));

        OrderClient.getOrders(user.getToken())
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/order-list.json"))
                .body("size()", greaterThanOrEqualTo(1));
    }
}
