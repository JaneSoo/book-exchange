package org.acme.getting.started;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
//import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;


@QuarkusTest
public class BookResourceTest {

//    @Test
//    public void testGetAllEndPoint() {
//        given()
//          .when().get("/books")
//          .then()
//             .statusCode(200)
//             .body("$.size()", is(4));
//    }
}