package cz.muni.fi.airportmanager.passengerservice.resource;

import cz.muni.fi.airportmanager.passengerservice.model.Passenger;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestHTTPEndpoint(PassengerResource.class)
class PassengerResourceTest {

    private Passenger testPassenger;

    @BeforeEach
    void setUp() {
        testPassenger = new Passenger();
        testPassenger.id = 1;
        testPassenger.firstName = "John";
        testPassenger.lastName = "Doe";
        testPassenger.email = "john.doe@example.com";
        testPassenger.flightId = 100;
        clearPassengers();
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
    void testListWithPassengers() {
        createPassenger(testPassenger);

        given().when()
                .get()
                .then()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void testCreatePassenger() {
        given().contentType("application/json")
                .body(testPassenger)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("id", equalTo(testPassenger.id));
    }

    @Test
    void testCreateDuplicatePassenger() {
        createPassenger(testPassenger);

        given().contentType("application/json")
                .body(testPassenger)
                .when()
                .post()
                .then()
                .statusCode(409);
    }

    @Test
    void testGetExistingPassenger() {
        createPassenger(testPassenger);

        given().when()
                .get("/" + testPassenger.id)
                .then()
                .statusCode(200)
                .body("id", equalTo(testPassenger.id));
    }

    @Test
    void testGetNonExistingPassenger() {
        given().when()
                .get("/99") // Assuming ID 99 does not exist
                .then()
                .statusCode(404);
    }

    @Test
    void testUpdateExistingPassenger() {
        createPassenger(testPassenger);
        testPassenger.lastName = "Smith";

        given().contentType("application/json")
                .body(testPassenger)
                .when()
                .put("/" + testPassenger.id)
                .then()
                .statusCode(200)
                .body("lastName", equalTo("Smith"));
    }

    @Test
    void testUpdateNonExistingPassenger() {
        given().contentType("application/json")
                .body(testPassenger)
                .when()
                .put("/1")
                .then()
                .statusCode(404);
    }

    @Test
    void testUpdatePassengerWithMismatchedId() {
        createPassenger(testPassenger);

        given().contentType("application/json")
                .body(testPassenger)
                .when()
                .put("/99") // Mismatched ID
                .then()
                .statusCode(400);
    }

    @Test
    void testDeleteExistingPassenger() {
        createPassenger(testPassenger);

        given().when()
                .delete("/" + testPassenger.id)
                .then()
                .statusCode(200);
    }

    @Test
    void testDeleteNonExistingPassenger() {
        given().when()
                .delete("/99") // Assuming ID 99 does not exist
                .then()
                .statusCode(404);
    }

    private void createPassenger(Passenger passenger) {
        given().contentType("application/json")
                .body(passenger)
                .when()
                .post()
                .then()
                .statusCode(201);
    }

    private void clearPassengers() {
        given().when()
                .delete()
                .then()
                .statusCode(200);
    }
}
