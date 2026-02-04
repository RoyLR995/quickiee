package com.quickiee.backend.testsupport;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public final class JsonHelper {

    private JsonHelper() {}

    public static JsonPath json(Response response) {
        return response.then().extract().jsonPath();
    }

    public static String string(Response response, String path) {
        return json(response).getString(path);
    }

    public static int integer(Response response, String path) {
        return json(response).getInt(path);
    }

    public static long longValue(Response response, String path) {
        return json(response).getLong(path);
    }
}
