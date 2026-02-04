package com.quickiee.backend.testsupport;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public final class AuthClient {

    private AuthClient() {}

    public static TestUser createAndLoginUser() {
        String email = "test+" + UUID.randomUUID() + "@quickiee.local";
        TestUser user = new TestUser("Test User", email, "password123");

        Response signupResponse = signup(user);
        user.setId(signupResponse.jsonPath().getLong("id"));

        Response loginResponse = login(user.getEmail(), user.getPassword());
        user.setToken(loginResponse.jsonPath().getString("token"));

        return user;
    }

    public static Response signup(TestUser user) {
        return given()
                .spec(RequestSpecFactory.jsonRequest())
                .body(Map.of(
                        "name", user.getName(),
                        "email", user.getEmail(),
                        "password", user.getPassword()
                ))
                .when()
                .post("/api/auth/signup");
    }

    public static Response login(String email, String password) {
        return given()
                .spec(RequestSpecFactory.jsonRequest())
                .body(Map.of(
                        "email", email,
                        "password", password
                ))
                .when()
                .post("/api/auth/login");
    }
}
