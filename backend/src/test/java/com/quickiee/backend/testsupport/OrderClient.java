package com.quickiee.backend.testsupport;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public final class OrderClient {

    private OrderClient() {}

    public static Response placeOrder(String token) {
        return given()
                .spec(RequestSpecFactory.jsonRequestWithAuth(token))
                .when()
                .post("/api/orders/place");
    }

    public static Response getOrders(String token) {
        return given()
                .spec(RequestSpecFactory.jsonRequestWithAuth(token))
                .when()
                .get("/api/orders");
    }

    public static Response getOrder(String token, long orderId) {
        return given()
                .spec(RequestSpecFactory.jsonRequestWithAuth(token))
                .when()
                .get("/api/orders/{orderId}", orderId);
    }

    public static Response cancelOrder(String token, long orderId) {
        return given()
                .spec(RequestSpecFactory.jsonRequestWithAuth(token))
                .when()
                .put("/api/orders/{orderId}/cancel", orderId);
    }
}
