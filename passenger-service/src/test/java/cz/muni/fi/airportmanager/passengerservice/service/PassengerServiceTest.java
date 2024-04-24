package cz.muni.fi.airportmanager.passengerservice.service;

import cz.muni.fi.airportmanager.passengerservice.model.Passenger;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class PassengerServiceTest {
    private PassengerService passengerService;

    private Passenger passenger;

    @BeforeEach
    void setUp() {
        passengerService = new PassengerService();
        passenger = new Passenger();
        passenger.id = 1;
        passenger.firstName = "John";
        passenger.lastName = "Doe";
        passenger.email = "john.doe@example.com";
        passenger.flightId = 100;
    }

    @Test
    void testListAllPassengers() {
        passengerService.createPassenger(passenger);
        List<Passenger> passengers = passengerService.listAll();
        assertNotNull(passengers);
        assertFalse(passengers.isEmpty());
        assertEquals(1, passengers.size());
        assertEquals(passenger, passengers.get(0));
    }

    @Test
    void testGetPassenger_Success() {
        passengerService.createPassenger(passenger);
        Passenger found = passengerService.getPassenger(passenger.id);
        assertNotNull(found);
        assertEquals(passenger, found);
    }

    @Test
    void testGetPassenger_NotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            passengerService.getPassenger(999);
        });
    }

    @Test
    void testGetPassengersForFlight() {
        // Arrange: Create and add several passengers, some with the same flight ID
        Passenger passenger1 = new Passenger();
        passenger1.id = 2;
        passenger1.firstName = "Alice";
        passenger1.lastName = "Smith";
        passenger1.email = "alice.smith@example.com";
        passenger1.flightId = 100;
        passengerService.createPassenger(passenger1);

        Passenger passenger2 = new Passenger();
        passenger2.id = 3;
        passenger2.firstName = "Bob";
        passenger2.lastName = "Johnson";
        passenger2.email = "bob.johnson@example.com";
        passenger2.flightId = 101;
        passengerService.createPassenger(passenger2);

        passengerService.createPassenger(passenger); // passenger with flightId = 100 from setUp method

        // Act: Retrieve passengers for a specific flight ID
        List<Passenger> passengersForFlight100 = passengerService.getPassengersForFlight(100);

        // Assert: Ensure the retrieved list contains the correct passengers
        assertEquals(2, passengersForFlight100.size());
        assertTrue(passengersForFlight100.contains(passenger));
        assertTrue(passengersForFlight100.contains(passenger1));
    }

    @Test
    void testUpdatePassenger_Success() {
        passengerService.createPassenger(passenger);
        passenger.lastName = "Smith";
        Passenger updated = passengerService.updatePassenger(passenger);
        assertNotNull(updated);
        assertEquals("Smith", updated.lastName);
    }

    @Test
    void testUpdatePassenger_NotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            passengerService.updatePassenger(passenger);
        });
    }

    @Test
    void testDeletePassenger_Success() {
        passengerService.createPassenger(passenger);
        passengerService.deletePassenger(passenger.id);
        assertThrows(IllegalArgumentException.class, () -> {
            passengerService.getPassenger(passenger.id);
        });
    }

    @Test
    void testDeletePassenger_NotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            passengerService.deletePassenger(999);
        });
    }

    @Test
    void testDeleteAllPassengers() {
        passengerService.createPassenger(passenger);
        passengerService.deleteAllPassengers();
        List<Passenger> passengers = passengerService.listAll();
        assertTrue(passengers.isEmpty());
    }

    @Test
    void testCreatePassenger_Success() {
        Passenger created = passengerService.createPassenger(passenger);
        assertNotNull(created);
        assertEquals(passenger, created);
    }

    @Test
    void testCreatePassenger_AlreadyExists() {
        passengerService.createPassenger(passenger);
        assertThrows(IllegalArgumentException.class, () -> {
            passengerService.createPassenger(passenger);
        });
    }
}
