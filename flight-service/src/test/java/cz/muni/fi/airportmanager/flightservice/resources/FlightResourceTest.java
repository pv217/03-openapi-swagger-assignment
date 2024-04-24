package cz.muni.fi.airportmanager.flightservice.resources;


import cz.muni.fi.airportmanager.flightservice.model.Flight;
import cz.muni.fi.airportmanager.flightservice.model.FlightStatus;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


@QuarkusTest
@TestHTTPEndpoint(FlightResource.class) // this tells Quarkus that requests will begin with /flight
class FlightResourceTest {

    private Flight testFlight;

    @BeforeEach
    void setUp() {
        testFlight = new Flight();
        testFlight.id = 1;
        testFlight.name = "Test Flight";
        testFlight.airportFrom = "Airport A";
        testFlight.airportTo = "Airport B";
        testFlight.departureTime = new Date();
        testFlight.arrivalTime = new Date();
        testFlight.capacity = 100;
        testFlight.status = FlightStatus.ACTIVE;
        clearFlights();
    }

    @Test
    void testListEmpty() {
        given().when()
                .get()
                .then()
                .statusCode(200)
                .body(is("[]"));
    }

    @Test
    void testListWithFlights() {
        createFlight(testFlight);

        given().when()
                .get()
                .then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void testCreateFlight() {
        given().contentType("application/json")
                .body(testFlight)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("id", equalTo(testFlight.id));
    }

    @Test
    void testCreateDuplicateFlight() {
        createFlight(testFlight);

        given().contentType("application/json")
                .body(testFlight)
                .when()
                .post()
                .then()
                .statusCode(409);
    }

    @Test
    void testGetExistingFlight() {
        createFlight(testFlight);

        given().when()
                .get("/" + testFlight

                        .id)
                .then()
                .statusCode(200)
                .body("id", equalTo(testFlight.id));
    }

    @Test
    void testGetNonExistingFlight() {
        given().when()
                .get("/99") // Assuming ID 99 does not exist
                .then()
                .statusCode(404);
    }

    @Test
    void testUpdateExistingFlight() {
        createFlight(testFlight);
        testFlight.name = "Updated Name";

        given().contentType("application/json")
                .body(testFlight)
                .when()
                .put("/" + testFlight.id)
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated Name"));
    }

    @Test
    void testUpdateNonExistingFlight() {
        given().contentType("application/json")
                .body(testFlight)
                .when()
                .put("/1")
                .then()
                .statusCode(404);
    }

    @Test
    void testUpdateFlightWithMismatchedId() {
        createFlight(testFlight);

        given().contentType("application/json")
                .body(testFlight)
                .when()
                .put("/99") // Mismatched ID
                .then()
                .statusCode(400);
    }

    @Test
    void testDeleteExistingFlight() {
        createFlight(testFlight);

        given().when()
                .delete("/" + testFlight.id)
                .then()
                .statusCode(200);
    }

    @Test
    void testDeleteNonExistingFlight() {
        given().when()
                .delete("/99") // Assuming ID 99 does not exist
                .then()
                .statusCode(404);
    }


    @Test
    void testCancelFlight_NotFound() {
        given().when()
                .put("/99/cancel") // Assuming ID 99 does not exist
                .then()
                .statusCode(404);
    }


    private void createFlight(Flight flight) {
        given().contentType("application/json")
                .body(flight)
                .when()
                .post()
                .then()
                .statusCode(201);
    }

    private void clearFlights() {
        given().when()
                .delete()
                .then()
                .statusCode(200);
    }
}