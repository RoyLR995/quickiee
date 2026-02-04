package com.quickiee.backend.api;

import com.quickiee.backend.testsupport.ApiTestBase;
import com.quickiee.backend.testsupport.AuthClient;
import com.quickiee.backend.testsupport.RequestSpecFactory;
import com.quickiee.backend.testsupport.TestUser;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class AuthApiTest extends ApiTestBase {

    @Test
    void signupThenLoginReturnsJwt() {
        TestUser user = AuthClient.createAndLoginUser();

        Response login = AuthClient.login(user.getEmail(), user.getPassword());

        login.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/login-response.json"))
                .body("token", notNullValue());
    }

    @Test
    void protectedEndpointsRequireAuth() {
        given()
                .spec(RequestSpecFactory.jsonRequest())
                .when()
                .get("/api/cart")
                .then()
                .statusCode(anyOf(is(401), is(403)));
    }
}
