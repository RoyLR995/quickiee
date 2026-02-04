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
import static org.hamcrest.Matchers.is;

public class OrderApiTest extends ApiTestBase {

    @Test
    void placeGetAndCancelOrder() {
        TestUser user = AuthClient.createAndLoginUser();
        Response products = ProductClient.getAll();
        int productId = JsonHelper.integer(products, "[0].id");

        CartClient.addToCart(user.getToken(), productId)
                .then()
                .statusCode(200);

        Response place = OrderClient.placeOrder(user.getToken());
        place.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/order-response.json"))
                .body("status", is("PLACED"));

        long orderId = JsonHelper.longValue(place, "id");

        OrderClient.getOrder(user.getToken(), orderId)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/order-response.json"));

        OrderClient.cancelOrder(user.getToken(), orderId)
                .then()
                .statusCode(200)
                .body("status", is("CANCELLED"));
    }
}
