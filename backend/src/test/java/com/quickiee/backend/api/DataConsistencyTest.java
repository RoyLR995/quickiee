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

import static org.hamcrest.Matchers.is;

public class DataConsistencyTest extends ApiTestBase {

    @Test
    void stockAndCartConsistencyAcrossOrderLifecycle() {
        TestUser user = AuthClient.createAndLoginUser();
        Response products = ProductClient.getAll();
        int productId = JsonHelper.integer(products, "[0].id");

        int initialStock = JsonHelper.integer(ProductClient.getById(productId), "stockQuantity");

        CartClient.addToCart(user.getToken(), productId).then().statusCode(200);
        CartClient.addToCart(user.getToken(), productId).then().statusCode(200);

        Response place = OrderClient.placeOrder(user.getToken());
        long orderId = JsonHelper.longValue(place, "id");

        int afterOrderStock = JsonHelper.integer(ProductClient.getById(productId), "stockQuantity");
        int expectedStock = initialStock - 2;
        org.junit.jupiter.api.Assertions.assertEquals(expectedStock, afterOrderStock);

        CartClient.getCart(user.getToken())
                .then()
                .statusCode(200)
                .body("size()", is(0));

        OrderClient.cancelOrder(user.getToken(), orderId)
                .then()
                .statusCode(200)
                .body("status", is("CANCELLED"));

        int afterCancelStock = JsonHelper.integer(ProductClient.getById(productId), "stockQuantity");
        org.junit.jupiter.api.Assertions.assertEquals(initialStock, afterCancelStock);
    }
}
