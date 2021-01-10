package org.acme.getting.started;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

@QuarkusTest
public class UserResourceTest {

    @Test
    public void testGetAllEndpoint() {
        given()
          .when().get("/users")
          .then()
             .statusCode(200)
             .body("email", containsInAnyOrder("jonh@email.com", "jake@email.com", "rose@email.com"));
    }

    @Test
    public void testRegisterEndpoint() {
        given()
          .body("{\"email\": \"pear@email.com\", \"password\": \"password\", \"firstName\": \"Pear\", \"lastName\": \"Poo\"}")
          .header("Content-Type", MediaType.APPLICATION_JSON)
          .when()
          .post("/users/register")
          .then()
          .statusCode(200)
          .body("email", is("pear@email.com"));
    }

    @Test
    public void testLoginEndpoint() {
        given()
          .body("{\"email\": \"jonh@email.com\", \"password\": \"passwordforjonh\"}")
          .header("Content-Type", MediaType.APPLICATION_JSON)
          .when()
          .post("/users/login")
          .then()
          .statusCode(200);
    }

    @Test
    public void testGetEndpoint() {
        given()
          .when().get("/users/1")
          .then()
          .statusCode(200)
          .body("email", is("jonh@email.com"));
    }

    @Test
    public void testUpdateEndpoint() {
        given()
          .body("{\"email\": \"update@email.com\"}")
          .header("Content-Type", MediaType.APPLICATION_JSON)
          .when()
          .put("/users/2")
          .then()
          .statusCode(200)
          .body("email", is("update@email.com"));
    }

    @Test
    public void testDeleteEndpoint() {
        given()
          .when()
          .delete("/users/4")
          .then()
          .statusCode(200)
          .body("email", is("jimmy@email.com"));
    }
}

