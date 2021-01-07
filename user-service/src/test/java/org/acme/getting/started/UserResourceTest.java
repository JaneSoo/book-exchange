package org.acme.getting.started;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class UserResourceTest {

    @Test
    public void testGetAllEndpoint() {
        given()
          .when().get("/users")
          .then()
             .statusCode(200)
             .body("$.size()", is(4));
    }
}

