package com.quickiee.backend.testsupport;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public final class CartClient {

    private CartClient() {}

    public static Response getCart(String token) {
        return given()
                .spec(RequestSpecFactory.jsonRequestWithAuth(token))
                .when()
                .get("/api/cart");
    }

    public static Response addToCart(String token, int productId) {
        return given()
                .spec(RequestSpecFactory.jsonRequestWithAuth(token))
                .queryParam("productId", productId)
                .when()
                .post("/api/cart/add");
    }

    public static Response decrement(String token, int productId) {
        return given()
                .spec(RequestSpecFactory.jsonRequestWithAuth(token))
                .queryParam("productId", productId)
                .when()
                .post("/api/cart/decrement");
    }
}
