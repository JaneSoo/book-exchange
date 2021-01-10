package org.acme.getting.started;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;


import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;



@QuarkusTest
public class BookResourceTest {

    @Test
    public void testGetAllEndpoint() {
        given()
          .when().get("/books")
          .then()
             .statusCode(200)
             .body("isbn", containsInAnyOrder("978-3836277471", "978-3836277372", "978-1983399404"));
    }

    @Test
    public void testGetEndpoint() {
        given()
          .when().get("/books/978-3836277471")
          .then()
          .statusCode(200)
          .body("isbn", is("978-3836277471"));
    }

    @Test
    public void testCreateEndpoint() {
        given()
          .body("{\"isbn\": \"BOOKISBN\"}")
          .header("Content-Type", MediaType.APPLICATION_JSON)
          .when()
          .post("/books/create")
          .then()
          .statusCode(200)
          .body("isbn", is("BOOKISBN"));
    }

    @Test
    public void testUpdateEndpoint() {
        given()
          .body("{\"isbn\": \"updateISBN\"}")
          .header("Content-Type", MediaType.APPLICATION_JSON)
          .when()
          .put("/books/978-3836277372")
          .then()
          .statusCode(200)
          .body("isbn", is("updateISBN"));
    }

    @Test
    public void testDeleteEndpoint() {
        given()
          .when()
          .delete("/books/978-3966450614")
          .then()
          .statusCode(200)
          .body("isbn", is("978-3966450614"));
    }
}