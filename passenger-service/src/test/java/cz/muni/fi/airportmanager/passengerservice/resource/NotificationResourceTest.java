package cz.muni.fi.airportmanager.passengerservice.resource;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestHTTPEndpoint(NotificationResource.class)
class NotificationResourceTest {

    @Test
    void testListEmpty() {
        given().when()
                .get()
                .then()
                .statusCode(200)
                .body(is("[]"));
    }

    @Test
    void testDeleteAll() {
        given().when()
                .delete()
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

}