package org.acme.getting.started;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PersonResourceTest {

    @Test
    public void testGetAllEndpoint() {
        given()
          .when().get("/users")
          .then()
             .statusCode(200)
             .body(is("get all"));
    }

    @Test
    public void testGetEndpoint() {
        given()
          .when().get("/users/{id}")
          .then()
            .statusCode(200)
            .body(is("get"));
    }

    @Test
    public void testCreateEndPoint() {
        given()
          .when().get("/users/register")
          .then()
            .statusCode(200)
            .body(is("create"));
    }

    @Test
    public void testUpdateEndPoint() {
        given()
          .when().get("users/{id}")
          .then()
            .statusCode(200)
            .body(is("update"));
    }

    @Test
    public void testDeleteEndPoint() {
        given()
          .when().get("/users/{id}")
          .then()
            .statusCode(200)
            .body(is("delete"));
    }

}

