package com.quickiee.backend.testsupport;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public final class ProductClient {

    private ProductClient() {}

    public static Response getAll() {
        return given()
                .spec(RequestSpecFactory.jsonRequest())
                .when()
                .get("/api/products");
    }

    public static Response getById(int id) {
        return given()
                .spec(RequestSpecFactory.jsonRequest())
                .when()
                .get("/api/products/{id}", id);
    }

    public static Response getCategories() {
        return given()
                .spec(RequestSpecFactory.jsonRequest())
                .when()
                .get("/api/products/categories");
    }

    public static Response getCategoriesLegacy() {
        return given()
                .spec(RequestSpecFactory.jsonRequest())
                .when()
                .get("/api/categories");
    }

    public static Response search(String term) {
        return given()
                .spec(RequestSpecFactory.jsonRequest())
                .queryParam("search", term)
                .when()
                .get("/api/products");
    }

    public static Response filterByCategory(String category) {
        return given()
                .spec(RequestSpecFactory.jsonRequest())
                .queryParam("category", category)
                .when()
                .get("/api/products");
    }
}
