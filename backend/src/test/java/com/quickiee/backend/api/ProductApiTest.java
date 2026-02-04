package com.quickiee.backend.api;

import com.quickiee.backend.testsupport.ApiTestBase;
import com.quickiee.backend.testsupport.JsonHelper;
import com.quickiee.backend.testsupport.ProductClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class ProductApiTest extends ApiTestBase {

    @Test
    void listProductsReturnsSeededProducts() {
        Response response = ProductClient.getAll();

        response.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/product-list.json"));

        response.then().body("size()", greaterThan(0));
    }

    @Test
    void getProductByIdReturnsProduct() {
        Response list = ProductClient.getAll();
        int id = JsonHelper.integer(list, "[0].id");

        ProductClient.getById(id)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/product.json"))
                .body("id", notNullValue());
    }

    @Test
    void categoriesEndpointsReturnValues() {
        ProductClient.getCategories()
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/categories.json"));

        ProductClient.getCategoriesLegacy()
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/categories.json"));
    }

    @Test
    void searchAndFilterReturnResults() {
        Response list = ProductClient.getAll();
        String category = JsonHelper.string(list, "[0].category");
        String name = JsonHelper.string(list, "[0].name");

        ProductClient.filterByCategory(category)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/product-list.json"));

        ProductClient.search(name.split(" ")[0])
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/product-list.json"));
    }
}
