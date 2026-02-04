package com.quickiee.backend.api;

import com.quickiee.backend.testsupport.ApiTestBase;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class HealthApiTest extends ApiTestBase {

    @Test
    void healthEndpointReturnsUpMessage() {
        given()
                .when()
                .get("/health")
                .then()
                .statusCode(200)
                .body(containsString("Quickiee backend is up"));
    }
}
